package client.ui;


import client.controll.Operator;
import client.model.Book;
import utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SearchFrame extends JFrame {
    public SearchFrame() {
        setTitle("📚 查询图书");
        setSize(480, 400);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                SearchFrame.this.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(250, 250, 250));
        add(panel);

        JLabel titleLabel = new JLabel("📖 添加图书信息", SwingConstants.CENTER);
        titleLabel.setBounds(0, 15, 480, 40);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 55, 95));
        panel.add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(40, 60, 400, 2);
        separator.setForeground(new Color(200, 200, 200));
        panel.add(separator);

        int labelX = 60;
        int fieldX = 160;
        int fieldWidth = 260;
        int height = 35;
        int startY = 85;
        int gap = 55;

        JLabel labelName = FrameUtils.createLabel("📕 图书名称：");
        labelName.setBounds(labelX, startY, 100, height);
        panel.add(labelName);

        JTextField fieldName = FrameUtils.createTextField();
        fieldName.setBounds(fieldX, startY, fieldWidth, height);
        panel.add(fieldName);

        JLabel labelResult = FrameUtils.createLabel("✍️ 搜索结果：");
        labelResult.setBounds(labelX, startY + gap, 100, height*2);
        panel.add(labelResult);

        JTextArea areaResult = FrameUtils.createTextArea(3,20);
        areaResult.setEditable(false);
        areaResult.setBounds(fieldX, startY + gap, fieldWidth, height*2);
        panel.add(areaResult);

        JCheckBox checkBoxAuthor = new JCheckBox("按作者搜索");
        checkBoxAuthor.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        checkBoxAuthor.setBounds(labelX-20, startY + 140, 120, height);
        checkBoxAuthor.setBackground(new Color(250, 250, 250)); // 与面板背景一致
        panel.add(checkBoxAuthor);


        JTextField fieldAuthor = FrameUtils.createTextField();
        fieldAuthor.setBounds(fieldX, startY + 140, fieldWidth, height);
        panel.add(fieldAuthor);
        fieldAuthor.setEditable(checkBoxAuthor.isSelected());

        int btnY = 280;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonSubmit = FrameUtils.createButton("✅ 确定", new Color(70, 130, 180));
        buttonSubmit.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonSubmit);

        buttonSubmit.addActionListener(e -> {
            areaResult.setText("");
            boolean isSelected=checkBoxAuthor.isSelected();

            if (!isSelected) {
                String name = fieldName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "书名不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Operator operator = new Operator();
                Book searchContent = operator.searchBook(name);
                areaResult.setText("找到图书：" + searchContent.getName()
                        + "\n作者：" + searchContent.getAuthor()
                        + "\n价格：" + searchContent.getPrice());
            }else {
                String author=fieldAuthor.getText().trim();
                if (author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "书名不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Operator operator = new Operator();
                Book searchContent = operator.searchBookByAuthor(author);
                areaResult.setText("找到图书：" + searchContent.getName()
                        + "\n作者：" + searchContent.getAuthor()
                        + "\n价格：" + searchContent.getPrice());
            }

        });

        JButton buttonCancel = FrameUtils.createButton("↩️ 返回", new Color(120, 120, 120));
        buttonCancel.setBounds(260, btnY, btnWidth, btnHeight);
        panel.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            SearchFrame.this.dispose();
        });

        checkBoxAuthor.addActionListener(e -> {
            boolean isSelected=checkBoxAuthor.isSelected();
            fieldAuthor.setEditable(isSelected);
            fieldName.setEditable(!isSelected);
        });

        setVisible(true);
    }


}
