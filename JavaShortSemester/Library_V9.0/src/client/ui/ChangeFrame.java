package client.ui;

import client.model.Book;
import client.model.ReturnResult;
import utils.FrameUtils;

import client.controll.Operator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeFrame extends JFrame{
    public ChangeFrame() {
        setTitle("📚 修改图书");
        setSize(480, 600);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                ChangeFrame.this.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(250, 250, 250));
        add(panel);

        JLabel titleLabel = new JLabel("📖 修改图书信息", SwingConstants.CENTER);
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

        JLabel labelOldName = FrameUtils.createLabel("📕 原图书名称：");
        labelOldName.setBounds(labelX, startY, 100, height);
        panel.add(labelOldName);

        JTextField fieldOldName = FrameUtils.createTextField();
        fieldOldName.setBounds(fieldX, startY, fieldWidth, height);
        panel.add(fieldOldName);


        JLabel labelNewName = FrameUtils.createLabel("📕 新图书名称：");
        labelNewName.setBounds(labelX, startY+gap, 100, height);
        panel.add(labelNewName);

        JTextField fieldNewName = FrameUtils.createTextField();
        fieldNewName.setBounds(fieldX, startY+gap, fieldWidth, height);
        panel.add(fieldNewName);

        JLabel labelAuthor = FrameUtils.createLabel("✍️ 新作者名称：");
        labelAuthor.setBounds(labelX, startY + gap*2, 100, height);
        panel.add(labelAuthor);

        JTextField fieldAuthor = FrameUtils.createTextField();
        fieldAuthor.setBounds(fieldX, startY + gap*2, fieldWidth, height);
        panel.add(fieldAuthor);

        JLabel labelPrice = FrameUtils.createLabel("💰 新图书价格：");
        labelPrice.setBounds(labelX, startY + gap * 3, 100, height);
        panel.add(labelPrice);

        JTextField fieldPrice = FrameUtils.createTextField();
        fieldPrice.setBounds(fieldX, startY + gap * 3, fieldWidth, height);
        panel.add(fieldPrice);

        int btnY = 300;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonSubmit = FrameUtils.createButton("✅ 确定", new Color(70, 130, 180));
        buttonSubmit.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonSubmit);

        buttonSubmit.addActionListener(e -> {
            String oldName = fieldOldName.getText().trim();
            String newName = fieldNewName.getText().trim();
            String author = fieldAuthor.getText().trim();
            String priceStr = fieldPrice.getText().trim();

            if (oldName.isEmpty()||newName.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "书名和作者不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price < 0) {
                    JOptionPane.showMessageDialog(null, "价格不能为负数！", "输入错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "价格必须是数字！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Operator operator = new Operator();
            ReturnResult returnResult=operator.changeBook(oldName,new Book(newName, author, price));

            if (returnResult.isSuccess()) {
                JOptionPane.showMessageDialog(null, "🎉 图书修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                fieldNewName.setText("");
                fieldAuthor.setText("");
                fieldPrice.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "❌ 图书修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonCancel = FrameUtils.createButton("↩️ 返回", new Color(120, 120, 120));
        buttonCancel.setBounds(260, btnY, btnWidth, btnHeight);
        panel.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            ChangeFrame.this.dispose();
        });

        setVisible(true);
    }
}
