package com.kinpos.gui.resources.print;


import com.kinpos.dao.StockDAO;
import com.kinpos.dao.hibernate.HibernateStockDAO;
import com.kinpos.models.StockEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;

public class PrintReceipt extends JComponent implements Printable {

    //private JComponent top = new TopHeader();
    //private JComponent mid = new JLabel("Document Body");

    public PrintReceipt() {
        this.setLayout(new GridLayout());
        //this.add(top, BorderLayout.NORTH);
        //this.add(mid, BorderLayout.CENTER);
    }

    /*private static class TopHeader extends JComponent {

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

    public void setReceiptParams(String receiptString,LinkedList<String> productNames,
                                        LinkedList<Integer> productCodes,LinkedList<Integer> productQuantities,
                                        LinkedList<Integer> productPrices,Integer newTotal,String commit,
                                        String userName) {
        final PrintReceipt p = new PrintReceipt();
        StockDAO product=new HibernateStockDAO();
        /*JTextArea a = new JTextArea();
        a.append("hello");
        p.add(a);*/

        JTextArea text = new JTextArea(100, 50);
        text.append("\tEDENMART SUPERMARKETS.\n");
        text.append("\tP.O BOX 1621-00902\n");
        text.append("\tKIKUYU, KENYA\n\n");
        text.append("\tTEL:+254 722 921328\n\t     +254 711 455400\n\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("PIN:A002765955L\t\t VAT #:REG.\n");
        text.append(new Date().toString()+"\tRcpt #:"+receiptString);
        text.append("\n=========================================\n");
        text.append("Item\tQuantity\t@Price\tTotal\n");
        text.append("=========================================\n");

        for (int i=0;i<productNames.size();i++) {
            text.append(productNames.get(i)+"\n");
            text.append(productCodes.get(i) +"\t" +productQuantities.get(i)+"\t"
                    +productPrices.get(i)+".00"+ "\t"+productQuantities.get(i)*productPrices.get(i)
                    +".00"+"\n");
        }
        text.append("=========================================\n");
        text.append("\tTOTAL:\t\t"+newTotal+".00\n");
        text.append("\tCASH:\t\t"+commit+".00\n");
        Integer z=Integer.parseInt(commit)-newTotal;
        text.append("\tCHANGE:\t\t"+z+".00\n");
        text.append("=========================================\n");
        text.append("Served By: "+userName+"\n");
        text.append("\tVAT ANALYSIS\n");
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("VAT Desc\tRate\tVAT Amount\n");
        Double vat=0.0;
        Boolean vatAvailable=false;
        for (int i=0;i<productCodes.size();i++){
            StockEntity stockEntity=product.getMyStock(productCodes.get(i));
            /*if(stockEntity.getVatable().equals("vatable")){
                vat=vat+(stockEntity.getSellingPricePerUnit().doubleValue()*productQuantities.get(i)*16)/116;
            }
            if (stockEntity.getVatable().equals("unvatable")){
                vatAvailable=true;
            }*/
        }
        if (vat!=0.0){

            DecimalFormat df = new DecimalFormat("#.00");
            String vatFormated = df.format(vat);
            text.append("S-16%\t16%\t"+vatFormated+"\n");
        }
        if (vatAvailable){
            text.append("E- 0%\t0%\t0.00\n");
        }
        text.append("----------------------------------------------------------------------------------------------------\n");
        text.append("\tPRICES INCLUSIVE OF 16% VAT\n\t       WHERE APPLICABLE\n\tTHANK YOU FOR SHOPPING WITH US\n" +
                "\tGOODS ONCE SOLD NOT REFUNDABLE\n");
        text.append("=========================================\n");
        text.append("\tSystem Tailored By Easy Software Solutions\n \tContact:+254 727 669491\n");
        text.append("\n=========================================\n");

        p.add(text);
        // Preview before print()
        new JFrame() {
            {
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(p);
                this.pack();
                //this.setVisible(true);
            }
        };
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.pageDialog(pj.defaultPage());
        pj.setPrintable(p);
        try{
            pj.print();
        }
        catch(Exception ex){

        }
    }
}