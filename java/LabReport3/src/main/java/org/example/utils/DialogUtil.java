package org.example.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void generateDialog(String txt) {
        JDialog jDialog = new JDialog();
        jDialog.setTitle("提示！");
        jDialog.setModal(true);
        jDialog.setSize(300, 100);
        jDialog.setLocationRelativeTo(null);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        jDialog.setLayout(new FlowLayout());
        JTextField textField = new JTextField(txt);
        textField.setEditable(false);
        jDialog.add(textField);
        jDialog.validate();
        jDialog.setVisible(true);
    }

    public static String generateInputDialog(String title, String text) {
        JTextField textField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel(text), BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 边距
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            return textField.getText().trim();
        } else {
            return null;
        }
    }
}
