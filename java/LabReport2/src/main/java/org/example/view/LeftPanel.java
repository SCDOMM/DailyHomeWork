package org.example.view;

import org.example.utils.DialogUtil;
import org.example.utils.ShareResource;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LeftPanel extends JPanel {
    private JLabel label1 = new JLabel("         数据采集端        ");

    private JPanel titlePanel = new JPanel();
    public JTextArea dataText = new JTextArea(1, 3);

    private JPanel pickPanel = new JPanel();
    private JTextField pickText = new JTextField(3);
    private JButton pickBtn = new JButton("设置采集周期");

    private JPanel sendPanel = new JPanel();
    private JTextField sendText = new JTextField(3);
    private JButton sendBtn = new JButton("设置发送周期");

    private JButton changePickBtn = new JButton("开始/停止(数据采集)");
    private JButton changeSendBtn = new JButton("开始/停止(数据发送)");

    private int sendTerm;
    private int pickTerm;
    private boolean isRunning1 = false;
    private boolean isRunning2 = false;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public LeftPanel() {
        setSize(200, 400);
        setBackground(new Color(135, 206, 235));
        setLayout(new GridLayout(7, 1));
        initText();
        initPick();
        initSend();

        add(label1);
        add(titlePanel);
        add(pickPanel);
        add(sendPanel);
        add(changePickBtn);
        add(changeSendBtn);

        initEvent();
    }

    private void initText() {
        label1.setFont(new Font("Serif", Font.PLAIN, 20));
        JLabel label2 = new JLabel("  实时温度数据:");
        label2.setFont(new Font("Serif", Font.PLAIN, 15));
        dataText.setEditable(false);

        titlePanel.add(label2);
        titlePanel.add(dataText);
    }

    private void initPick() {
        pickPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        pickPanel.setLayout(new GridLayout(1, 3));
        pickPanel.add(new JLabel("采集周期(ms):"));
        pickPanel.add(pickText);
        pickPanel.add(pickBtn);
    }

    private void initSend() {
        sendPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sendPanel.setLayout(new GridLayout(1, 3));
        sendPanel.add(new JLabel("发送周期(ms):"));
        sendPanel.add(sendText);
        sendPanel.add(sendBtn);
    }

    private void initEvent() {
        pickBtn.addActionListener(event -> {
            if (pickText.getText().isEmpty()) {
                DialogUtil.generateDialog("请输入数值！");
                return;
            }
            pickTerm = Integer.parseInt(pickText.getText());
            System.out.println("pickTerm:" + pickTerm);
        });
        sendBtn.addActionListener(event -> {
            if (sendText.getText().isEmpty()) {
                DialogUtil.generateDialog("请输入数值！");
                return;
            }
            sendTerm = Integer.parseInt(sendText.getText());
            System.out.println("sendTerm:" + sendTerm);
        });

        initPickAct();
        changePickBtn.addActionListener(event -> {
            if (pickText.getText().isEmpty()) {
                DialogUtil.generateDialog("请输入采集周期！");
                return;
            }
            isRunning1 = !isRunning1;
            synchronized (lock1) {
                lock1.notify();
            }
        });

        initSendAct();
        changeSendBtn.addActionListener(event -> {
            if (sendText.getText().isEmpty()) {
                DialogUtil.generateDialog("请输入发送周期！");
                return;
            }
            if (dataText.getText().isEmpty()) {
                DialogUtil.generateDialog("温度数据为空！");
                return;
            }
            isRunning2 = !isRunning2;
            ShareResource.isRunning=!ShareResource.isRunning;
            synchronized (ShareResource.LOCK) {
                if (ShareResource.isRunning) ShareResource.LOCK.notify();
            }
            synchronized (lock2) {
                lock2.notify();
            }
        });


    }

    private void initPickAct() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock1) {
                    while (!isRunning1) {
                        try {
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
                try {
                    Thread.sleep(pickTerm);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int number = (int) (Math.random() * 100) + 1;
                SwingUtilities.invokeLater(() -> dataText.setText(String.valueOf(number)));
            }
        });
        thread1.start();
    }

    private void initSendAct() {
        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock2) {
                    while (!isRunning2) {
                        try {
                            lock2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
                while (true) {
                    synchronized (ShareResource.LOCK) {
                        CenterPanel.generateTransmit(Integer.parseInt(dataText.getText()));
                        try {
                            ShareResource.LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(sendTerm);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread2.start();
    }
}



