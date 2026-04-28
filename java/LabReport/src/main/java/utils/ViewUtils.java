package utils;

import javax.swing.*;
import java.awt.*;

public class ViewUtils {
    public static void generateDialog(String txt){
        JDialog jDialog=new JDialog();
        jDialog.setTitle("提示！");
        jDialog.setModal(true);
        jDialog.setSize(300,100);
        jDialog.setLocationRelativeTo(null);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        jDialog.setLayout(new FlowLayout());
        jDialog.add(new JTextField(txt));
        jDialog.validate();
        jDialog.setVisible(true);
    }
}
