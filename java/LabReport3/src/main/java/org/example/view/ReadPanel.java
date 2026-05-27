package org.example.view;

import org.example.utils.HintTextField;

import javax.swing.*;
import java.awt.*;

public class ReadPanel extends JPanel {
    public JTextArea text1=new JTextArea("数据读取区",15,40);
    public ReadPanel(){
        setBackground(new Color(187,255,255));
        setLayout(new FlowLayout());

        text1.setEditable(false);
        add(text1);
    }
}
