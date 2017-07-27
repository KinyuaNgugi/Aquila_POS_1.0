package com.kinpos.gui.today;

import com.kinpos.dao.RunDateDAO;
import com.kinpos.dao.hibernate.HibernateRunDateDAO;
import com.kinpos.gui.resources.ResultSetTableModel;
import com.kinpos.models.RunDateTableEntity;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import static com.kinpos.gui.resources.Constants.DB_HOST_ADDRESS;
import static com.kinpos.gui.resources.Constants.DB_USER;
import static com.kinpos.gui.resources.Constants.DB_PASSWORD;
import static com.kinpos.gui.resources.Constants.TILL_ID;

public class ReceiptToday extends JFrame {
    // database URL, username and password
    static final String DATABASE_URL = DB_HOST_ADDRESS;
    static final String USERNAME = DB_USER;
    static final String PASSWORD = DB_PASSWORD;

    public final RunDateDAO runDateService=new HibernateRunDateDAO();
    String runDateActual;
    private ResultSetTableModel tableModel;
    private JTable resultTable;

    // create ResultSetTableModel and GUI
    public ReceiptToday() {
        super("Displaying Petty Cash Payments for Today");
        // create ResultSetTableModel and display database table
        try {
            //get run date
            java.util.List<RunDateTableEntity> runDates = runDateService.getMyActiveRunDate();
            for (RunDateTableEntity runDate : runDates)
            {
                runDateActual = runDate.getRunDate().toString();
            }
            String DEFAULT_QUERY = "SELECT actualDate,receiptNumber,receiptTotal FROM income " +
                    "WHERE runDate="+"'"+runDateActual+"'AND zedClear='0' AND tillId="+TILL_ID+ " ORDER BY receiptNumber";
            // create TableModel for results of query SELECT * FROM stock
            tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
            JScrollPane scrollPane = new JScrollPane(
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            Box boxNorth = Box.createHorizontalBox();
            boxNorth.add(scrollPane);

            // create JTable based on the tableModel
            resultTable = new JTable(tableModel);
            resultTable.setShowVerticalLines(false);
            resultTable.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 11));
            resultTable.setBackground(Color.cyan);
            resultTable.setShowGrid(false);
            JLabel filterLabel = new JLabel("Filter:");
            final JTextField filterText = new JTextField(30);
            Box boxSouth = Box.createHorizontalBox();
            boxSouth.add(filterLabel);
            boxSouth.add(filterText);

            // place GUI components on content pane
            add(boxNorth, BorderLayout.SOUTH);
            add(new JScrollPane(resultTable), BorderLayout.CENTER);
            add(boxSouth, BorderLayout.NORTH);
            final TableRowSorter<TableModel> sorter =
                    new TableRowSorter<TableModel>(tableModel);
            resultTable.setRowSorter(sorter);
            setSize(700, 450); // set window size
            setVisible(true); // display window

            //ActionListener that handles productsearch exit
            final ActionListener ext= new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    setVisible(false);
                }
            };

            KeyStroke ksx = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
            filterText.registerKeyboardAction(ext, ksx, JComponent.WHEN_IN_FOCUSED_WINDOW);
            //ActionListener that handles productsearch exit
            final ActionListener ext1= new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    setVisible(false);
                }
            };

            KeyStroke ksx1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
            resultTable.registerKeyboardAction(ext1, ksx1, JComponent.WHEN_IN_FOCUSED_WINDOW);
            filterText.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent keyEvent) {
                            String text = filterText.getText();
                            if (text.length() == 0)
                                sorter.setRowFilter(null);
                            else {
                                try {
                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(text));
                                } catch (PatternSyntaxException pse) {
                                    JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }

                        @Override
                        public void keyPressed(KeyEvent keyEvent) {
                            String text = filterText.getText();
                            if (text.length() == 0)
                                sorter.setRowFilter(null);
                            else {
                                try {
                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(text));
                                } catch (PatternSyntaxException pse) {
                                    JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent keyEvent) {
                            String text = filterText.getText();
                            if (text.length() == 0)
                                sorter.setRowFilter(null);
                            else {
                                try {
                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(text));
                                } catch (PatternSyntaxException pse) {
                                    JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
            );

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
            // ensure database connection is closed
            tableModel.disconnectFromDatabase();
            System.exit(1);
        }

// dispose of window when user quits application (this overrides
// the default of HIDE_ON_CLOSE)
        //setDefaultCloseOperation( EXIT_ON_CLOSE );

        // ensure database connection is closed when user quits application
        addWindowListener(
                new WindowAdapter() {
                    // disconnect from database and exit when window has closed
                    public void windowClosed(WindowEvent event) {
                        tableModel.disconnectFromDatabase();
                        System.exit(0);
                    }
                }
        );
    }
}
