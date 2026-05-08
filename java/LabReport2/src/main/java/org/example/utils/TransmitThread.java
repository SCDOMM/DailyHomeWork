package org.example.utils;

import org.example.view.CenterPanel;
import org.example.view.RightPanel;

import javax.swing.*;

public class TransmitThread extends Thread {
    private int tempData;

    public TransmitThread(int tempData) {
        this.tempData = tempData;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            SwingUtilities.invokeLater(()->CenterPanel.progressBar.setValue(finalI));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SwingUtilities.invokeLater(()->CenterPanel.progressBar.setValue(0));
        SwingUtilities.invokeLater(()-> RightPanel.text1.setText("数据是"+tempData));
    }
}
