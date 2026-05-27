package org.example.view;

import javax.swing.*;
import java.awt.*;

public class OperationPanel extends JPanel {
    public JLabel label1=new JLabel("请输入文件名(包含后缀)");
    public JTextField nameText =new JTextField(10);
    public JButton create_btn =new JButton("创建/加载文件");
    public JCheckBox encrypt_cb =new JCheckBox("加密");
    public JButton write_btn =new JButton("按字符写入");
    public JCheckBox decrypt_cb =new JCheckBox("解密");
    public JButton read_btn =new JButton("按字符读出");

    public OperationPanel(){
        setLayout(new FlowLayout());
        setBackground(new Color(240,248,255));
        initView();


    }
    private void initView(){
        JPanel jPanel1=new JPanel();
        jPanel1.setLayout(new GridLayout(1,3));
        jPanel1.add(label1);
        jPanel1.add(nameText);
        jPanel1.add(create_btn);
        add(jPanel1);

        JPanel jPanel2=new JPanel();
        jPanel2.setLayout(new GridLayout(1,2));
        jPanel2.add(encrypt_cb);
        jPanel2.add(write_btn);
        add(jPanel2);

        JPanel jPanel3=new JPanel();
        jPanel3.setLayout(new GridLayout(1,2));
        jPanel3.add(decrypt_cb);
        jPanel3.add(read_btn);
        add(jPanel3);
    }

}
