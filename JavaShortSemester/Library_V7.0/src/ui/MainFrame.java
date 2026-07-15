package ui;


import control.Operator;
import utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("📚 图书管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        add(panel);

        JLabel titleLabel = new JLabel("📖 图书管理系统", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 450, 50);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 55, 95));
        panel.add(titleLabel);

        JLabel subLabel = new JLabel("欢迎使用，请选择操作", SwingConstants.CENTER);
        subLabel.setBounds(0, 70, 450, 30);
        subLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        subLabel.setForeground(new Color(100, 100, 100));
        panel.add(subLabel);

        int btnWidth = 140;
        int btnHeight = 45;

        JButton buttonAdd = FrameUtils.createButton("➕ 添加图书", new Color(70, 130, 180));
        buttonAdd.setBounds(60, 120, btnWidth, btnHeight);
        panel.add(buttonAdd);
        buttonAdd.addActionListener(e -> {
            AddFrame addFrame = new AddFrame();
            addFrame.setVisible(true);
            MainFrame.this.dispose();
        });

        JButton buttonDelete = FrameUtils.createButton("🗑️ 删除图书", new Color(220, 80, 80));
        buttonDelete.setBounds(250, 120, btnWidth, btnHeight);
        panel.add(buttonDelete);
        buttonDelete.addActionListener(e->{
            ui.DeleteFrame deleteFrame = new DeleteFrame();
            deleteFrame.setVisible(true);
            MainFrame.this.dispose();
        });

        JButton buttonChange = FrameUtils.createButton("✏️ 修改图书", new Color(255, 165, 0));
        buttonChange.setBounds(60, 185, btnWidth, btnHeight);
        panel.add(buttonChange);
        buttonChange.addActionListener(e -> {
            ChangeFrame changeFrame=new ChangeFrame();
            changeFrame.setVisible(true);
            MainFrame.this.dispose();
        });

        JButton buttonSearch = FrameUtils.createButton("🔍 查询图书", new Color(60, 179, 113));
        buttonSearch.setBounds(250, 185, btnWidth, btnHeight);
        panel.add(buttonSearch);
        buttonSearch.addActionListener(e -> {
            SearchFrame searchFrame=new SearchFrame();
            searchFrame.setVisible(true);
            MainFrame.this.dispose();
        });


        JButton buttonShowAll = FrameUtils.createButton("📋 显示全部", new Color(147, 112, 219));
        buttonShowAll.setBounds(60, 250, btnWidth, btnHeight);
        panel.add(buttonShowAll);
        buttonShowAll.addActionListener(e -> {
            ShowAllFrame showAllFrame=new ShowAllFrame();
            showAllFrame.setVisible(true);
            MainFrame.this.dispose();
        });

        JButton buttonExit = FrameUtils.createButton("🚪 退出系统", new Color(80, 80, 80));
        buttonExit.setBounds(250, 250, btnWidth, btnHeight);
        panel.add(buttonExit);
        buttonExit.addActionListener(e -> {
            System.exit(0);
        });

        JTextField textStatistics = FrameUtils.createTextField();
        textStatistics.setBounds(250, 315, 150, 50);
        panel.add(textStatistics);

        JButton buttonStatistics = FrameUtils.createButton("📋 统计图书", new Color(147, 112, 219));
        buttonStatistics.setBounds(60, 315, btnWidth, btnHeight);
        panel.add(buttonStatistics);
        buttonStatistics.addActionListener(e -> {
            Operator operator=new Operator();
            textStatistics.setText(operator.showStatistics());
        });

        JLabel footerLabel = new JLabel("Java Swing GUI 演示", SwingConstants.CENTER);
        footerLabel.setBounds(0, 360, 450, 25);
        footerLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(150, 150, 150));
        panel.add(footerLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainFrame();
    }
}
