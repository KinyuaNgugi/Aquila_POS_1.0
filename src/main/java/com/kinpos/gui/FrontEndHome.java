package com.kinpos.gui;

/**
 * Created by kinyua on 6/19/15.
 */

import com.kinpos.dao.*;
import com.kinpos.dao.hibernate.*;
import com.kinpos.gui.resources.CreditList;
import com.kinpos.gui.resources.DateLabelFormatter;
import com.kinpos.gui.resources.print.PrintZed;
import com.kinpos.gui.resources.print.PrinterService;
import com.kinpos.gui.today.CashPaymentToday;
import com.kinpos.gui.today.FullSaleListToday;
import com.kinpos.gui.today.PettyCashPaymentToday;
import com.kinpos.gui.today.ReceiptToday;
import com.kinpos.models.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

import static com.kinpos.gui.resources.Constants.TILL_ID;

public class FrontEndHome extends JFrame {
    public final RunDateDAO runDateService=new HibernateRunDateDAO();
    public final UserDAO userService=new HibernateUserDAO();
    public final SaleDAO saleService=new HibernateSaleDAO();
    public final CashPaymentDAO cashPaymentService=new HibernateCashPaymentDAO();
    public final PettyCashPaymentDAO pettyCashPaymentService=new HibernatePettyCashPaymentDAO();
    public final StockDAO stockService=new HibernateStockDAO();
    public final ReceiptDAO receiptService=new HibernateReceiptDAO();
    public final SupplierDAO supplierService=new HibernateSupplierDAO();
    public final PrintZed printZed=new PrintZed();
    public final PrinterService printerService = new PrinterService();
    private CreditList creditList = new CreditList();
    private java.sql.Date runDateActual;
    private Integer runDateId;
    private float salesG;
    private Integer tillCollection;
    private float pettyPayments;
    private float cashPayments;
    private List<String>userList=new LinkedList<String>();
    private UserEntity userEntity;
    private String authority;
    private int uidKesho;
    private int customerCount;

