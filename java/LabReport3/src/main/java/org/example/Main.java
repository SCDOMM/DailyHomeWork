package org.example;

import org.example.controller.ViewController;
import org.example.view.MainFrame;

import javax.swing.*;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            MainFrame mainFrame=new MainFrame();
            ViewController controller=new ViewController(mainFrame.operationPanel,mainFrame.writePanel,mainFrame.readPanel);
        });


    }
}