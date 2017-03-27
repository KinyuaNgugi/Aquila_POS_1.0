package com.kinpos.gui.dummies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class AutoFilter extends JFrame {

    private JList userList = null;
    private DefaultListModel model = null;
    private DefaultListModel filteredModel = null;
    private JTextField usernameTextField = null;
    private int fieldLength = 0;

    public AutoFilter() throws HeadlessException {

        setSize(400, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// We have to set Layout to null to manually set bounds to our components...
        setLayout(null);

        JLabel usernameLabel = new JLabel("Enter Name : ");
        usernameLabel.setBounds(10, 10, 200, 20);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(120, 10, 200, 20);

// Here we put values to to the JList...
// For an instance these values you can get from the database..??
        model = new DefaultListModel();
        model.addElement("One");
        model.addElement("Two");
        model.addElement("Three");
        model.addElement("Four");
        model.addElement("Five");
        model.addElement("Six");
        model.addElement("Seven");
        model.addElement("Eight");
        model.addElement("Nine");
        model.addElement("Ten");

        userList = new JList(model);
        userList.setBounds(10, 50, 300, 200);

// Adding our components to the JFrame...
        add(usernameLabel);
        add(usernameTextField);
        add(userList);

// We have to add a KeyListner for the text field.
// It will capture the key events for the text field.
        usernameTextField.addKeyListener(new KeyAdapter() {

            // Here is the implementation of keyReleased method
            public void keyReleased(KeyEvent e) {

// In case of deleting a previously entered character by hitting backspace maybe...
                if (fieldLength > usernameTextField.getText().length()) {
                    userList.setModel(model);
                    filterList();
                } else {
                    filterList();
                }

            }

            // Here is the implementation of keyReleased method
            public void keyPressed(KeyEvent e) {

// Just getting the length of the text field
// before key is pressed...
                fieldLength = usernameTextField.getText().length();
            }
        });
    }


    // Welcome to the magic part... ;)
    private void filterList() {

// Setting up the environment for the logic
        int start = 0;
        int itemIx = 0;

// Here the glitch that one should remeber
// Sets hold NO DUPLICATE values... :)
        Set resultSet = new HashSet();

        filteredModel = new DefaultListModel();

// Following logic is used to find an item in JList
        String prefix = usernameTextField.getText();

        javax.swing.text.Position.Bias direction = javax.swing.text.Position.Bias.Forward;

        for (int i = 0; i < userList.getModel().getSize(); i++) {
            itemIx = userList.getNextMatch(prefix, start, direction);

// Following try-catch blog will enhance the user friendliness
            try {
                resultSet.add(userList.getModel().getElementAt(itemIx));
            } catch(ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "No entry is matched with your query....");
                usernameTextField.setText("");
                return;
            }

            start++;
        }

        Iterator itr = resultSet.iterator();

// Adding the filtered results to the new model
        while (itr.hasNext()) {
            filteredModel.addElement(itr.next());
        }

// Setting the model to the list again
        userList.setModel(filteredModel);
    }

    public static void main(String[] args) {
        new AutoFilter().setVisible(true);
    }
} 