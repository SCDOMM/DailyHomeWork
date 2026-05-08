package org.example.view;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private JLabel label1=new JLabel( "         数据接收端        ");
    public static JTextArea text1=new JTextArea(5,20);

    public RightPanel(){
        setSize(300,400);
        setBackground(new Color(255,255,224));
        setLayout(new GridLayout(7,1));

        initView();
        add(label1);
        add(text1);
    }
    private void initView(){
        label1.setFont(new Font("Serif",Font.PLAIN,20));
        text1.setEditable(false);
    }
}
