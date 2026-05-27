package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel jPanel1=new JPanel();
    public ReadPanel readPanel=new ReadPanel();
    public WritePanel writePanel=new WritePanel();
    public OperationPanel operationPanel=new OperationPanel();
    public MainFrame(){
        setSize(new Dimension(900,400));
        setLayout(new GridLayout(1,2));
        setVisible(true);
        validate();
        initView();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(jPanel1);
        add(operationPanel);
    }
    private void initView(){
        jPanel1.setLayout(new GridLayout(2,1));
        jPanel1.add(writePanel);
        jPanel1.add(readPanel);
    }




}
