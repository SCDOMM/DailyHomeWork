package org.example.view;

import javax.swing.*;
import java.awt.*;

public class TransmitPanel extends JPanel {
    public JTextArea textArea = new JTextArea(5, 20);
    public JButton sendButton = new JButton("Send Data");
    public TransmitPanel() {
        setBackground(new Color(187, 255, 255));
        setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("发送区 (输入消息)"));
        add(scrollPane, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);
    }
}
