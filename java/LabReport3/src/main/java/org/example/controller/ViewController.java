package org.example.controller;

import org.example.utils.DialogUtil;
import org.example.utils.EncryptUtil;
import org.example.view.OperationPanel;
import org.example.view.ReadPanel;
import org.example.view.WritePanel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewController {
    private OperationPanel operationPanel;
    private WritePanel writePanel;
    private BufferedWriter buf_writer = null;
    private ReadPanel readPanel;
    private BufferedReader buf_reader = null;
    private FileWriter writer = null;
    private FileReader reader = null;
    private File file = null;

    private final AtomicBoolean encrypt = new AtomicBoolean(false);
    private final AtomicBoolean decrypt = new AtomicBoolean(false);
    private String key;

    public ViewController(OperationPanel operationPanel, WritePanel writePanel, ReadPanel readPanel) {
        this.operationPanel = operationPanel;
        this.writePanel = writePanel;
        this.readPanel = readPanel;
        initCreate();
        initWrite();
        initRead();

        operationPanel.encrypt_cb.addItemListener(event -> {
            boolean selected = operationPanel.encrypt_cb.isSelected();
            encrypt.set(selected);
            if (selected) {
                SwingUtilities.invokeLater(() -> {
                    key = DialogUtil.generateInputDialog("提示：", "请输入密钥！");
                });
            }
        });
        operationPanel.decrypt_cb.addItemListener(event -> {
            boolean selected = operationPanel.decrypt_cb.isSelected();
            decrypt.set(selected);
            if (selected) {
                SwingUtilities.invokeLater(() -> {
                    key = DialogUtil.generateInputDialog("提示：", "请输入密钥！");
                });
            }
        });
    }

    private void initCreate() {
        operationPanel.create_btn.addActionListener(event -> {
            String text = operationPanel.nameText.getText();
            if (text.isEmpty() || text.isBlank()) {
                DialogUtil.generateDialog("错误！文件名为空！");
                return;
            }
            file = new File(text);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    DialogUtil.generateDialog("创建文件成功！");
                } catch (IOException e) {
                    DialogUtil.generateDialog("创建文件失败！" + e);
                    e.printStackTrace();
                }
            } else {
                DialogUtil.generateDialog("加载文件成功！");
            }
        });
    }

    private void initWrite() {
        operationPanel.write_btn.addActionListener(event -> {
            if (writePanel.text1.getText().isBlank() || writePanel.text1.getText().isEmpty()) {
                DialogUtil.generateDialog("写入内容为空！");
                return;
            }
            String writeText = writePanel.text1.getText();
            if (encrypt.get()) {
                writeText = EncryptUtil.xorEncrypt(writeText, key);
            }

            try {
                writer = new FileWriter(file);
                buf_writer = new BufferedWriter(writer);
                buf_writer.write(writeText, 0, writeText.length());
                buf_writer.flush();
                DialogUtil.generateDialog("写入成功！");
            } catch (IOException e) {
                DialogUtil.generateDialog("写入失败！" + e);
                e.printStackTrace();
            }
        });
    }

    private void initRead() {
        operationPanel.read_btn.addActionListener(event -> {
            String s;
            readPanel.text1.setText("");
            try {
                reader = new FileReader(file);
                buf_reader = new BufferedReader(reader);
            } catch (FileNotFoundException e) {
                DialogUtil.generateDialog("读取文件出错！" + e);
                e.printStackTrace();
            }
            try {
                while ((s = buf_reader.readLine()) != null) {
                    if (decrypt.get()) {
                        s = EncryptUtil.xorEncrypt(s, key);
                    }
                    readPanel.text1.append(s + '\n');
                }
                DialogUtil.generateDialog("读取文件成功！");
            } catch (IOException e) {
                DialogUtil.generateDialog("读取文件出错！" + e);
                e.printStackTrace();
            }
        });
    }


}
