package ui;

import control.Operator;
import utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DeleteFrame extends JFrame{
    public DeleteFrame(){
        setTitle("📚 删除图书");
        setSize(480, 400);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                DeleteFrame.this.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(250, 250, 250));
        add(panel);

        JLabel titleLabel = new JLabel("📖 输入图书姓名", SwingConstants.CENTER);
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

        JLabel labelName = createLabel("📕 图书名称：");
        labelName.setBounds(labelX, startY, 100, height);
        panel.add(labelName);

        JTextField fieldName = FrameUtils.createTextField();
        fieldName.setBounds(fieldX, startY, fieldWidth, height);
        panel.add(fieldName);

        int btnY = 260;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonSubmit = FrameUtils.createButton("✅ 确定", new Color(70, 130, 180));
        buttonSubmit.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonSubmit);

        buttonSubmit.addActionListener(e -> {
            String name = fieldName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "书名和作者不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Operator operator=new Operator();
            if (operator.deleteBook(name)) {
                JOptionPane.showMessageDialog(null, "🎉 图书删除功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                fieldName.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "❌ 删除失败！", "失败", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonCancel = FrameUtils.createButton("↩️ 返回", new Color(120, 120, 120));
        buttonCancel.setBounds(260, btnY, btnWidth, btnHeight);
        panel.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            DeleteFrame.this.dispose();
        });

        setVisible(true);
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }



}
