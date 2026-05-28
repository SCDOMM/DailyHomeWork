package org.example.view;

import javax.swing.*;
import java.awt.*;

public class OperationPanel extends JPanel {
    private JLabel modeLabel = new JLabel("Working mode");
    public JRadioButton clientRadio = new JRadioButton("TCP Client",true);
    public JTextField serverAddrField = new JTextField("未配置", 20);
    public JButton connectButton = new JButton("Connect to/Disconnect from server");

    public OperationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 8));
        setBackground(new Color(240, 248, 255));
        initView();
        serverAddrField.setEditable(false);
        serverAddrField.setBackground(Color.WHITE);
    }

    public void initView() {
        add(modeLabel);
        add(clientRadio);
        add(new JLabel("Server IP:Port:"));
        add(serverAddrField);
        add(connectButton);
    }


}
