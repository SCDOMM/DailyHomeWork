package org.example.view;

import org.example.utils.HintTextArea;

import javax.swing.*;
import java.awt.*;

public class WritePanel extends JPanel {
    public HintTextArea text1=new HintTextArea("数据写入区",15,40);
    public WritePanel(){
        setBackground(new Color(248,248,255));
        setLayout(new FlowLayout());
        add(text1);
    }

}
