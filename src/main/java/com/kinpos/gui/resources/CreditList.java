package com.kinpos.gui.resources;

import com.kinpos.dao.SaleDAO;
import com.kinpos.dao.StockDAO;
import com.kinpos.dao.hibernate.HibernateSaleDAO;
import com.kinpos.dao.hibernate.HibernateStockDAO;
import com.kinpos.models.IncomeItemsEntity;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static com.kinpos.gui.resources.Constants.DB_HOST_ADDRESS;
import static com.kinpos.gui.resources.Constants.DB_USER;
import static com.kinpos.gui.resources.Constants.DB_PASSWORD;

public class CreditList extends JFrame {
    // database URL, username and password
    static final String DATABASE_URL = DB_HOST_ADDRESS;
    static final String USERNAME = DB_USER;
    static final String PASSWORD = DB_PASSWORD;
    // default query retrieves all data from stock table
    static final String DEFAULT_QUERY = "SELECT runDate,actualDate,receiptNumber " +
            "from income where paid=0";
    private ResultSetTableModel tableModel;
    private JTable resultTable;
    public final SaleDAO saleService=new HibernateSaleDAO();
    public final StockDAO stockService=new HibernateStockDAO();


    // create ResultSetTableModel and GUI
    public CreditList() {
        super("Credit Receipts");
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

            //actionlistener on enter
            ActionListener select = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String runDateString = resultTable.getValueAt(resultTable.getSelectedRow(), 0).toString();
                    String receiptNumber =resultTable.getValueAt(resultTable.getSelectedRow(), 2).toString();
                    String total=resultTable.getValueAt(resultTable.getSelectedRow(),3).toString();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = null;
                    try {
                        date = sdf1.parse(runDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    java.sql.Date runDate = new java.sql.Date(date.getTime());

                    List<IncomeItemsEntity> creditItems= saleService.getCreditSalesByReceipt(receiptNumber,runDate);


                    JPanel panelChange=new JPanel();
                    panelChange.setPreferredSize(new Dimension(800, 150));
                    panelChange.setBackground(Color.white);
                    panelChange.setLayout(new GridLayout(2, 2, 6, 6));
                    JTable table=new JTable(creditItems.size(),4);

                    int row=0;
                    for (IncomeItemsEntity saleEntity:creditItems) {
                        table.setValueAt(stockService.getMyStock(saleEntity.getStockId()).getProductName(),row,0);
                        table.setValueAt(saleEntity.getUnitsSold(),row,1);
                        table.setValueAt(saleEntity.getUnit_cost(),row,2);
                        table.setValueAt(saleEntity.getUnit_cost()*saleEntity.getUnitsSold(),row,3);
                        row++;
                        /*area.append(stockService.getMyStock(saleEntity.getStockId()).getProductName()+"   "+ saleEntity.getUnitsSold()+
                                "  "+ saleEntity.getPricePerUnit()+ "  "+
                                saleEntity.getPricePerUnit()*saleEntity.getUnitsSold()+"\n");*/
                    }
                    panelChange.add(table);
                    JOptionPane.showMessageDialog(null, panelChange, "Credit Receipt", JOptionPane.PLAIN_MESSAGE);

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

}
