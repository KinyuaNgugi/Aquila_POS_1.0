package com.kinpos.gui.resources.print;


import com.kinpos.dao.CashPaymentDAO;
import com.kinpos.dao.PettyCashPaymentDAO;
import com.kinpos.dao.hibernate.HibernateCashPaymentDAO;
import com.kinpos.dao.hibernate.HibernatePettyCashPaymentDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.LinkedList;

public class PrintZed extends JComponent implements Printable {

    //private JComponent top = new TopHeader();
    //private JComponent mid = new JLabel("Document Body");
    public final CashPaymentDAO cashPaymentService=new HibernateCashPaymentDAO();
    public final PettyCashPaymentDAO pettyCashPaymentService=new HibernatePettyCashPaymentDAO();

    public PrintZed() {
        this.setLayout(new GridLayout());
        //this.add(top, BorderLayout.NORTH);
        //this.add(mid, BorderLayout.CENTER);
    }

   /* private static class TopHeader extends JComponent {

        public TopHeader() {
            this.setLayout(new BorderLayout());
            JLabel companyName = new JLabel("Edenmart Supermarkets");
            JLabel docType = new JLabel("Customer Receipt");
            //this.add(companyName, BorderLayout.WEST);
            //this.add(docType, BorderLayout.EAST);
        }
    }*/

    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        int w = (int)pf.getImageableWidth();
        int h = (int)pf.getImageableHeight();
        //top.setSize(new Dimension(w, top.getPreferredSize().height));
        this.setSize(w, h);
        this.validate();
        this.printAll(g2d);
        return PAGE_EXISTS;
    }

    public void setZedParams(java.sql.Date runDateActual,Integer salesG,Integer tillCollection,
                             Integer cashPayments,Integer pettyPayments,String authority,Integer customerCount,
                             LinkedList<Integer>cashAmountsPaidTemp,LinkedList<String>supplierNamesTemp,
                             LinkedList<Integer>pettyCashAmountsPaidTemp,LinkedList<String>pettyPayeesTemp) {
        final PrintZed p = new PrintZed();
        Integer expenses=pettyPayments+cashPayments;
        JTextArea text = new JTextArea(100, 50);
        text.append("\tEDENMART SUPERMARKETS\n");
        text.append("\tZED REPORT\n");
        text.append("\tTIME:" + new java.util.Date().toLocaleString() + "\n");
        text.append("\tRUN DATE:"+runDateActual+"\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("\tZED FIGURES FOR TILL 1\n");
        text.append("\n=========================================\n");
        text.append("\t**********Computer Analysis*********\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("Total Cash Sales\t\t"+salesG);
        text.append("\n=========================================\n");
        text.append("\t**********Cashier Analysis*********\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("Till Collection   \t\t"+tillCollection);
        text.append("\n----------------------------------------------------------------------------------------------------\n");
        text.append("Cash Payments      \t"+cashPayments);
        text.append("\n----------------------------------------------------------------------------------------------------\n");
        text.append("Petty Cash Payments\t"+pettyPayments);
        text.append("\n----------------------------------------------------------------------------------------------------\n");
        text.append("Total Expenses   \t\t"+expenses);
        text.append("\n---------------------------------------------------------------------------------------------------\n");
        Integer v=(pettyPayments+cashPayments+tillCollection)-salesG;
        Integer t=pettyPayments+cashPayments+tillCollection;
        text.append("Total Sales       \t\t"+t);
        text.append("\n=========================================\n");
        text.append("\t**********Variance*********\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("Variance           \t\t"+v);
        text.append("\n=========================================\n");
        text.append("\t********Cash Payment Breakdown*********\n");
        text.append("=========================================\n");
        text.append("Rcpt#\tPayee\t\tAmount\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        Integer pt=0;
        for (int i=0;i<supplierNamesTemp.size();i++){
            text.append(supplierNamesTemp.get(i)+"\t"+"\t\t"+cashAmountsPaidTemp.get(i)+"\n");
            pt=pt+cashAmountsPaidTemp.get(i);
        }

        text.append("Total:\t     \t\t"+pt);
        text.append("\n----------------------------------------------------------------------------------------------------\n");
        text.append("\n=========================================\n");
        text.append("\t*********Petty Cash Breakdown********\n");
        text.append("=========================================\n");
        text.append("Rcpt#\tPayee\t\tAmount\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        Integer pt1=0;
        for (int i=0;i<pettyPayeesTemp.size();i++){
            text.append(pettyPayeesTemp.get(i)+"\t"+"\t\t"+pettyCashAmountsPaidTemp.get(i)+"\n");
            pt1=pt1+pettyCashAmountsPaidTemp.get(i);
        }
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("Total:\t     \t\t"+pt1);
        text.append("\n=========================================\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("Customer count:\t     \t\t"+customerCount);
        text.append("\n=========================================\n");
        text.append("\n----------------------------------------------------------------------------------------------------\n");
        text.append("Authorised By:" + authority);
        text.append("\n=========================================\n");

        p.add(text);
        // Preview before print()
        new JFrame() {
            {
                this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                this.add(p);
                this.pack();
                this.setVisible(true);
            }
        };
        /*PrinterJob pj = PrinterJob.getPrinterJob();
        //PageFormat pf = pj.pageDialog(pj.defaultPage());
        pj.setPrintable(p);
        try{
            pj.print();
        }
        catch(Exception ex){

        }*/
    }
}