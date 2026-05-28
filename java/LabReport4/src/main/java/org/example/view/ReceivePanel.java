package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ReceivePanel extends JPanel {
    public JTextArea textArea=new JTextArea(5,20);
    public ReceivePanel(){
        setBackground(new Color(248, 248, 255));
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("接收区 (服务器消息)"));
        add(scrollPane, BorderLayout.CENTER);
    }
}
