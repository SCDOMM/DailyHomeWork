package client.ui;

import client.controll.Operator;
import client.model.Book;
import utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ShowAllFrame extends JFrame {

    private JTable textTable;          // 原 JTextArea → JTable，变量名不变
    private DefaultTableModel tableModel;
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

        // ===== 改为 JTable 显示 =====
        String[] columnNames = {"编号", "书名", "作者", "价格", "库存"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 不可编辑
            }
        };
        JTable areaBooks = new JTable(tableModel);   // 变量名保留 areaBooks，类型改为 JTable
        areaBooks.setRowHeight(28);
        areaBooks.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 13));
        areaBooks.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        areaBooks.getTableHeader().setBackground(new Color(230, 240, 250));
        areaBooks.setSelectionBackground(new Color(180, 210, 240));

        // 设置列宽
        areaBooks.getColumnModel().getColumn(0).setPreferredWidth(40);
        areaBooks.getColumnModel().getColumn(1).setPreferredWidth(120);
        areaBooks.getColumnModel().getColumn(2).setPreferredWidth(60);
        areaBooks.getColumnModel().getColumn(3).setPreferredWidth(40);
        areaBooks.getColumnModel().getColumn(4).setPreferredWidth(40);

        JScrollPane scrollPane = new JScrollPane(areaBooks);
        scrollPane.setBounds(160, 100, 300, 400);
        panel.add(scrollPane);

        // 加载数据
        loadBooks();

        int btnY = 520;
        int btnWidth = 120;
        int btnHeight = 40;

        JButton buttonRefresh = FrameUtils.createButton("✅ 刷新", new Color(70, 130, 180));
        buttonRefresh.setBounds(100, btnY, btnWidth, btnHeight);
        panel.add(buttonRefresh);

        buttonRefresh.addActionListener(e -> {
            loadBooks(); // 刷新表格数据
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

    private void loadBooks() {
        tableModel.setRowCount(0); // 清空旧数据
        Operator operator = new Operator();
        ArrayList<Book> bookList = operator.showAllBooks();
        if (bookList.isEmpty()) {
        } else {
            for (Book book : bookList) {
                tableModel.addRow(new Object[]{
                        book.getName(),
                        book.getAuthor(),
                        book.getPrice(),
                });
            }
        }
    }
}