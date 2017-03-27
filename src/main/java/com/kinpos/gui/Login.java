package com.kinpos.gui;

import com.kinpos.dao.RunDateDAO;
import com.kinpos.dao.UserDAO;
import com.kinpos.dao.hibernate.HibernateRunDateDAO;
import com.kinpos.dao.hibernate.HibernateUserDAO;
import com.kinpos.models.RunDateTableEntity;
import com.kinpos.models.UserEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kinyua on 7/25/15.
 */
public class Login extends JFrame{

    public final RunDateDAO runDateService=new HibernateRunDateDAO();
    String runDateActual;

    public final UserDAO userService=new HibernateUserDAO();
    List<String> userList=new LinkedList<String>();
    // create ResultSetTableModel and GUI
    public Login() {
        super("Aquila 1.0");
        // create ResultSetTableModel and display database table
        try {
            //get run date
            java.util.List<RunDateTableEntity> runDates = runDateService.getAllMyRunDates();
            for (RunDateTableEntity runDate : runDates) {
                if (runDate.getActiveStatus()) {
                    runDateActual = runDate.getRunDate().toString();
                }
            }
            //get all users
            final java.util.List<UserEntity> userEntityList = userService.getAllMyUsers();
            for (UserEntity user : userEntityList) {
                if (user.getOperationStatus())
                    if (user.getRole().equals("pos") || user.getRole().equals("both")) {
                        userList.add(user.getUserName());
                    }
            }
            JLabel welcomeMsg = new JLabel("Welcome To EdenMart Supermarket Login Page");
            JLabel log = new JLabel("Please enter your credentials to access the system");


            JPanel top = new JPanel();
            top.setLayout(new GridLayout(2, 2, 3, 3));
            Box boxNorth = Box.createHorizontalBox();
            top.add(welcomeMsg);
            top.add(log);
            boxNorth.add(top);


            JPanel bot = new JPanel();
            bot.setLayout(new GridLayout(4, 4, 3, 3));
            JLabel copyright = new JLabel("Copyright 2015 KINPOS");
            JLabel contacts = new JLabel("Phone Number :(+254) 727 669 491");
            JLabel email = new JLabel("Email: kinyuag92@gmail.com");
            JButton submit = new JButton("Login");
            Box boxSouth = Box.createHorizontalBox();
            bot.add(submit);
            bot.add(copyright);
            bot.add(contacts);
            bot.add(email);
            boxSouth.add(bot);

            JPanel panel = new JPanel();
            panel.setBackground(Color.yellow);
            panel.setLayout(new GridLayout(2, 2, 6, 6));
            final JComboBox usersCombo = new JComboBox(userList.toArray());
            final JPasswordField passwordField = new JPasswordField(20);
            JLabel user = new JLabel("Choose Your Username");
            user.setFont(new Font("Serif", Font.BOLD, 15));
            final JLabel pass = new JLabel("Enter password");
            pass.setFont(new Font("Serif", Font.BOLD, 15));
            panel.add(user);
            panel.add(usersCombo);
            panel.add(pass);
            panel.add(passwordField);
            // place GUI components on content pane
            add(boxNorth, BorderLayout.NORTH);
            add(panel, BorderLayout.CENTER);
            add(boxSouth, BorderLayout.SOUTH);
            setSize(700, 450); // set window size
            setResizable(false);
            setVisible(true); // display window
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            submit.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            List<UserEntity> userEntityL = userService.getMyUserByUserName(usersCombo.getSelectedItem().toString());
                            for (UserEntity u : userEntityL) {

                                if (passwordField.getText().equals(u.getPassword())) {
                                    new FrontEndHome(u);
                                    setVisible(false);
                                    break;
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "incorrect password for user "+usersCombo.getSelectedItem());
                                    passwordField.setText("");
                                    break;
                                }
                            }
                        }
                    });
            ActionListener registerCode = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    List<UserEntity> userEntityL = userService.getMyUserByUserName(usersCombo.getSelectedItem().toString());
                    for (UserEntity u : userEntityL) {

                        if (passwordField.getText().equals(u.getPassword())) {
                            new FrontEndHome(u);
                            setVisible(false);
                            break;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "incorrect password for user "+usersCombo.getSelectedItem());
                            passwordField.setText("");
                            break;
                        }
                    }
                }
            };
            KeyStroke ksEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
            bot.registerKeyboardAction(registerCode, ksEnter, JComponent.WHEN_IN_FOCUSED_WINDOW);

        } catch (Exception ex) {
            System.out.print(ex.getLocalizedMessage());
        }
    }

    public static void main (String args[]){
        new Login();
    }
}

