package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public OperationPanel operationPanel=new OperationPanel();
    public ReceivePanel receivePanel =new ReceivePanel();
    public TransmitPanel transmitPanel=new TransmitPanel();
    public MainFrame(){
        setTitle("TCP通讯工具");
        setSize(new Dimension(900,400));
        setLayout(new BorderLayout(30,5));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(operationPanel,BorderLayout.NORTH);
        add(receivePanel,BorderLayout.CENTER);
        add(transmitPanel,BorderLayout.SOUTH);
    }

}
