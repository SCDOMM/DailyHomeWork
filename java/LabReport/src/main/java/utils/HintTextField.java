package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField {
    private final String hint;
    private final Color textColor;

    public HintTextField(String hint) {
        this.hint = hint;
        this.textColor = getForeground();
        setText(hint);
        setForeground(Color.GRAY);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)){
                    setText("");
                    setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isBlank()){
                    setText(hint);
                    setForeground(Color.GRAY);
                }
            }
        });
    }




}
