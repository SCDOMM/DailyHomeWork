package org.example.view;

import javax.swing.*;
import java.awt.*;

public class DataTransferApp extends JFrame {
    public LeftPanel leftPanel=new LeftPanel();
    public CenterPanel centerPanel=new CenterPanel();
    public RightPanel rightPanel=new RightPanel();
    public DataTransferApp(){
        setLayout(new BorderLayout());
        setSize(900,400);
        add(leftPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        setVisible(true);

        setTitle("模拟采集程序");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        validate();
    }






}
