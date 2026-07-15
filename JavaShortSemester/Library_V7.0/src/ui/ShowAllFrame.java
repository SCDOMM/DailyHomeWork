package ui;

import control.Operator;
import model.Book;
import utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ShowAllFrame extends JFrame {

    private JTextArea textArea;

    public ShowAllFrame() {
        setTitle("📚 查看所有图书");
        setSize(480, 800);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                ShowAllFrame.this.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(250, 250, 250));
        add(panel);

        JLabel titleLabel = new JLabel("📖 查看所有图书信息", SwingConstants.CENTER);
        titleLabel.setBounds(0, 15, 480, 40);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 55, 95));
        panel.add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(40, 60, 400, 2);
        separator.setForeground(new Color(200, 200, 200));
        panel.add(separator);

        JLabel labelBooks = FrameUtils.createLabel("✍️ 当前书籍：");
        labelBooks.setBounds(60, 100, 100, 35);
        panel.add(labelBooks);

        JTextArea areaBooks = FrameUtils.createTextArea(20,20);
        areaBooks.setEditable(false);
        areaBooks.setBounds(160,100, 300, 400);
        // ===== 新增：用 JScrollPane 包裹 JTextArea，支持滚动条 =====
        JScrollPane scrollPane = new JScrollPane(areaBooks);
        scrollPane.setBounds(160, 100, 300, 400);
        panel.add(scrollPane);    // 改为添加 scrollPane 到面板
        // ===== 结束 =====

        areaBooks.setText(loadBooks());


        int btnY = 520;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonRefresh = FrameUtils.createButton("✅ 刷新", new Color(70, 130, 180));
        buttonRefresh.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonRefresh);

        buttonRefresh.addActionListener(e -> {
            areaBooks.setText(loadBooks());
        });

        JButton buttonCancel = FrameUtils.createButton("↩️ 返回", new Color(120, 120, 120));
        buttonCancel.setBounds(260, btnY, btnWidth, btnHeight);
        panel.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            ShowAllFrame.this.dispose();
        });

        setVisible(true);
    }

    private String loadBooks() {
        Operator operator=new Operator();
        ArrayList<Book> bookList=operator.showAllBooks();
        StringBuilder sb = new StringBuilder("图书信息：\n");
        if (bookList.isEmpty()) {
            sb.append("暂无图书数据");
        } else {
            for (Book book : bookList) {
                sb.append(book.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}