package org.example.view;

import org.example.utils.Transmit;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private JLabel jLabel=new JLabel("数据传输");
    public static JProgressBar progressBar=new JProgressBar(0,100);

    public CenterPanel(){
        setSize(100,400);
        setBackground(new Color(	255,240,245));
        setLayout(new GridLayout(3,1));

        initLabel();
        add(jLabel);
        add(progressBar);
    }
    private void initLabel(){
        jLabel.setFont(new Font("Serif",Font.PLAIN,20));
    }
    public static void generateTransmit(int tempData){
        Transmit thread=new Transmit(tempData);
        thread.run();
        System.out.println("start");

    }

}
