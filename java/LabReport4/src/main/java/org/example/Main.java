package org.example;

import org.example.controller.ViewController;
import org.example.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            MainFrame mainFrame=new MainFrame();
            ViewController controller=new ViewController(mainFrame.operationPanel,mainFrame.receivePanel,mainFrame.transmitPanel);
        });
    }
}