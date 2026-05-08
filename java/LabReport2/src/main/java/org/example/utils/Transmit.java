package org.example.utils;

import org.example.view.CenterPanel;
import org.example.view.RightPanel;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Transmit {
    private int tempData;
    private int delay = 100;
    private Timer timer;
    AtomicInteger value = new AtomicInteger(CenterPanel.progressBar.getValue());

    public Transmit(int tempData) {
        this.tempData = tempData;
    }

    public void run() {

        timer = new Timer(delay, e -> {
            if (value.get() < 100) {
                 CenterPanel.progressBar.setValue(value.getAndIncrement());
                System.out.println(value);
            } else {
                timer.stop();
                synchronized (ShareResource.LOCK) {
                    if (ShareResource.isRunning) ShareResource.LOCK.notify();
                }
                CenterPanel.progressBar.setValue(0);
                RightPanel.text1.setText("数据是" + tempData);
            }
        });
        timer.start();
    }
}
