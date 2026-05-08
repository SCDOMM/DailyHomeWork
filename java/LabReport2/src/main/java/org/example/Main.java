package org.example;

import org.example.view.DataTransferApp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            DataTransferApp app=new DataTransferApp();
        });
    }
}