    public FrontEndHome(final UserEntity user1)
    {
        final JFrame frame = new JFrame("Aquila 1.0");
        //get run date
        List<RunDateTableEntity> runDates = runDateService.getMyActiveRunDate();
        for (RunDateTableEntity runDate : runDates)
        {
            runDateActual = runDate.getRunDate();
            runDateId = runDate.getRunDateId();
            salesG=runDate.getSales();
            pettyPayments=runDate.getPettyCashPayments();
            cashPayments=runDate.getCashPayments();
            customerCount=runDate.getCustomerCount();
        }
        userEntity=user1;
        //get all users
        final List<UserEntity> userEntityList=userService.getAllMyUsers();
        for(UserEntity user:userEntityList){
            if (user.getStatus() == 1)
            userList.add(user.getUsername());
        }

        JMenu salesMenu = new JMenu("POS"); // create purchases menu
        salesMenu.setMnemonic('P'); // set mnemonic to P
        JMenuItem directSales = new JMenuItem("Direct Sales");
        directSales.setBackground(Color.yellow);
        salesMenu.add(directSales);
        JMenuItem about =new JMenuItem("About...");
        salesMenu.add(about);

        JMenu viewMenu = new JMenu("View"); // create purchases menu
        viewMenu.setMnemonic('V'); // set mnemonic to P
        JMenuItem creditTransactions = new JMenuItem("Credit Transactions");
        viewMenu.add(creditTransactions);
        JMenuItem productsSold = new JMenuItem("X Report");
        productsSold.setBackground(Color.yellow);
        viewMenu.add(productsSold);
        final JMenuItem receiptsToday=new JMenuItem("Customer receipts");
        viewMenu.add(receiptsToday);
        JMenuItem payments= new JMenuItem("Cash Payments");
        payments.setBackground(Color.yellow);
        viewMenu.add(payments);
        JMenuItem petty = new JMenuItem("Petty Cash Payments");
        viewMenu.add(petty);


        JMenu system = new JMenu("Exit");
        system.setMnemonic('E');
        JMenuItem logout = new JMenuItem("logout");
        logout.setBackground(Color.yellow);
        system.add(logout);

        JMenu endOfDay = new JMenu("End Of Day Procedure");
        endOfDay.setMnemonic('E');
        JMenuItem inputTill = new JMenuItem("Input Till Collection");
        inputTill.setBackground(Color.yellow);
        endOfDay.add(inputTill);
        JMenuItem zed=new JMenuItem("Zed Report");
        endOfDay.add(zed);

        JMenuBar bar = new JMenuBar(); // create menu bar
        frame.setJMenuBar(bar);
        bar.add(salesMenu);
        bar.add(viewMenu);
        bar.add(endOfDay);
        bar.add(system);

        inputTill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    final JComponent[] a = new JComponent[]{new JLabel("Run Date: " + runDateActual.toLocaleString().substring(0, 12)),
                            new JLabel("Total Cash Payments:" + cashPayments), new JLabel("Total Petty Cash Payments:" + pettyPayments),
                            new JLabel("Enter Till Collection:")};
                    String collection = JOptionPane.showInputDialog(null, a, "Payment", JOptionPane.PLAIN_MESSAGE);
                    if (collection.isEmpty())
                    {
                        collection = JOptionPane.showInputDialog(null, a, "Payment", JOptionPane.PLAIN_MESSAGE);
                    }
                    else
                    {
                        //update the runDate table(customer count and sales)
                        RunDateTableEntity replace = runDateService.getMyRunDate(runDateId);
                        replace.setManualCashEntry(Integer.parseInt(collection));

                        runDateService.updateMyRunDate(replace);
                        JOptionPane.showMessageDialog(null, "Till Collection Saved");
                    }
                }
                catch (Exception ex)
                {

                }
            }
        });

        zed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                float vatableTotal = 0;
                float unvatableTotal = 0;
                float cash_sales = 0;
                float credit_sales = 0;
                JComboBox usersCombo = new JComboBox(userList.toArray());
                JPasswordField passwordField = new JPasswordField(15);
                JLabel label = new JLabel("Enter a password:");
                JLabel label1 = new JLabel("Choose user:");
                JPanel panel = new JPanel();
                panel.add(label1);
                panel.add(usersCombo);
                panel.add(label);
                panel.add(passwordField);

                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(null, panel, "The title",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if (option == 0)
                {
                    List<UserEntity> userEntityL = userService.getMyUserByUserName(usersCombo.getSelectedItem().toString());
                    for (UserEntity u : userEntityL) {
                        if (passwordField.getText().equals(u.getPassword()))
                        {
                            authority = u.getUsername();
                            JComponent[] c = new JComponent[]{new JLabel("Are You sure You want to do the zed report?")};
                            int yes = JOptionPane.showConfirmDialog(null, c, "Zed Report Verification", JOptionPane.YES_NO_OPTION);
                            if (yes == JOptionPane.YES_OPTION)
                            {
                                JFrame frame=new JFrame("Processing zed");
                                 
                                frame.setResizable(false);
                                JPanel panel1=new JPanel();
                                panel1.add(new JLabel("Processing zed....."));
                                frame.add(panel1);
                                frame.setSize(300, 100);
                                frame.setVisible(true);

                                //update zed clear in receipts table
                                for (IncomeEntity incomeEntity : receiptService.getAllMyUnclearedReceipts())
                                {
                                    IncomeEntity replace =receiptService.getMyReceipt(incomeEntity.getRid());
                                    replace.setZedClear(true);

                                    //vat categorization
                                    for (IncomeItemsEntity item: saleService.getItemsForReceipt(incomeEntity.getRid()))
                                    {
                                        if (item.getT_tax() == 0)
                                        {
                                            unvatableTotal = unvatableTotal + item.getTotal();
                                        }
                                        else
                                        {
                                            vatableTotal = vatableTotal + item.getTotal();
                                        }
                                    }
                                    //cash and credit sale categorization
                                    if (incomeEntity.getCreditStatus() == 1)
                                    {
                                        for (IncomeItemsEntity item: saleService.getItemsForReceipt(incomeEntity.getRid()))
                                        {
                                            cash_sales += item.getTotal();
                                        }
                                    }
                                    else
                                    {
                                        for (IncomeItemsEntity item: saleService.getItemsForReceipt(incomeEntity.getRid()))
                                        {
                                            credit_sales += item.getTotal();
                                        }
                                    }

                                    receiptService.updateMyReceipt(replace);
                                }
                                //update the zed clear in income_items
                                List<IncomeItemsEntity> saleEntities = saleService.getAllMyUnclearedSales();
                                for (IncomeItemsEntity sale : saleEntities)
                                {
                                    IncomeItemsEntity replace = saleService.getMySale(sale.getSaleId());
                                    replace.setZedClear(true);
                                    saleService.updateMySale(replace);

                                }

                                RunDateTableEntity runDateTableEntity = runDateService.getMyRunDate(runDateId);
                                tillCollection = runDateTableEntity.getManualCashEntry();

                                //variables for temporal storage of cash payments before zed is cleared
                                LinkedList<String> supplierNamesTemp=new LinkedList<String>();
                                LinkedList<Integer> cashAmountsPaidTemp=new LinkedList<Integer>();

                                //update the zed clear in cash payments
                                List<CashPaymentEntity> paymentEntities = cashPaymentService.getTillCashPayments();
                                for (CashPaymentEntity cash : paymentEntities)
                                {
                                    CashPaymentEntity replace = cashPaymentService.getMyCashPayment(cash.getCashPaymentId());

                                    replace.setZedClear(true);
                                    cashPaymentService.updateMyCashPayment(replace);
                                }

                                //variables for temporal storage of cash payments before zed is cleared
                                LinkedList<String> pettyPayeesTemp=new LinkedList<String>();
                                LinkedList<Integer> pettyCashAmountsPaidTemp=new LinkedList<Integer>();

                                //update the zed clear in petty cash payments
                                List<PettyCashPaymentEntity> pettyPaymentEntities = pettyCashPaymentService.getTillPettyCashPayments();
                                for (PettyCashPaymentEntity petty : pettyPaymentEntities) {
                                    PettyCashPaymentEntity replace = pettyCashPaymentService.getMyPettyCashPayment(petty.getPettyCashPaymentId());
                                    replace.setZedClear(true);

                                    pettyCashPaymentService.updateMyPettyCashPayment(replace);
                                }


                                frame.setVisible(false);
                                printZed.setZedParams(runDateActual, salesG, tillCollection, cashPayments,
                                        pettyPayments, authority, customerCount,cashAmountsPaidTemp,supplierNamesTemp,
                                        pettyCashAmountsPaidTemp,pettyPayeesTemp);

                                printerService.printZedReport(runDateActual, salesG, tillCollection, cashPayments,
                                        pettyPayments, authority, customerCount,cashAmountsPaidTemp,supplierNamesTemp,
                                        pettyCashAmountsPaidTemp,pettyPayeesTemp);


                                SqlDateModel model = new SqlDateModel();

                                Calendar cal = Calendar.getInstance();
                                cal.setTime(runDateActual);
                                cal.add(Calendar.DATE, 1);

                                model.setValue(new Date(cal.getTime().getTime()));
                                model.setSelected(true);
                                Properties p = new Properties();
                                p.put("text.today", "Today");
                                p.put("text.month", "Month");
                                p.put("text.year", "Year");
                                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                                final JComponent[] d = new JComponent[]{new JLabel("Enter Next Run Date:"), datePicker,
                                        new JLabel("Enter teller to run:"), usersCombo};
                                int ex = JOptionPane.showConfirmDialog(null, d, "Choose Run Date", JOptionPane.YES_NO_OPTION);
                                if (ex == JOptionPane.YES_OPTION)
                                {
                                    List<UserEntity> userEntities = userService.getMyUserByUserName(usersCombo.getSelectedItem().toString());
                                    for (UserEntity u1 : userEntities)
                                    {
                                        uidKesho = u1.getId();
                                    }
                                    Date selectedDate = (Date) datePicker.getModel().getValue();
                                    //tomorrow
                                    if (!selectedDate.equals(null))
                                    {
                                        //update the runDate table(customer count and sales)
                                        RunDateTableEntity replace = runDateService.getMyRunDate(runDateId);
                                        replace.setActiveStatus(false);
                                        replace.setVatableTotals(vatableTotal);
                                        replace.setUnvatableTotals(unvatableTotal);
                                        replace.setCredit_sales(credit_sales);
                                        replace.setCash_sales(cash_sales);
                                        runDateService.updateMyRunDate(replace);

                                        //set rundate for tomorrow
                                        RunDateTableEntity setTomorrowRunDateValues = new RunDateTableEntity();
                                        setTomorrowRunDateValues.setRunDate(selectedDate);
                                        setTomorrowRunDateValues.setActiveStatus(true);
                                        setTomorrowRunDateValues.setCashPayments(0);
                                        setTomorrowRunDateValues.setPettyCashPayments(0);
                                        setTomorrowRunDateValues.setManualCashEntry(0);
                                        setTomorrowRunDateValues.setSales(0);
                                        setTomorrowRunDateValues.setCustomerCount(0);
                                        setTomorrowRunDateValues.setVatableTotals(0);
                                        setTomorrowRunDateValues.setUnvatableTotals(0);
                                        setTomorrowRunDateValues.setProfits(0);
                                        setTomorrowRunDateValues.setCredit_sales(0);
                                        setTomorrowRunDateValues.setSales(0);
                                        setTomorrowRunDateValues.setTillId(TILL_ID);
                                        setTomorrowRunDateValues.setZedClear(false);
                                        setTomorrowRunDateValues.setUserId(uidKesho);
                                        Random random = new Random();
                                        setTomorrowRunDateValues.setRunDateId(random.nextInt());
                                        runDateService.saveMyRunDate(setTomorrowRunDateValues);
                                    }

                                }
                                System.exit(1);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Password");
                        }
                    }
                }
            }
        });
        productsSold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new FullSaleListToday();
            }
        });
        payments.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new CashPaymentToday();
            }
        });
        petty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new PettyCashPaymentToday();
            }
        });
        receiptsToday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ReceiptToday();
            }
        });
        directSales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                new PointOfSale(user1);
            }
        });

        /*try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("/home/kinyua/kinmvnpos/src/main/java/com/kinpos/gui/resources/images/eden.png")))));
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }*/

        creditTransactions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                creditList.setVisible(true);
                creditList.setSize(700, 450);
            }
        });
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(1);
            }
        });
        frame.getContentPane().setBackground(new Color(0x152133));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(550, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        setupMenuKey(frame);
        frame.setVisible(true);
    }

    private void setupMenuKey(final JFrame frame) {
        Action menuAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRootPane rootPane = frame.getRootPane();
                JMenuBar jMenuBar = rootPane.getJMenuBar();
                JMenu menu = jMenuBar.getMenu(0);
                menu.doClick();
            }
        };

        JRootPane rootPane = frame.getRootPane();
        ActionMap actionMap = rootPane.getActionMap();

        final String MENU_ACTION_KEY = "expand_that_first_menu_please";
        actionMap.put(MENU_ACTION_KEY, menuAction);
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, 0, true), MENU_ACTION_KEY);
    }
}