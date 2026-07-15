package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import control.Operator;
import model.Book;
import utils.FrameUtils;

public class AddFrame extends JFrame {

    public AddFrame() {
        setTitle("📚 添加图书");
        setSize(480, 400);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                AddFrame.this.dispose();
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

        JLabel labelAuthor = FrameUtils.createLabel("✍️ 作者名称：");
        labelAuthor.setBounds(labelX, startY + gap, 100, height);
        panel.add(labelAuthor);

        JTextField fieldAuthor = FrameUtils.createTextField();
        fieldAuthor.setBounds(fieldX, startY + gap, fieldWidth, height);
        panel.add(fieldAuthor);

        JLabel labelPrice = FrameUtils.createLabel("💰 图书价格：");
        labelPrice.setBounds(labelX, startY + gap * 2, 100, height);
        panel.add(labelPrice);

        JTextField fieldPrice = FrameUtils.createTextField();
        fieldPrice.setBounds(fieldX, startY + gap * 2, fieldWidth, height);
        panel.add(fieldPrice);

        int btnY = 260;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonSubmit = FrameUtils.createButton("✅ 确定", new Color(70, 130, 180));
        buttonSubmit.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonSubmit);

        buttonSubmit.addActionListener(e -> {
            String name = fieldName.getText().trim();
            String author = fieldAuthor.getText().trim();
            String priceStr = fieldPrice.getText().trim();

            if (name.isEmpty() || author.isEmpty()) {
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

            Book book = new Book(name, author, price);
            Operator operator = new Operator();
            boolean isSuccess = operator.addBook(book);

            if (isSuccess) {
                JOptionPane.showMessageDialog(null, "🎉 图书添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                fieldName.setText("");
                fieldAuthor.setText("");
                fieldPrice.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "❌ 图书添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonCancel = FrameUtils.createButton("↩️ 返回", new Color(120, 120, 120));
        buttonCancel.setBounds(260, btnY, btnWidth, btnHeight);
        panel.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            AddFrame.this.dispose();
        });

        JLabel tipLabel = new JLabel("💡 提示：输入完成后点击\"确定\"按钮保存", SwingConstants.CENTER);
        tipLabel.setBounds(0, 320, 480, 25);
        tipLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        tipLabel.setForeground(new Color(150, 150, 150));
        panel.add(tipLabel);

        setVisible(true);
    }
}