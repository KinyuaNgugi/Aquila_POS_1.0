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

import static com.kinpos.gui.resources.Constants.*;

@SuppressWarnings("fallthrough")
public class CreateCashPaymentPurchase extends JFrame {
    public final StockDAO product = new HibernateStockDAO();
    public final SaleDAO saleService = new HibernateSaleDAO();
    public final RunDateDAO runDateService = new HibernateRunDateDAO();
    public final SupplierDAO supplierService=new HibernateSupplierDAO();
    public final OrgChartDAO chartService=new HibernateOrgChartDAO();
    public final AccountPostingsDAO postingService=new HibernateAccountPostingsDAO();
    public final CountryTaxRatesDAO taxService=new HibernateCountryTaxRatesDAO();
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
    String col[] = {"Product Code", "Product Description", "quantity", "Unit Buying Price", "Total Tax", "Total"};
    String colEmpty[] = {"", "", "", "", "", ""};
    LinkedList<Integer> productCodes= new LinkedList<Integer>();
    LinkedList<Integer> productQuantities = new LinkedList<Integer>();
    LinkedList<Float> productPrices=new LinkedList<Float>();
    LinkedList<Float> taxes = new LinkedList<Float>();
    LinkedList<String> productNames=new LinkedList<String>();
    String code;
    int row = 0;
    float change=0;
    Integer total = 0;
    JLabel totalLabel;
    java.sql.Date runDateActual;
    Date systemDate = new Date();
    Integer runDateId;
    boolean multip=true;
    boolean multipleAccept=true;

    JComboBox supplierCombo;
    public CreateCashPaymentPurchase(final  UserEntity userEntity)
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
            supplierList.add("Select Supplier");
            for (SupplierEntity supplierEntity:supplierEntityList){
                if (supplierEntity.getMethodOfPayment() == 2)
                    supplierList.add(supplierEntity.getSupplierName());
            }

