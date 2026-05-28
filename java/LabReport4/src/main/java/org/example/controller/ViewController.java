package org.example.controller;


import org.example.view.OperationPanel;
import org.example.view.ReceivePanel;
import org.example.view.TransmitPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewController {
    private OperationPanel operationPanel;
    private ReceivePanel receivePanel;
    private TransmitPanel transmitPanel;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String serverIp;
    private int serverPort;

    private AtomicBoolean connected = new AtomicBoolean(false);
    private AtomicBoolean sendFlag = new AtomicBoolean(false);
    private AtomicBoolean receiverRunning = new AtomicBoolean(false);
    private AtomicBoolean senderRunning = new AtomicBoolean(false);

    private Thread receiverThread;
    private Thread senderThread;

    public ViewController(OperationPanel operationPanel,
                          ReceivePanel receivePanel,
                          TransmitPanel transmitPanel) {
        this.operationPanel = operationPanel;
        this.receivePanel = receivePanel;
        this.transmitPanel = transmitPanel;

        operationPanel.clientRadio.addItemListener(this::onModeSelected);
        operationPanel.connectButton.addActionListener(this::onConnectDisconnect);
        transmitPanel.sendButton.addActionListener(e -> onSendData());
    }

    private void onModeSelected(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            while (true) {
                String input = JOptionPane.showInputDialog(
                        operationPanel,
                        "请输入服务器 IP 和端口（格式：IP:Port）\n例如：127.0.0.1:8888",
                        "客户端模式配置",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (input == null) {
                    operationPanel.clientRadio.setSelected(false);
                    JOptionPane.showMessageDialog(operationPanel,
                            "未配置服务器地址，无法使用客户端模式。请重新选择并配置。",
                            "配置取消", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                input = input.trim();
                if (parseServerAddress(input)) {
                    operationPanel.serverAddrField.setText(serverIp + ":" + serverPort);
                    break;
                } else {
                    JOptionPane.showMessageDialog(operationPanel,
                            "格式错误！请使用英文冒号分隔 IP 和端口，例如：192.168.1.100:8080",
                            "无效格式", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean parseServerAddress(String address) {
        if (address == null || address.isEmpty()) return false;
        int colonIdx = address.lastIndexOf(':');
        if (colonIdx <= 0 || colonIdx == address.length() - 1) return false;
        String ip = address.substring(0, colonIdx);
        String portStr = address.substring(colonIdx + 1);
        try {
            int port = Integer.parseInt(portStr);
            if (port < 1 || port > 65535) return false;
            serverIp = ip;
            serverPort = port;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void onConnectDisconnect(ActionEvent e) {
        if (!connected.get()) {
            if (serverIp == null || serverPort == 0) {
                JOptionPane.showMessageDialog(operationPanel,
                        "请先通过 TCP Client 单选按钮配置服务器 IP 和端口！",
                        "未配置服务器", JOptionPane.WARNING_MESSAGE);
                return;
            }
            connectToServer();
        } else {
            disconnectFromServer();
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket(serverIp, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            connected.set(true);
            operationPanel.connectButton.setText("Disconnect from Server");
            operationPanel.clientRadio.setEnabled(false);
            appendReceiveMessage("*** 已连接到服务器 " + serverIp + ":" + serverPort + " ***\n");

            startSenderThread();
            startReceiverThread();

        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(operationPanel,
                    "无效的服务器地址：" + serverIp,
                    "UnknownHostException", JOptionPane.ERROR_MESSAGE);
            cleanupConnection();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(operationPanel,
                    "连接失败：" + e.getMessage(),
                    "IOException", JOptionPane.ERROR_MESSAGE);
            cleanupConnection();
        }
    }

    private void disconnectFromServer() {
        senderRunning.set(false);
        receiverRunning.set(false);
        if (senderThread != null) senderThread.interrupt();
        if (receiverThread != null) receiverThread.interrupt();

        cleanupConnection();
        appendReceiveMessage("*** 已断开与服务器的连接 ***\n");
        operationPanel.connectButton.setText("Connect to Server");
        operationPanel.clientRadio.setEnabled(true);
        connected.set(false);
    }

    private void cleanupConnection() {
        try { if (out != null) out.close(); } catch (Exception e) {}
        try { if (in != null) in.close(); } catch (Exception e) {}
        try { if (socket != null) socket.close(); } catch (Exception e) {}
        out = null;
        in = null;
        socket = null;
    }

    private void startReceiverThread() {
        receiverRunning.set(true);
        receiverThread = new Thread(() -> {
            try {
                while (receiverRunning.get() && !Thread.currentThread().isInterrupted()) {
                    String msg = in.readLine();
                    if (msg == null) break;
                    final String displayMsg = msg;
                    SwingUtilities.invokeLater(() -> appendReceiveMessage(displayMsg + "\n"));
                }
            } catch (IOException e) {
                if (receiverRunning.get()) {
                    SwingUtilities.invokeLater(() -> {
                        appendReceiveMessage("!!! 接收错误，连接可能已中断 !!!\n");
                        JOptionPane.showMessageDialog(operationPanel,
                                "与服务器的连接中断：" + e.getMessage(),
                                "连接中断", JOptionPane.ERROR_MESSAGE);
                    });
                }
            } finally {
                if (connected.get()) {
                    SwingUtilities.invokeLater(this::disconnectFromServer);
                }
            }
        });
        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    private void startSenderThread() {
        senderRunning.set(true);
        senderThread = new Thread(() -> {
            while (senderRunning.get() && !Thread.currentThread().isInterrupted()) {
                if (sendFlag.get()) {
                    final String[] textHolder = new String[1];
                    final boolean[] shouldSend = {false};
                    try {
                        SwingUtilities.invokeAndWait(() -> {
                            String txt = transmitPanel.textArea.getText();
                            if (txt.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(operationPanel,
                                        "不能发送空消息！", "提示", JOptionPane.WARNING_MESSAGE);
                                shouldSend[0] = false;
                            } else {
                                textHolder[0] = txt;
                                shouldSend[0] = true;
                            }
                        });
                    } catch (Exception ex) {
                        sendFlag.set(false);
                        continue;
                    }

                    if (shouldSend[0] && textHolder[0] != null && out != null) {
                        out.println(textHolder[0]);
                        SwingUtilities.invokeLater(() -> {
                            appendReceiveMessage("[我] " + textHolder[0] + "\n");
                            transmitPanel.textArea.setText("");
                        });
                    }
                    sendFlag.set(false);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        senderThread.setDaemon(true);
        senderThread.start();
    }

    private void onSendData() {
        if (!connected.get()) {
            JOptionPane.showMessageDialog(operationPanel,
                    "尚未连接服务器，请先建立连接！",
                    "未连接", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (transmitPanel.textArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(operationPanel,
                    "发送内容不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        sendFlag.set(true);
    }

    private void appendReceiveMessage(String msg) {
        receivePanel.textArea.append(msg);
        receivePanel.textArea.setCaretPosition(receivePanel.textArea.getDocument().getLength());
    }
}