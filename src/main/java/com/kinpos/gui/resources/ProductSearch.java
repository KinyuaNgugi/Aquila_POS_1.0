package com.kinpos.gui.resources;

import com.kinpos.gui.PointOfSale;

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
public class ProductSearch extends JFrame {
    // database URL, username and password
    static final String DATABASE_URL = DB_HOST_ADDRESS;
    static final String USERNAME = DB_USER;
    static final String PASSWORD = DB_PASSWORD;
    // default query retrieves all data from stock table
    static final String DEFAULT_QUERY = "SELECT productCode,productName,sellingPricePerUnit from stock";
    private PointOfSale pointOfSale;
    private ResultSetTableModel tableModel;
    private JTable resultTable;
    private String productDesc;
    private String productCode;
    private String price;

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // create ResultSetTableModel and GUI
    public ProductSearch(final PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
        //super("Displaying Product Results");
        // create ResultSetTableModel and display database table
        try {
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
            resultTable.setFont(new Font("Times New Roman", Font.BOLD, 15));
            resultTable.setBackground(Color.LIGHT_GRAY);
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
            //setSize( 700, 450 ); // set window size
            //setVisible( true ); // display window

            ActionListener select = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int column = resultTable.getSelectedColumn();
                    int row = resultTable.getSelectedRow();
                    productCode = resultTable.getValueAt(row, column).toString();
                    setProductCode(productCode);
                    productDesc = resultTable.getValueAt(row, column + 1).toString();
                    setProductDesc(productDesc);
                    price = resultTable.getValueAt(row, column + 2).toString();
                    setPrice(price);
                    System.out.print(productDesc + productCode + price);
                }
            };
            KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true);
            resultTable.registerKeyboardAction(select, ks, JComponent.WHEN_FOCUSED);
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
/*public static void main(String []args){
    new ProductSearch();
}*/

}