            supplierCombo = new JComboBox(supplierList.toArray());
            supplierCombo.setBackground(Color.yellow);

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
                        case 3:
                            return true;
                    }
                    return false;
                }
            };

            table.setShowVerticalLines(false);
            table.setGridColor(Color.cyan);
            table.setBackground(Color.WHITE);
            table.getColumnModel().getColumn(0).setPreferredWidth(250);
            table.getColumnModel().getColumn(1).setPreferredWidth(500);
            table.getColumnModel().getColumn(2).setPreferredWidth(152);
            table.getColumnModel().getColumn(3).setPreferredWidth(155);
            table.getColumnModel().getColumn(4).setPreferredWidth(180);
            table.getColumnModel().getColumn(5).setPreferredWidth(180);
            table.setFont(new Font("Myriad Pro", Font.BOLD, 15));
            table.setForeground(Color.blue);
            table.setRowHeight(30);

            detailArea.setFont(new Font("Sans Serif", Font.BOLD, 16));
            detailArea.setForeground(Color.black);
            detailArea.setBackground(new Color(228,241,254));
            detailArea.setEditable(false);
            area.setBackground(Color.white);
            JScrollPane detailScrollPane = new JScrollPane(detailArea,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            detailArea.append("System Date: " + systemDate.toLocaleString().substring(0, 12));
            detailArea.append("\n\nRun Date: " + runDateActual.toLocaleString().substring(0, 12));
            detailArea.append("\n\nteller: "+ userEntity.getUsername());;


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

            add(boxNorth, BorderLayout.NORTH);
            JScrollPane tableScrollPane = new JScrollPane(table,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            add(tableScrollPane, BorderLayout.CENTER);
            setVisible(true);
            setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                                    table.setValueAt(stock.getBuyingPricePerUnit(), row, 3);
                                    table.setValueAt(1, row, 2);
                                    table.setValueAt(stock.getBuyingPricePerUnit(), row, 4);
                                    productCodes.add(stock.getStockId());
                                    productQuantities.add(Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                                    productNames.add(table.getValueAt(row, 1).toString());
                                    productPrices.add(Float.parseFloat(table.getValueAt(row, 3).toString().trim()));
                                    taxes.add(taxService.getMyTaxRate(stock.getVat()).getRate() * stock.getSellingPricePerUnit());

                                    float newTotal = 0;
                                    for (int i = 0; i < productCodes.size(); i++) {
                                        newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal + ".00");
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
                                float t=Integer.parseInt(table.getValueAt(row,2).toString().trim())*stock.getSellingPricePerUnit();
                                table.setValueAt(t, row, 4);
                                productCodes.add(stock.getStockId());
                                productQuantities.add(Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                                productNames.add(table.getValueAt(row, 1).toString());
                                productPrices.add(Float.parseFloat(table.getValueAt(row, 3).toString().trim()));
                                taxes.add(taxService.getMyTaxRate(stock.getVat()).getRate() * stock.getSellingPricePerUnit());
                                float newTotal=0;
                                for (int i=0;i<productCodes.size();i++){
                                    newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                                }
                                row++;
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.addRow(colEmpty);
                                table.editCellAt(row, 0);
                                area.setText("Total\nKSH " + newTotal + ".00");
                                table.changeSelection(table.getRowCount() - 1, 0, false, false);
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
                                    float sPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),2).toString().trim());
                                    table.setValueAt(sPrice,row,4);
                                    float bPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),5).toString().trim());
                                    productQuantities.add(1);
                                    productNames.add(resultTable.getValueAt(resultTable.getSelectedRow(), 1).toString());
                                    productPrices.add(sPrice);
                                    StockEntity stock = product.getMyStock(pCode);
                                    taxes.add(taxService.getMyTaxRate(stock.getVat()).getRate() * stock.getSellingPricePerUnit());
                                    float newTotal = 0;
                                    for (int i = 0; i < productCodes.size(); i++) {
                                        newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal + ".00");
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
                                    float sPrice=Float.parseFloat(resultTable.getValueAt(resultTable.getSelectedRow(),2).toString().trim());
                                    table.setValueAt(sPrice, row, 4);
                                    float bPrice=Integer.parseInt(resultTable.getValueAt(resultTable.getSelectedRow(),5).toString().trim());
                                    productQuantities.add(Integer.parseInt(table.getValueAt(row,2).toString().trim()));
                                    productNames.add(resultTable.getValueAt(resultTable.getSelectedRow(), 1).toString());
                                    productPrices.add(sPrice);
                                    StockEntity stock = product.getMyStock(pCode);
                                    taxes.add(taxService.getMyTaxRate(stock.getVat()).getRate() * stock.getSellingPricePerUnit());

                                    float newTotal=0;
                                    for (int i=0;i<productCodes.size();i++){
                                        newTotal=newTotal+productPrices.get(i)*productQuantities.get(i);
                                    }
                                    row++;
                                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                                    model.addRow(colEmpty);
                                    table.editCellAt(row, 0);
                                    area.setText("Total\nKSH " + newTotal + ".00");
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
                        float newTotal=0;
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
                                if (confirm == JOptionPane.YES_OPTION)
                                {
                                    //save individual receipt and total
                                    IncomeEntity incomeEntity =new IncomeEntity();
                                    incomeEntity.setReceiptNumber(receiptString);
                                    incomeEntity.setRunDate(runDateActual);
                                    incomeEntity.setActualDate(new Timestamp(System.currentTimeMillis()));
                                    incomeEntity.setZedClear(false);
                                    incomeEntity.setCreditStatus(1);
                                    incomeEntity.setTillId(TILL_ID);
                                    incomeEntity.setUserId(userEntity.getId());
                                    incomeEntity.setCreditStatus(1);

                                    Integer rid = new Random().nextInt();
                                    incomeEntity.setRid(rid);

                                    receiptService.saveMyReceipt(incomeEntity);


                                    for (int i = 0; i < productNames.size(); i++)
                                    {
                                        //save products and quantities to income_items table
                                        IncomeItemsEntity incomeItemsEntity = new IncomeItemsEntity();
                                        incomeItemsEntity.setUnitsSold(productQuantities.get(i));
                                        incomeItemsEntity.setReceiptNumber(rid);
                                        incomeItemsEntity.setUnit_cost(productPrices.get(i));
                                        incomeItemsEntity.setStockId(productCodes.get(i));
                                        Random random = new Random();
                                        incomeItemsEntity.setSaleId(random.nextInt());
                                        incomeItemsEntity.setZedClear(false);
                                        incomeItemsEntity.setT_tax(taxes.get(i) * productQuantities.get(i));
                                        incomeItemsEntity.setTotal(productPrices.get(i) * productQuantities.get(i));
                                        saleService.saveMySale(incomeItemsEntity);

                                        //update the stock table(decrement quantities
                                        StockEntity stockEntity=product.getMyStock(productCodes.get(i));
                                        Integer unitsInStock=stockEntity.getUnitsInStock();
                                        stockEntity.setUnitsInStock(unitsInStock - productQuantities.get(i));

                                        product.updateMyStock(stockEntity);

                                        //save to accounts
                                        AccountsPostingsEntity credit_post = new AccountsPostingsEntity();
                                        credit_post.setDebit(0);
                                        credit_post.setCredit(productPrices.get(i) * productQuantities.get(i));
                                        credit_post.setCurrency("KSH");
                                        credit_post.setDescription("Credit Stock via Receipt Number " + receiptString);
                                        for (OrgChartEntity org :chartService.getMyAccountByNumber(productCodes.get(i))) {
                                            credit_post.setAccountId(org.getId());
                                            break;
                                        }
                                        credit_post.setDate(new java.sql.Date(System.currentTimeMillis()));

                                        postingService.saveMyPosting(credit_post);

                                        AccountsPostingsEntity debit_post = new AccountsPostingsEntity();
                                        debit_post.setDebit(productPrices.get(i) * productQuantities.get(i));
                                        debit_post.setCredit(0);
                                        debit_post.setCurrency("KSH");
                                        debit_post.setDescription("Incoming Cash for Receipt Number " + receiptString);
                                        debit_post.setAccountId(1);
                                        debit_post.setDate(new java.sql.Date(System.currentTimeMillis()));

                                        postingService.saveMyPosting(debit_post);
                                    }


                                    //update the runDate table(customer count and sales)
                                    RunDateTableEntity setRunDateValues = runDateService.getMyRunDate(runDateId);
                                    float sales = setRunDateValues.getSales();
                                    Integer customerCountPrevious = setRunDateValues.getCustomerCount();
                                    float profit = setRunDateValues.getProfits();

                                    setRunDateValues.setSales(sales + newTotal);
                                    setRunDateValues.setCustomerCount(customerCountPrevious + 1);

                                    runDateService.updateMyRunDate(setRunDateValues);

                                    printerService.printReceipt(receiptString, productNames, productCodes, productQuantities,
                                            productPrices, newTotal, commit, userEntity.getUsername());

                                    //calculate customer change
                                    change = Float.parseFloat(commit.trim()) - newTotal;
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
                                    taxes.clear();
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
                                table.createDefaultColumnsFromModel();
                                table.getColumnModel().getColumn(0).setPreferredWidth(250);
                                table.getColumnModel().getColumn(1).setPreferredWidth(500);
                                table.getColumnModel().getColumn(2).setPreferredWidth(152);
                                table.getColumnModel().getColumn(3).setPreferredWidth(155);
                                table.getColumnModel().getColumn(4).setPreferredWidth(180);
                                table.setValueAt("", row, 0);
                                table.editCellAt(row, 0);
                                float newTotal = 0;
                                for (int i = 0; i < productCodes.size(); i++) {
                                    newTotal = newTotal + productPrices.get(i) * productQuantities.get(i);
                                }
                                area.setText("Total\nKSH " + newTotal + ".00");
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


            try {
                supplierCombo.addActionListener (new ActionListener () {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,"yolo");
                    }
                });
            }
            catch (NullPointerException npe){

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}