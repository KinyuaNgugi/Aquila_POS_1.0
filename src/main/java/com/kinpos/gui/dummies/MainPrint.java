package com.kinpos.gui.dummies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by kinyua on 8/26/15.
 */
public class MainPrint {

    public static void main(String args[]) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Print UI Example");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JTextArea text = new JTextArea(50, 20);
        for (int i=1;i<=50;i++) {
            text.append("Line " + i + "\n");
        }
        JScrollPane pane = new JScrollPane(text);
        pane.setPreferredSize(new Dimension(250,200));
        f.add("Center", pane);
        JButton printButton = new JButton("Print This Window");
        //printButton.addActionListener(new PrintReceipt(f));
        f.add("South", printButton);
        f.pack();
        f.setVisible(true);
    }
}
