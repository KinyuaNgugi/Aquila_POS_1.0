package com.kinpos.gui.resources.print;

/**
 * Created by openworldkin on 1/3/17.
 */
import com.kinpos.dao.StockDAO;
import com.kinpos.dao.hibernate.HibernateStockDAO;
import com.kinpos.models.StockEntity;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import static com.kinpos.gui.resources.Constants.TILL_ID;
public class PrinterService implements Printable
{

    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
		/* Now we perform our rendering */

        g.setFont(new Font("Roman", 0, 8));
        g.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
    }

    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PrintService findPrintService(String printerName,
                                          PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }
    public void printReceipt(String receiptString,LinkedList<String> productNames,
                                 LinkedList<Integer> productCodes,
                                 LinkedList<Integer> productQuantities,
                                 LinkedList<Integer> productPrices,Integer newTotal,
                                 String commit, String userName)
    {
        StockDAO product=new HibernateStockDAO();

        String header = "\tEDENMART SUPERMARKETS.\n"+"\tP.O BOX 1621-00902\n"+
                "\tKIKUYU, KENYA\n"+"\tTEL:+254 722 921328 / +254 711 455400\n\n"+
                "-----------------------------------------\n"+
                "PIN:A002765955L\t\t VAT #:REG.\n"+new Date().toString()+"  Rcpt #:"+receiptString+
                "\n=========================================\n"+"Item\tQuantity\t@Price\tTotal\n"+
                "=========================================\n";
        String body = "";
        for (int i=0;i<productNames.size();i++)
        {
            body += productNames.get(i)+"\n";
            body += productCodes.get(i) +"\t" +productQuantities.get(i)+"\t"
                    +productPrices.get(i)+".00"+ "\t"+productQuantities.get(i)*productPrices.get(i)
                    +".00"+"\n";
        }
        body += "=========================================\n";
        body += "\tTOTAL:\t\t"+newTotal+".00\n";
        body += "\tCASH:\t\t"+commit+".00\n";
        Integer z=Integer.parseInt(commit)-newTotal;
        body += "\tCHANGE:\t\t"+z+".00\n";
        body += "=========================================\n";
        body += "Served By: "+userName+"\n";
        body += "\t\tVAT ANALYSIS\n";
        body += "-----------------------------------------\n";
        body += "VAT Desc\tRate\tVAT Amount\n";
        Double vat=0.0;
        Boolean vatAvailable=false;
        for (int i=0;i<productCodes.size();i++)
        {
            StockEntity stockEntity=product.getMyStock(productCodes.get(i));
            if(stockEntity.getVatable().equals("vatable")){
                vat=vat+(stockEntity.getSellingPricePerUnit().doubleValue()*productQuantities.get(i)*16)/116;
            }
            if (stockEntity.getVatable().equals("unvatable")){
                vatAvailable=true;
            }
        }
        if (vat!=0.0)
        {

            DecimalFormat df = new DecimalFormat("#.00");
            String vatFormated = df.format(vat);
            body += "S-16%\t\t16%\t\t"+vatFormated+"\n";
        }
        if (vatAvailable)
        {
            body += "E- 0%\t\t0%\t\t0.00\n";
        }
        body += "-----------------------------------------\n";
        body += "\tPRICES INCLUSIVE OF 16% VAT\n\t       WHERE APPLICABLE\n\tTHANK YOU FOR SHOPPING WITH US\n" +
                "\tGOODS ONCE SOLD NOT REFUNDABLE\n";
        body += "=========================================\n";
        body += "\tTailored By Easy Software Solutions\n \tContact:+254 727 669491\n";
        body += "\n=========================================\n\n\n\n\n\n\n\n";

        //print some stuff
        printString("Generic-text-only", header+body);

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printBytes("Generic-text-only", cutP);

    }
    public void printZedReport(java.sql.Date runDateActual,Integer salesG,Integer tillCollection,
                             Integer cashPayments,Integer pettyPayments,String authority,
                             Integer customerCount, LinkedList<Integer>cashAmountsPaidTemp,
                             LinkedList<String>supplierNamesTemp,
                               LinkedList<Integer>pettyCashAmountsPaidTemp,
                             LinkedList<String>pettyPayeesTemp)
    {
        Integer expenses=pettyPayments+cashPayments;

        String text = "";
        text += "\tEDENMART SUPERMARKETS\n";
        text += "\tZED REPORT\n";
        text += "\tTIME:" + new java.util.Date().toLocaleString() + "\n" ;
        text += "\tRUN DATE:"+runDateActual+"\n" ;
        text += "-------------------------------------------\n" ;
        text += "\tZED FIGURES FOR TILL "+TILL_ID+"\n" ;
        text += "\n=========================================\n" ;
        text += "\t**********Computer Analysis*********\n" ;
        text += "-------------------------------------------\n" ;
        text += "Total Cash Sales\t\t"+salesG ;
        text += "\n=========================================\n" ;
        text += "\t**********Cashier Analysis*********\n" ;
        text += "-------------------------------------------\n" ;
        text += "Till Collection   \t\t"+tillCollection ;
        text += "\n-----------------------------------------\n" ;
        text += "Cash Payments      \t"+cashPayments ;
        text += "\n-----------------------------------------\n" ;
        text += "Petty Cash Payments\t"+pettyPayments ;
        text += "\n-----------------------------------------\n" ;
        text += "Total Expenses   \t\t"+expenses ;
        text += "\n-----------------------------------------\n" ;
        Integer v=(pettyPayments+cashPayments+tillCollection)-salesG;
        Integer t=pettyPayments+cashPayments+tillCollection;
        text += "Total Sales       \t\t"+t ;
        text += "\n=========================================\n" ;
        text += "\t**********Variance*********\n" ;
        text += "-------------------------------------------\n" ;
        text += "Variance           \t\t"+v ;
        text += "\n=========================================\n" ;
        text += "\t********Cash Payment Breakdown*********\n" ;
        text += "=========================================\n" ;
        text += "Rcpt#\tPayee\t\tAmount\n" ;
        text += "-------------------------------------------\n" ;
        Integer pt=0;
        for (int i=0; i<supplierNamesTemp.size(); i++){
            text += supplierNamesTemp.get(i)+"\t\t"+cashAmountsPaidTemp.get(i)+"\n" ;
            pt=pt+cashAmountsPaidTemp.get(i) ;
        }

        text += "Total:\t     \t\t"+pt ;
        text += "\n-----------------------------------------\n" ;
        text += "\n=========================================\n" ;
        text += "\t*********Petty Cash Breakdown********\n" ;
        text += "=========================================\n" ;
        text += "Rcpt#\tPayee\t\tAmount\n" ;
        text += "-----------------------------------------\n" ;
        Integer pt1=0;
        for (int i=0;i<pettyPayeesTemp.size() ;i++){
            text += pettyPayeesTemp.get(i)+"\t\t"+pettyCashAmountsPaidTemp.get(i)+"\n" ;
            pt1=pt1+pettyCashAmountsPaidTemp.get(i) ;
        }
        text += "-----------------------------------------\n" ;
        text += "Total:\t     \t\t"+pt1 ;
        text += "\n=========================================\n" ;
        text += "-----------------------------------------\n" ;
        text += "Customer count:\t     \t\t"+customerCount ;
        text += "\n=========================================\n" ;
        text += "\n-----------------------------------------\n" ;
        text += "Authorised By:" + authority ;
        text += "\n=========================================\n\n\n\n\n\n\n\n\n\n";

        print(text);
    }

    public void printCashPayment(String creditor, Integer cash, String user, java.sql.Date runDateActual)
    {
        String text = "";
        text += "\tEDENMART SUPERMARKETS\n";
        text += "\tCASH PAYMENT\n";
        text += "\tTIME:" + new java.util.Date().toLocaleString() + "\n" ;
        text += "\tRUN DATE:"+runDateActual+"\n" ;
        text += "-------------------------------------------\n" ;
        text += creditor + "\t\tKES" + cash;
        text += "\n-------------------------------------------\n" ;
        text += "Paid By:" + user;
        text += "\n=========================================\n\n\n\n\n\n\n\n";

        print(text);
    }

    public void printPettyCashPayment(String payee, Integer cash, String user, java.sql.Date runDateActual)
    {
        String text = "";
        text += "\tEDENMART SUPERMARKETS\n";
        text += "\tPETTY CASH PAYMENT\n";
        text += "\tTIME:" + new java.util.Date().toLocaleString() + "\n" ;
        text += "\tRUN DATE:"+runDateActual+"\n" ;
        text += "-------------------------------------------\n" ;
        text += payee + "\t\tKES" + cash;
        text += "\n-------------------------------------------\n" ;
        text += "Paid By:" + user;
        text += "\n=========================================\n\n\n\n\n\n\n\n";

        print(text);
    }
    private void print(String text)
    {
        //print some stuff
        printString("Generic-text-only", text);

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printBytes("Generic-text-only", cutP);
    }


    /*public static void main(String[] args) {

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());

        //print some stuff
        printerService.printString("Generic-text-only", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes("Generic-text-only", cutP);

    }*/
}