package com.kinpos.gui;

/**
 * Created by kinyua on 6/21/15.
 */

import com.kinpos.dao.*;
import com.kinpos.dao.hibernate.*;
import com.kinpos.gui.resources.ProductSearch;
import com.kinpos.gui.resources.ResultSetTableModel;
import com.kinpos.gui.resources.print.PrintReceipt;
import com.kinpos.gui.resources.print.PrinterService;
import com.kinpos.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

import static com.kinpos.gui.resources.Constants.DB_HOST_ADDRESS;
import static com.kinpos.gui.resources.Constants.DB_USER;
import static com.kinpos.gui.resources.Constants.DB_PASSWORD;
import static com.kinpos.gui.resources.Constants.TILL_ID;

@SuppressWarnings("fallthrough")
public class PointOfSale extends JFrame {
    public final StockDAO product = new HibernateStockDAO();
    public final SaleDAO saleService = new HibernateSaleDAO();
    public final RunDateDAO runDateService = new HibernateRunDateDAO();
    public final SupplierDAO supplierService=new HibernateSupplierDAO();
    public final CashPaymentDAO cashPaymentService = new HibernateCashPaymentDAO();
    public final PettyCashPaymentDAO pettyCashPaymentService = new HibernatePettyCashPaymentDAO();
    public final ReceiptDAO receiptService = new HibernateReceiptDAO();
    public final PrintReceipt printReceipt = new PrintReceipt();
    public final PrinterService printerService = new PrinterService();
    List<String> supplierList=new LinkedList<String>();
    DefaultTableModel model;
    JTable table;
    JTable resultTable;
    JTextArea detailArea = new JTextArea(3, 0);
    JTextArea area = new JTextArea(3, 0);
    String col[] = {"Product Code", "Product Description", "quantity", "Unit price", "Total"};
    String colEmpty[] = {"", "", "", "", ""};
    LinkedList<Integer> productCodes= new LinkedList<Integer>();
    LinkedList<Integer> productQuantities = new LinkedList<Integer>();
    LinkedList<Integer> productPrices=new LinkedList<Integer>();
    LinkedList<Integer> profits = new LinkedList<Integer>();
    LinkedList<String> productNames=new LinkedList<String>();
    String code;
    int row = 0;
    Integer change=0;
    Integer total = 0;
    JLabel totalLabel;
    java.sql.Date runDateActual;
    Date systemDate = new Date();
    Integer runDateId;
    boolean multip=false;
    boolean multipleAccept=false;
    private ProductSearch productSearch = new ProductSearch(this);

