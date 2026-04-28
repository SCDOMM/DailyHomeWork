package view;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private static JFrame frame = new JFrame("图形化界面绘制");
    private static JMenuBar jMenuBar = new JMenuBar();
    private static JPanel mainPanel = new JPanel();
    static JMenu menu1 = new JMenu("个人");
    static JMenu menu2 = new JMenu("绘制图形");
    static JMenuItem jMenuItem1 = new JMenuItem("登录");
    static JMenuItem jMenuItem2 = new JMenuItem("注册");
    static JMenuItem jMenuItem3 = new JMenuItem("绘制三角形");

    public static void onCreate() {
        menu1.add(jMenuItem1);
        menu1.add(jMenuItem2);
        menu2.add(jMenuItem3);
        jMenuBar.add(menu1);
        jMenuBar.add(menu2);
        frame.setSize(800, 400);
        frame.setJMenuBar(jMenuBar);
        frame.validate();
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        initEvent();
    }

    public static void initEvent() {
        JTextArea mainText = new JTextArea("7,35");
        mainText.setText("测试");
        mainText.setEditable(false);
        mainPanel.add(mainText);
        frame.add(mainPanel);
        jMenuItem1.addActionListener(event -> {
            frame.getContentPane().removeAll();
            LoginView.onCreate(frame);
        });
        jMenuItem2.addActionListener(event -> {
            frame.getContentPane().removeAll();
            RegisterView.onCreate(frame);
        });
        jMenuItem3.addActionListener(event->{
            frame.getContentPane().removeAll();
            DrawView.onCreate(frame);
        });
    }
}
