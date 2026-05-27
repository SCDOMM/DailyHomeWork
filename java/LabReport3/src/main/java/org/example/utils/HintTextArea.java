package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextArea extends JTextArea {
    private String hint;
    private Color textColor;

    public HintTextArea(String hint,int rows,int columns) {
        this.hint = hint;
        this.textColor = getForeground();
        setColumns(columns);
        setRows(rows);
        setText(hint);
        setForeground(Color.GRAY);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isBlank()) {
                    setText("");
                    setForeground(Color.GRAY);
                }
            }
        });

    }


}