    List<String>productList=new LinkedList<String>();
    public PointOfSale(final  UserEntity userEntity)
    {
        super("Aquila 1.0");
        try {
            totalLabel = new JLabel();

            //get run date
            List<RunDateTableEntity> runDates = runDateService.getMyActiveRunDate();
            for (RunDateTableEntity runDate : runDates) {
                runDateActual = runDate.getRunDate();
                runDateId = runDate.getRunDateId();
            }

            //populating supplier list
            List<SupplierEntity> supplierEntityList= supplierService.getAllMySuppliers();
            for (SupplierEntity supplierEntity:supplierEntityList){
                if (supplierEntity.getMethodOfPayment().equals("cash"))
                    supplierList.add(supplierEntity.getSupplierName());
            }

            //getting all products
            List<StockEntity> stockEntityList=product.getAllMyStocks();
            for (StockEntity s:stockEntityList){
                productList.add(s.getProductCode()+">>"+s.getProductName());
            }
            //table creation
            model = new DefaultTableModel(col, 1);
            //ensure column 0 is editable
            table = new JTable(model) {
                @Override
                public boolean isCellEditable(int arg0, int arg1) {
                    switch (arg1) {
                        case 0:
                            return true;
                        case 2:
                            return true;
                    }
                    return false;
                }
            };

            //table.setAutoResizeMode(JTable.AUTO_RESIZE_ON);
            /*table.setPreferredScrollableViewportSize(table.getPreferredSize());*/
            table.setShowVerticalLines(false);
            table.setGridColor(Color.cyan);
            table.setBackground(Color.WHITE);
            table.getColumnModel().getColumn(0).setPreferredWidth(250);
            table.getColumnModel().getColumn(1).setPreferredWidth(500);
            table.getColumnModel().getColumn(2).setPreferredWidth(152);
            table.getColumnModel().getColumn(3).setPreferredWidth(155);
            table.getColumnModel().getColumn(4).setPreferredWidth(180);
            table.setFont(new Font("Myriad Pro", Font.BOLD, 15));
            table.setForeground(Color.blue);
            table.setRowHeight(30);

            detailArea.setFont(new Font("Sans Serif", Font.BOLD, 16));
            detailArea.setForeground(Color.black);
            detailArea.setBackground(new Color(228,241,254));
            //detailArea.setBackground(Color.getHSBColor(174,168,211));
            detailArea.setEditable(false);
            area.setBackground(Color.white);
            JScrollPane detailScrollPane = new JScrollPane(detailArea,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            detailArea.append("System Date: " + systemDate.toLocaleString().substring(0, 12));
            detailArea.append("\n\nRun Date: " + runDateActual.toLocaleString().substring(0, 12));
            detailArea.append("\n\nteller: "+ userEntity.getUserName());


            area.setFont(new Font("Serif", Font.BOLD, 47));
            area.setForeground(new Color(102, 0, 0));
            area.setEditable(false);
            area.setText("Total\nKSH 0.00");
            JScrollPane scrollPane = new JScrollPane(area,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


            Box boxNorth = Box.createHorizontalBox();
            int boxNorthwidth=Toolkit.getDefaultToolkit().getScreenSize().width/7;
            int boxNorthheight=Toolkit.getDefaultToolkit().getScreenSize().height/7;
            Dimension dimension=new Dimension(boxNorthwidth,boxNorthheight);
            boxNorth.setPreferredSize(dimension);
            boxNorth.add(detailScrollPane);
            boxNorth.add(scrollPane);
            // create JTable based on the tableModel
            Box boxSouth = Box.createHorizontalBox();
            int boxSouthwidth=Toolkit.getDefaultToolkit().getScreenSize().width/13;
            int boxSouthheight=Toolkit.getDefaultToolkit().getScreenSize().height/13;
            Dimension dimensionSouth=new Dimension(boxSouthwidth,boxSouthheight);
            boxSouth.setPreferredSize(dimensionSouth);
            JButton f1=new JButton("F1:Help");
            //f1.setBackground(new Color(0x462655));
            f1.setForeground(Color.black);
            f1.setFont(new Font("Sans Serif", Font.BOLD, 20));

            JButton f5=new JButton("F5:Petty Cash Payments");
            //f5.setBackground(new Color(0x462655));
            f5.setForeground(Color.black);
            f5.setFont(new Font("Sans Serif", Font.BOLD, 20));

            JButton f6=new JButton("F6:Cash Payments");
            //f6.setBackground(new Color(0x462655));
            f6.setForeground(Color.black);
            f6.setFont(new Font("Sans Serif", Font.BOLD, 20));

            JButton f7=new JButton("F7:Check Price");
            //f7.setBackground(new Color(0x462655));
            f7.setForeground(Color.black);
            f7.setFont(new Font("Sans Serif", Font.BOLD, 20));

            JButton f9=new JButton("F9:Multiple");
            //f9.setBackground(new Color(0x462655));
            f9.setForeground(Color.black);
            f9.setFont(new Font("Sans Serif", Font.BOLD, 20));

            JPanel top=new JPanel();
            top.add(f1);
            top.add(f5);
            top.add(f6);
            top.add(f7);
            top.add(f9);
            boxSouth.add(top);
            //boxSouth.add(new JLabel("Total:"+total+".00"));
            // place GUI components on content pane
            add(boxNorth, BorderLayout.NORTH);
            JScrollPane tableScrollPane = new JScrollPane(table,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            add(tableScrollPane, BorderLayout.CENTER);
            add(boxSouth, BorderLayout.SOUTH);
            setVisible(true);
            setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setResizable(false);
            table.requestFocus();
            table.changeSelection(0, 0, false, false);
            table.editCellAt(0,0);


            //ActionListener that handles the product code and displays the item
            ActionListener registerCode = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        code = table.getValueAt(row, 0).toString();
                        List<StockEntity> stockEntity = product.getMyStockByProductCode(code);
                        if(multip==false) {
                            if (stockEntity.size() != 0) {
                                for (StockEntity stock : stockEntity) {
                                    table.setValueAt(stock.getProductName(), row, 1);
                                    table.setValueAt(stock.getSellingPricePerUnit(), row, 3);
                                    table.setValueAt(1, row, 2);
                                    table.setValueAt(stock.getSellingPricePerUnit(), row, 4);
                                    productCodes.add(stock.getStockId());
                                    profits.add(stock.getSellingPricePerUnit() - stock.getBuyingPricePerUnit());
                                    productQuantities.add(Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                                    productNames.add(table.getValueAt(row, 1).toString());
                                    productPrices.add(Integer.parseInt(table.getValueAt(row, 3).toString().trim()));
                                    Integer newTotal = 0;
                                    for (int i = 0; i < productCodes.size(); i++) {
                                        newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal.toString() + ".00");
                                    table.changeSelection(table.getRowCount() - 1, 0, false, false);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "item with that product code does not exist");
                                table.setValueAt("", row, 0);
                                table.editCellAt(row, 0);
                            }
                        }
                        if (multipleAccept==true){
                            for (StockEntity stock : stockEntity) {
                                Integer t=Integer.parseInt(table.getValueAt(row,2).toString().trim())*stock.getSellingPricePerUnit();
                                table.setValueAt(t, row, 4);
                                productCodes.add(stock.getStockId());
                                profits.add((stock.getSellingPricePerUnit() - stock.getBuyingPricePerUnit())*Integer.parseInt(table.getValueAt(row,2).toString().trim()));
                                productQuantities.add(Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                                productNames.add(table.getValueAt(row, 1).toString());
                                productPrices.add(Integer.parseInt(table.getValueAt(row, 3).toString().trim()));
                                Integer newTotal=0;
                                for (int i=0;i<productCodes.size();i++){
                                    newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                                }
                                row++;
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.addRow(colEmpty);
                                table.editCellAt(row, 0);
                                area.setText("Total\nKSH " + newTotal.toString() + ".00");
                                table.changeSelection(table.getRowCount() - 1, 0, false, false);
                                multip=false;
                                multipleAccept=false;
                            }
                        }
                        if (multip==true){
                            if (stockEntity.size() != 0) {
                                for (StockEntity stock : stockEntity) {
                                    table.setValueAt(stock.getProductName(), row, 1);
                                    table.setValueAt(stock.getSellingPricePerUnit(), row, 3);
                                    table.editCellAt(row,2);
                                    multipleAccept=true;
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "item with that product code does not exist");
                                table.setValueAt("", row, 0);
                                table.editCellAt(row, 0);
                            }
                        }
                    } catch (Exception ex) {
                        table.editCellAt(row, 0);
                        //JOptionPane.showMessageDialog(null, "error");
                        // System.out.println(ex.getLocalizedMessage());
                    }
                }
            };
            KeyStroke ksEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
            table.registerKeyboardAction(registerCode, ksEnter, JComponent.WHEN_FOCUSED);


            //ActionListener that handles price check
            ActionListener checkPrice = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    productSearch.setVisible(true);
                    productSearch.setSize(700, 450);
                }
            };

            KeyStroke ksf7 = KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, true);
            table.registerKeyboardAction(checkPrice, ksf7, JComponent.WHEN_IN_FOCUSED_WINDOW);

            //ActionListener that handles search
            ActionListener search = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        final JFrame f=new JFrame("Product Search");
                        // database URL, username and password
                        String DATABASE_URL = DB_HOST_ADDRESS;
                        String USERNAME = DB_USER;
                        String PASSWORD = DB_PASSWORD;
                        // default query retrieves all data from stock table
                        String DEFAULT_QUERY = "SELECT productCode,productName,sellingPricePerUnit,unitsInstock,stockId,buyingPricePerUnit from stock";
                        final ResultSetTableModel tableModel;
                        // create TableModel for results of query
                        tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
                        JScrollPane scrollPane = new JScrollPane(
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        Box boxNorth = Box.createHorizontalBox();
                        boxNorth.add(scrollPane);


                        // create JTable based on the tableModel
                        resultTable = new JTable(tableModel);
                        resultTable.setShowVerticalLines(false);
                        resultTable.setFont(new Font("Times New Roman", Font.BOLD, 18));
                        resultTable.setBackground(Color.WHITE);
                        resultTable.setShowGrid(false);
                        JLabel filterLabel = new JLabel("Filter:");
                        final JTextField filterText = new JTextField();
                        filterText.setSize(30,20);
                        Box boxSouth = Box.createHorizontalBox();
                        boxSouth.add(filterLabel);
                        boxSouth.add(filterText);
                        final TableRowSorter<TableModel> sorter =
                                new TableRowSorter<TableModel>(tableModel);
                        resultTable.setRowSorter(sorter);
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
                        resultTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                        resultTable.getColumnModel().getColumn(1).setPreferredWidth(500);
                        resultTable.getColumnModel().getColumn(2).setPreferredWidth(10);
                        resultTable.getColumnModel().getColumn(3).setPreferredWidth(15);
                        resultTable.getColumnModel().getColumn(4).setPreferredWidth(10);
                        resultTable.getColumnModel().getColumn(5).setPreferredWidth(10);
                        // place GUI components on content pane
                        //JComponent[] a = new JComponent[]{filterText,new JScrollPane(resultTable)};
                        resultTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
                        resultTable.getActionMap().put("Enter", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (multip== false) {
                                    table.createDefaultColumnsFromModel();
                                    table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                    table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                    table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                    table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                    table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                    table.setValueAt(resultTable.getValueAt(resultTable.getSelectedRow(),1),row,1);
                                    table.getModel().setValueAt(resultTable.getValueAt(resultTable.getSelectedRow(), 0), row, 0);
                                    table.setValueAt(resultTable.getValueAt(resultTable.getSelectedRow(),2),row,3);

                                    table.setValueAt(1, row, 2);
                                    Integer pCode=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(), 4).toString().trim());
                                    productCodes.add(pCode);
                                    Integer sPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),2).toString().trim());
                                    table.setValueAt(sPrice,row,4);
                                    Integer bPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),5).toString().trim());
                                    profits.add(sPrice-bPrice);
                                    productQuantities.add(1);
                                    productNames.add(resultTable.getValueAt(resultTable.getSelectedRow(), 1).toString());
                                    productPrices.add(sPrice);
                                    Integer newTotal = 0;
                                    for (int i = 0; i < productCodes.size(); i++) {
                                        newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal.toString() + ".00");
                                    tableModel.disconnectFromDatabase();
                                    f.setVisible(false);
                                }
                                //find
                                else
                                {
                                    table.createDefaultColumnsFromModel();
                                    table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                    table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                    table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                    table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                    table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                    table.setValueAt(resultTable.getValueAt(resultTable.getSelectedRow(),0),row,0);
                                    table.setValueAt(resultTable.getValueAt(resultTable.getSelectedRow(), 1), row, 1);
                                    f.setVisible(false);
                                    Integer t=Integer.parseInt(table.getValueAt(row,2).toString().trim())*
                                            Integer.parseInt(table.getValueAt(row, 3).toString().trim());
                                    table.editCellAt(row,2);
                                    table.setValueAt(t, row, 4);
                                    Integer pCode=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(), 4).toString().trim());
                                    productCodes.add(pCode);
                                    Integer sPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),2).toString().trim());
                                    table.setValueAt(sPrice, row, 4);
                                    Integer bPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),5).toString().trim());
                                    profits.add(sPrice-bPrice);
                                    productQuantities.add(Integer.parseInt(table.getValueAt(row,2).toString().trim()));
                                    productNames.add(resultTable.getValueAt(resultTable.getSelectedRow(), 1).toString());
                                    productPrices.add(sPrice);
                                    Integer newTotal=0;
                                    for (int i=0;i<productCodes.size();i++){
                                        newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal.toString() + ".00");
                                    multip=false;
                                    multipleAccept=false;
                                }
                                //System.out.print(resultTable.getValueAt(resultTable.getSelectedRow(), 0));
                            }
                        });
                        //ActionListener that handles productsearch exit
                        final ActionListener ext= new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                f.setVisible(false);
                            }
                        };

                        KeyStroke ksx = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
                        filterText.registerKeyboardAction(ext, ksx, JComponent.WHEN_IN_FOCUSED_WINDOW);
                        //ActionListener that handles productsearch exit
                        final ActionListener ext1= new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                tableModel.disconnectFromDatabase();
                                f.setVisible(false);
                            }
                        };

                        KeyStroke ksx1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
                        resultTable.registerKeyboardAction(ext1, ksx1, JComponent.WHEN_IN_FOCUSED_WINDOW);

                        filterText.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
                        filterText.getActionMap().put("Down", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                resultTable.requestFocus();
                                resultTable.changeSelection(0,0,false, false);
                                //JOptionPane.showMessageDialog(null,"wait");
                            }
                        });
                        f.pack();
                        f.add("North",filterText);
                        f.add("Center",new JScrollPane(resultTable));
                        f.setSize(800,300);
                        f.setVisible(true);
                        //JOptionPane.showMessageDialog(null, a, "Product Search",JOptionPane.INFORMATION_MESSAGE);


                    } catch (Exception exception) {

                    }
                }
            };

            KeyStroke ksS = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, true);
            table.registerKeyboardAction(search, ksS, JComponent.WHEN_IN_FOCUSED_WINDOW);

            //ActionListener that handles payment
            ActionListener payment = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        Integer newTotal=0;
                        for (int i=0;i<productCodes.size();i++){
                            newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                        }
                        if(newTotal!=0) {
                            //set receipt number
                            RunDateTableEntity setReceiptNumber = runDateService.getMyRunDate(runDateId);
                            Integer receiptNumber = setReceiptNumber.getCustomerCount() + 1;
                            String receiptString = runDateActual.toString().replace("-", "") + receiptNumber;

                            JLabel paymentTotal = new JLabel("Total:Ksh " + newTotal + ".00");
                            paymentTotal.setPreferredSize(new Dimension(100, 100));

                            paymentTotal.setFont(new Font("Serif", Font.BOLD, 50));

                            paymentTotal.setForeground(Color.blue);
                            JPanel panelTotal=new JPanel();
                            panelTotal.setPreferredSize(new Dimension(600, 150));
                            panelTotal.add(paymentTotal, BorderLayout.NORTH);
                            panelTotal.setBackground(Color.white);
                            panelTotal.setLayout(new GridLayout(3, 3, 6, 6));
                            panelTotal.add(new JLabel("Receipt Number: " + receiptString));
                            panelTotal.add(paymentTotal);
                            panelTotal.add( new JLabel("Enter Cash Received"));
                            String commit = JOptionPane.showInputDialog(null, panelTotal, "Payment", JOptionPane.PLAIN_MESSAGE);
                            if (Integer.parseInt(commit.trim()) < newTotal) {
                                commit = JOptionPane.showInputDialog(null, panelTotal, "Payment", JOptionPane.PLAIN_MESSAGE);
                            }
                            if (Integer.parseInt(commit.trim()) >= newTotal) {
                                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save this transaction?");
                                if (confirm == JOptionPane.YES_OPTION) {

                                    Integer prof=0;
                                    for (int i = 0; i < productNames.size(); i++) {
                                        //save products and quantities to sales table
                                        SaleEntity saleEntity = new SaleEntity();
                                        saleEntity.setUnitsSold(productQuantities.get(i));
                                        saleEntity.setPricePerUnit(productPrices.get(i));
                                        saleEntity.setDateOfSale(new java.sql.Date(System.currentTimeMillis()));
                                        saleEntity.setRunDate(runDateActual);
                                        saleEntity.setReceiptNumber(receiptString);
                                        saleEntity.setStockId(productCodes.get(i));
                                        Random random = new Random();
                                        saleEntity.setSaleId(random.nextInt());
                                        saleEntity.setTillId(TILL_ID);
                                        saleEntity.setUserId(userEntity.getUserId());
                                        saleEntity.setZedClear(false);
                                        saleEntity.setProfit(profits.get(i));
                                        prof+=profits.get(i);
                                        saleService.saveMySale(saleEntity);

                                        //update the stock table(decrement quantities
                                        StockEntity stockEntity=product.getMyStock(productCodes.get(i));
                                        String productCode=stockEntity.getProductCode();
                                        String productName=stockEntity.getProductName();
                                        Integer reorderLevel=stockEntity.getReorderLevel();
                                        Integer unitsInStock=stockEntity.getUnitsInStock();
                                        Double marginPercent=stockEntity.getMargin();
                                        String vatable=stockEntity.getVatable();
                                        String percentOrMargin=stockEntity.getMarginOrPercent();
                                        Integer packing=stockEntity.getPacking();
                                        Integer buyingPrice=stockEntity.getBuyingPricePerUnit();
                                        Integer sellingPrice=stockEntity.getSellingPricePerUnit();
                                        Integer supplierId=stockEntity.getSupplierId();
                                        Double vatAmt=stockEntity.getVatAmount();

                                        stockEntity.setProductCode(productCode);
                                        stockEntity.setProductName(productName);
                                        stockEntity.setBuyingPricePerUnit(buyingPrice);
                                        stockEntity.setSellingPricePerUnit(sellingPrice);
                                        stockEntity.setReorderLevel(reorderLevel);
                                        stockEntity.setSupplierId(supplierId);
                                        stockEntity.setUnitsInStock(unitsInStock - productQuantities.get(i));
                                        stockEntity.setVatAmount(vatAmt);
                                        stockEntity.setMargin(marginPercent);
                                        stockEntity.setMarginOrPercent(percentOrMargin);
                                        stockEntity.setActualMargin(sellingPrice - buyingPrice);
                                        stockEntity.setVatable(vatable);
                                        stockEntity.setPacking(packing);

                                        product.updateMyStock(stockEntity);
                                    }
                                    //save individual receipt and total(required creation of receipts table)
                                    ReceiptEntity receiptEntity=new ReceiptEntity();
                                    receiptEntity.setReceiptNumber(receiptString);
                                    receiptEntity.setRunDate(runDateActual);
                                    receiptEntity.setActualDate(new Timestamp(System.currentTimeMillis()));
                                    receiptEntity.setReceiptTotal(newTotal);
                                    receiptEntity.setZedClear(false);
                                    receiptEntity.setCreditStatus("NO CREDIT");
                                    receiptEntity.setTillId(TILL_ID);
                                    Random r=new Random();
                                    receiptEntity.setRid(r.nextInt());

                                    receiptService.saveMyReceipt(receiptEntity);

                                    //update the runDate table(customer count and sales)
                                    RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
                                    Integer payments = getRunDateValues.getCashPayments();
                                    Integer pettyCash = getRunDateValues.getPettyCashPayments();
                                    Integer sales = getRunDateValues.getSales();
                                    Integer manualCash = getRunDateValues.getManualCashEntry();
                                    Integer customerCountPrevious = getRunDateValues.getCustomerCount();
                                    Integer profit=getRunDateValues.getProfits();
                                    Integer till=getRunDateValues.getTillId();
                                    Integer uid=getRunDateValues.getUserId();

                                    RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
                                    setNewRunDateValues.setRunDate(runDateActual);
                                    setNewRunDateValues.setActiveStatus(true);
                                    setNewRunDateValues.setCashPayments(payments);
                                    setNewRunDateValues.setPettyCashPayments(pettyCash);
                                    setNewRunDateValues.setManualCashEntry(manualCash);
                                    setNewRunDateValues.setSales(sales + newTotal);
                                    setNewRunDateValues.setProfits(profit + prof);
                                    setNewRunDateValues.setTillId(till);
                                    setNewRunDateValues.setCustomerCount(customerCountPrevious + 1);
                                    setNewRunDateValues.setUserId(uid);

                                    runDateService.updateMyRunDate(setNewRunDateValues);

                                    /*printReceipt.setReceiptParams(receiptString, productNames, productCodes, productQuantities,
                                            productPrices, newTotal, commit, userEntity.getUserName());*/
                                    printerService.printReceipt(receiptString, productNames, productCodes, productQuantities,
                                            productPrices, newTotal, commit, userEntity.getUserName());
                                    //calculate customer change
                                    change = Integer.parseInt(commit.trim()) - newTotal;
                                    JLabel changeLabel = new JLabel("Change:Ksh " + change + ".00");
                                    changeLabel.setPreferredSize(new Dimension(100, 100));

                                    changeLabel.setFont(new Font("Serif", Font.BOLD, 50));

                                    changeLabel.setForeground(Color.blue);
                                    JPanel panelChange=new JPanel();
                                    panelChange.setPreferredSize(new Dimension(600, 150));
                                    panelChange.setBackground(Color.white);
                                    panelChange.setLayout(new GridLayout(2, 2, 6, 6));
                                    panelChange.add(changeLabel);
                                    JOptionPane.showMessageDialog(null, panelChange, "Change", JOptionPane.PLAIN_MESSAGE);
                                    //JOptionPane.showMessageDialog(null, "Change is " + change);
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    int p = model.getRowCount();
                                    for (int i = p - 1; i <= p; i--) {
                                        model.removeRow(i);
                                        if (i == 1) {
                                            break;
                                        }
                                    }
                                    area.setText("");
                                    table.setValueAt("", 0, 0);
                                    table.setValueAt("", 0, 1);
                                    table.setValueAt("", 0, 3);
                                    table.setValueAt("", 0, 2);
                                    table.setValueAt("", 0, 4);

                                    //reset all values
                                    row = 0;
                                    total = 0;
                                    change=0;
                                    productPrices.clear();
                                    productNames.clear();
                                    productQuantities.clear();
                                    productCodes.clear();
                                    profits.clear();
                                    area.setText("Total\nKSH 0.00");
                                    table.createDefaultColumnsFromModel();
                                    table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                    table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                    table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                    table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                    table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                    table.editCellAt(0, 0);
                                }
                            }
                        }
                        else {
                            int exit = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit?");
                            if (exit == JOptionPane.YES_OPTION) {
                                setVisible(false);
                                new FrontEndHome(userEntity);
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException a){
                        table.createDefaultColumnsFromModel();
                        table.getColumnModel().getColumn(0).setPreferredWidth(250);
                        table.getColumnModel().getColumn(1).setPreferredWidth(500);
                        table.getColumnModel().getColumn(2).setPreferredWidth(152);
                        table.getColumnModel().getColumn(3).setPreferredWidth(155);
                        table.getColumnModel().getColumn(4).setPreferredWidth(180);
                        table.editCellAt(0,0);
                    }

                }
            };
            KeyStroke ksP = KeyStroke.getKeyStroke(KeyEvent.VK_END, 0, true);
            table.registerKeyboardAction(payment, ksP, JComponent.WHEN_IN_FOCUSED_WINDOW);

            //ActionListener that handles cash pay outs
            ActionListener cashPayment = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        JComboBox supplierCombo = new JComboBox(supplierList.toArray());
                        supplierCombo.setBackground(Color.yellow);
                        final JComponent[] a = new JComponent[]{new JLabel("Supplier Name:"),
                                supplierCombo, new JLabel("Enter Cash")};
                        String cash = JOptionPane.showInputDialog(null, a, "Cash Payment", JOptionPane.PLAIN_MESSAGE);
                        String sup=supplierCombo.getSelectedItem().toString();
                        List<SupplierEntity> supplierEntities=supplierService.getMySupplierBySupplierName(sup);
                        for (SupplierEntity s:supplierEntities){
                            int supId=s.getSupplierId();
                            //save to cash payment
                            try{
                                CashPaymentEntity cashInsert=new CashPaymentEntity();
                                cashInsert.setTillId(TILL_ID);
                                cashInsert.setUserId(userEntity.getUserId());
                                cashInsert.setZedClear(false);
                                cashInsert.setRunDate(runDateActual);
                                cashInsert.setAmountPaid(Integer.parseInt(cash.trim()));
                                cashInsert.setDateOfPayment(new java.sql.Date(System.currentTimeMillis()));
                                cashInsert.setSupplierId(supId);
                                Random random=new Random();
                                cashInsert.setCashPaymentId(random.nextInt());

                                cashPaymentService.saveMyCashPayment(cashInsert);

                                //save to run date table
                                RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
                                Integer payments = getRunDateValues.getCashPayments();
                                Integer pettyCash = getRunDateValues.getPettyCashPayments();
                                Integer sales = getRunDateValues.getSales();
                                Integer manualCash = getRunDateValues.getManualCashEntry();
                                Integer customerCount = getRunDateValues.getCustomerCount();

                                RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
                                setNewRunDateValues.setRunDate(runDateActual);
                                setNewRunDateValues.setActiveStatus(true);
                                setNewRunDateValues.setCashPayments(payments + Integer.parseInt(cash.trim()));
                                setNewRunDateValues.setPettyCashPayments(pettyCash);
                                setNewRunDateValues.setManualCashEntry(manualCash);
                                setNewRunDateValues.setSales(sales);
                                setNewRunDateValues.setCustomerCount(customerCount);

                                runDateService.updateMyRunDate(setNewRunDateValues);
                                printerService.printCashPayment(sup,Integer.parseInt(cash.trim()),userEntity.getUserName(),runDateActual);
                                JOptionPane.showMessageDialog(null,"payment saved successfully");
                            }
                            catch (NumberFormatException nfe){
                                JOptionPane.showMessageDialog(null,"Please enter a valid value");
                            }
                        }

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            };
            KeyStroke ksCashP = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, true);
            table.registerKeyboardAction(cashPayment, ksCashP, JComponent.WHEN_IN_FOCUSED_WINDOW);

            //ActionListener that handles petty cash
            ActionListener pettyCash = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        JTextField payee=new JTextField();
                        JTextField amount =new JTextField();
                        final JComponent[] a = new JComponent[]{new JLabel("Payee:"),
                                payee, new JLabel("Enter Cash"),amount};
                        int opt= JOptionPane.showConfirmDialog(null, a, "Petty Cash Payment", JOptionPane.YES_NO_OPTION);
                        if (opt==JOptionPane.YES_OPTION && !payee.getText().isEmpty() && !amount.getText().isEmpty()) {
                            //save to petty cash payment
                            try{
                                PettyCashPaymentEntity cashInsert = new PettyCashPaymentEntity();
                                cashInsert.setTillId(TILL_ID);
                                cashInsert.setUserId(userEntity.getUserId());
                                cashInsert.setZedClear(false);
                                cashInsert.setRunDate(runDateActual);
                                cashInsert.setAmountPaid(Integer.parseInt(amount.getText().trim()));
                                cashInsert.setDateOfPayment(new java.sql.Date(System.currentTimeMillis()));
                                cashInsert.setPayee(payee.getText());
                                Random random = new Random();
                                cashInsert.setPettyCashPaymentId(random.nextInt());

                                pettyCashPaymentService.saveMyPettyCashPayment(cashInsert);

                                //save to run date table
                                RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
                                Integer payments = getRunDateValues.getCashPayments();
                                Integer pettyCash = getRunDateValues.getPettyCashPayments();
                                Integer sales = getRunDateValues.getSales();
                                Integer manualCash = getRunDateValues.getManualCashEntry();
                                Integer customerCount = getRunDateValues.getCustomerCount();

                                RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
                                setNewRunDateValues.setRunDate(runDateActual);
                                setNewRunDateValues.setActiveStatus(true);
                                setNewRunDateValues.setCashPayments(payments);
                                setNewRunDateValues.setPettyCashPayments(pettyCash+Integer.parseInt(amount.getText().trim()));
                                setNewRunDateValues.setManualCashEntry(manualCash);
                                setNewRunDateValues.setSales(sales);
                                setNewRunDateValues.setCustomerCount(customerCount);

                                runDateService.updateMyRunDate(setNewRunDateValues);
                                printerService.printPettyCashPayment(payee.getText(), Integer.parseInt(amount.getText().trim()),userEntity.getUserName(),runDateActual);
                                JOptionPane.showMessageDialog(null, "petty cash payment saved successfully");
                            }
                            catch (NumberFormatException nfe){
                                JOptionPane.showMessageDialog(null,"Please enter a valid value");
                            }

                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            };
            KeyStroke ksPetty = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true);
            table.registerKeyboardAction(pettyCash, ksPetty, JComponent.WHEN_IN_FOCUSED_WINDOW);


            //ActionListener that handles delete
            ActionListener delete = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        int x=table.getSelectedRow();
                        Object object=table.getValueAt(x,0);
                        Object object1=table.getValueAt(x,1);
                        Object object2=table.getValueAt(x,2);
                        Object object3=table.getValueAt(x,3);
                        Object object4=table.getValueAt(x,4);
                        //String value = table.getValueAt(0, table.getSelectedRow()).toString();
                        if (!object.equals("")&&!object1.equals("")&&!object2.equals("")&&!object3.equals("")
                                &&!object4.equals("")) {
                            int exit = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to remove this item from list");
                            if (exit == JOptionPane.YES_OPTION) {
                                int j = table.getSelectedRow();
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.removeRow(j);
                                row = model.getRowCount() - 1;
                                productCodes.remove(j);
                                productPrices.remove(j);
                                productQuantities.remove(j);
                                productNames.remove(j);
                                profits.remove(j);
                                table.createDefaultColumnsFromModel();
                                table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                table.setValueAt("", row, 0);
                                table.editCellAt(row, 0);
                                Integer newTotal = 0;
                                for (int i = 0; i < productCodes.size(); i++) {
                                    newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                }
                                area.setText("Total\nKSH " + newTotal.toString() + ".00");
                            }
                            else {
                                if (multip==true)
                                    multip=false;
                                table.editCellAt(row,0);
                            }
                        }
                    }
                    catch (Exception ex){

                    }
                }

            };
            KeyStroke ksDelete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true);
            table.registerKeyboardAction(delete, ksDelete, JComponent.WHEN_FOCUSED);

            //ActionListener that handles multiples
            final ActionListener multiple = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    multip=true;
                }
            };

            KeyStroke ksf9 = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0, true);
            table.registerKeyboardAction(multiple, ksf9, JComponent.WHEN_IN_FOCUSED_WINDOW);

            //ActionListener that handles credit payments
            ActionListener credit = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        Integer newTotal=0;
                        for (int i=0;i<productCodes.size();i++){
                            newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                        }
                        if(newTotal!=0) {
                            //set receipt number
                            RunDateTableEntity setReceiptNumber = runDateService.getMyRunDate(runDateId);
                            Integer receiptNumber = setReceiptNumber.getCustomerCount() + 1;
                            String receiptString = runDateActual.toString().replace("-", "") + receiptNumber;

                            JLabel paymentTotal = new JLabel("Total:Ksh " + newTotal + ".00");
                            paymentTotal.setPreferredSize(new Dimension(100, 100));

                            paymentTotal.setFont(new Font("Serif", Font.BOLD, 50));

                            paymentTotal.setForeground(Color.blue);
                            JPanel panelTotal=new JPanel();
                            panelTotal.setPreferredSize(new Dimension(600, 150));
                            panelTotal.add(paymentTotal, BorderLayout.NORTH);
                            panelTotal.setBackground(Color.white);
                            panelTotal.setLayout(new GridLayout(3, 3, 6, 6));
                            panelTotal.add(new JLabel("Receipt Number: " + receiptString));
                            panelTotal.add(paymentTotal);
                            JOptionPane.showMessageDialog(null, panelTotal, "Credit", JOptionPane.PLAIN_MESSAGE);

                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save this as a transaction on credit?");
                            if (confirm == JOptionPane.YES_OPTION) {

                                Integer prof=0;
                                for (int i = 0; i < productNames.size(); i++) {
                                    //save products and quantities to sales table
                                    SaleEntity saleEntity = new SaleEntity();
                                    saleEntity.setUnitsSold(productQuantities.get(i));
                                    saleEntity.setPricePerUnit(productPrices.get(i));
                                    saleEntity.setDateOfSale(new java.sql.Date(System.currentTimeMillis()));
                                    saleEntity.setRunDate(runDateActual);
                                    saleEntity.setReceiptNumber(receiptString.trim());
                                    saleEntity.setStockId(productCodes.get(i));
                                    Random random = new Random();
                                    saleEntity.setSaleId(random.nextInt());
                                    saleEntity.setTillId(TILL_ID);
                                    saleEntity.setUserId(userEntity.getUserId());
                                    saleEntity.setZedClear(false);
                                    saleEntity.setProfit(profits.get(i));
                                    prof+=profits.get(i);
                                    saleService.saveMySale(saleEntity);

                                    //update the stock table(decrement quantities
                                    StockEntity stockEntity=product.getMyStock(productCodes.get(i));
                                    String productCode=stockEntity.getProductCode();
                                    String productName=stockEntity.getProductName();
                                    Integer reorderLevel=stockEntity.getReorderLevel();
                                    Integer unitsInStock=stockEntity.getUnitsInStock();
                                    Double marginPercent=stockEntity.getMargin();
                                    String vatable=stockEntity.getVatable();
                                    String percentOrMargin=stockEntity.getMarginOrPercent();
                                    Integer packing=stockEntity.getPacking();
                                    Integer buyingPrice=stockEntity.getBuyingPricePerUnit();
                                    Integer sellingPrice=stockEntity.getSellingPricePerUnit();
                                    Integer supplierId=stockEntity.getSupplierId();
                                    Double vatAmt=stockEntity.getVatAmount();

                                    stockEntity.setProductCode(productCode);
                                    stockEntity.setProductName(productName);
                                    stockEntity.setBuyingPricePerUnit(buyingPrice);
                                    stockEntity.setSellingPricePerUnit(sellingPrice);
                                    stockEntity.setReorderLevel(reorderLevel);
                                    stockEntity.setSupplierId(supplierId);
                                    stockEntity.setUnitsInStock(unitsInStock - productQuantities.get(i));
                                    stockEntity.setVatAmount(vatAmt);
                                    stockEntity.setMargin(marginPercent);
                                    stockEntity.setMarginOrPercent(percentOrMargin);
                                    stockEntity.setActualMargin(sellingPrice - buyingPrice);
                                    stockEntity.setVatable(vatable);
                                    stockEntity.setPacking(packing);

                                    product.updateMyStock(stockEntity);
                                }
                                //save individual receipt and total(required creation of receipts table)
                                ReceiptEntity receiptEntity=new ReceiptEntity();
                                receiptEntity.setReceiptNumber(receiptString);
                                receiptEntity.setRunDate(runDateActual);
                                receiptEntity.setActualDate(new Timestamp(System.currentTimeMillis()));
                                receiptEntity.setReceiptTotal(newTotal);
                                receiptEntity.setZedClear(false);
                                receiptEntity.setCreditStatus("CREDIT");
                                receiptEntity.setTillId(TILL_ID);
                                Random r=new Random();
                                receiptEntity.setRid(r.nextInt());

                                receiptService.saveMyReceipt(receiptEntity);

                                //update the runDate table(customer count and sales)
                                RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
                                Integer payments = getRunDateValues.getCashPayments();
                                Integer pettyCash = getRunDateValues.getPettyCashPayments();
                                Integer credit_sales = getRunDateValues.getCreditSales();
                                Integer manualCash = getRunDateValues.getManualCashEntry();
                                Integer customerCountPrevious = getRunDateValues.getCustomerCount();
                                Integer profit=getRunDateValues.getProfits();
                                Integer till=getRunDateValues.getTillId();
                                Integer uid=getRunDateValues.getUserId();

                                RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
                                setNewRunDateValues.setRunDate(runDateActual);
                                setNewRunDateValues.setActiveStatus(true);
                                setNewRunDateValues.setCashPayments(payments);
                                setNewRunDateValues.setPettyCashPayments(pettyCash);
                                setNewRunDateValues.setManualCashEntry(manualCash);
                                setNewRunDateValues.setCreditSales(credit_sales + newTotal);
                                setNewRunDateValues.setProfits(profit + prof);
                                setNewRunDateValues.setTillId(till);
                                setNewRunDateValues.setCustomerCount(customerCountPrevious + 1);
                                setNewRunDateValues.setUserId(uid);

                                runDateService.updateMyRunDate(setNewRunDateValues);

                                /*printReceipt.setReceiptParams(receiptString, productNames, productCodes, productQuantities,
                                        productPrices, newTotal, "0", userEntity.getUserName());*/
                                printerService.printReceipt(receiptString, productNames, productCodes, productQuantities,
                                        productPrices, newTotal, "0", userEntity.getUserName());

                                JOptionPane.showMessageDialog(null,"Transaction saved under credit transactions");

                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                int p = model.getRowCount();
                                for (int i = p - 1; i <= p; i--) {
                                    model.removeRow(i);
                                    if (i == 1) {
                                        break;
                                    }
                                }
                                area.setText("");
                                table.setValueAt("", 0, 0);
                                table.setValueAt("", 0, 1);
                                table.setValueAt("", 0, 3);
                                table.setValueAt("", 0, 2);
                                table.setValueAt("", 0, 4);

                                //reset all values
                                row = 0;
                                total = 0;
                                change=0;
                                productPrices.clear();
                                productNames.clear();
                                productQuantities.clear();
                                productCodes.clear();
                                profits.clear();
                                area.setText("Total\nKSH 0.00");
                                table.createDefaultColumnsFromModel();
                                table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                table.editCellAt(0, 0);
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException a){
                        table.createDefaultColumnsFromModel();
                        table.getColumnModel().getColumn(0).setPreferredWidth(250);
                        table.getColumnModel().getColumn(1).setPreferredWidth(500);
                        table.getColumnModel().getColumn(2).setPreferredWidth(152);
                        table.getColumnModel().getColumn(3).setPreferredWidth(155);
                        table.getColumnModel().getColumn(4).setPreferredWidth(180);
                        table.editCellAt(0,0);
                    }
                    catch (NumberFormatException npe){
                        JOptionPane.showMessageDialog(null,"Please enter valid values");
                    }
                }
            };
            KeyStroke ksCr = KeyStroke.getKeyStroke(KeyEvent.VK_ALT, 0, true);
            table.registerKeyboardAction(credit, ksCr, JComponent.WHEN_IN_FOCUSED_WINDOW);

        } catch (Exception ex) {
            System.out.print(ex.getLocalizedMessage());
        }
    }

}