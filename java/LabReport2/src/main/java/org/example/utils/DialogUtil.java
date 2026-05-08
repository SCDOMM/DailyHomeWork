package org.example.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void generateDialog(String txt){
        JDialog jDialog=new JDialog();
        jDialog.setTitle("提示！");
        jDialog.setModal(true);
        jDialog.setSize(300,100);
        jDialog.setLocationRelativeTo(null);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        jDialog.setLayout(new FlowLayout());
        JTextField textField=new JTextField(txt);
        textField.setEditable(false);
        jDialog.add(textField);
        jDialog.validate();
        jDialog.setVisible(true);
    }
}
