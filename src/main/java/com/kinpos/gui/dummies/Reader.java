package com.kinpos.gui.dummies;

import com.kinpos.dao.*;
import com.kinpos.dao.hibernate.*;
import com.kinpos.models.*;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by kinyua on 9/7/15.
 */
public class Reader {
    BufferedReader reader;
    private Scanner input;
    List<String> products=new LinkedList<String>();
    StockDAO stockService=new HibernateStockDAO();
    StockEdenDAO stockEdenService= new HibernateStockEdenDAO();
    CreateProdLogDAO createProdLogService=new HibernateCreateProdLogDAO();
    SaleDAO saleService= new HibernateSaleDAO();
    RunDateDAO runDateService=new HibernateRunDateDAO();
    ReceiptDAO receiptService= new HibernateReceiptDAO();
    SupplierDAO supplierService=new HibernateSupplierDAO();
    CashPaymentDAO cashPaymentService=new HibernateCashPaymentDAO();
    java.sql.Date runDateActual;
    int runDateId;
    // enable user to open fi
    public void openFile()
    {
        try
        {
            reader=new BufferedReader(new InputStreamReader
                    ((new BufferedInputStream(new FileInputStream
                            (new File("/home/kinyua/FileReader/src/plast.txt"))))));
            /*input = new Scanner( new File( "/home/kinyua/FileReader/src/tissue.txt" ));*/
        } // end try
        catch ( FileNotFoundException f)
        {
            System.err.println("file not found");
                    System.exit(1);
        } // end catch
    } // end method openFile
    public void readRecords() {

        try // read records from file using Scanner object
        {
            String line=null;
            while ((line=reader.readLine())!=null) {
                products.add(line);
                //System.out.print(line + products.size()+"\n");
                // display record contents
            }
        } catch (NoSuchElementException n) {
            System.err.println("File improperly formed");
            //input.close();
            //System.exit(1);
        } catch (IllegalStateException stateException) {
            System.err.println("error reading");
            System.exit(1);
        }
        catch (Exception ex){

        }
    }
    public void insertToDb(){
            List<StockedenEntity> stockedenEntities= stockEdenService.getAllMyRunDates();
            for (StockedenEntity stockedenEntity:stockedenEntities) {
                try
                {
                if (stockService.getMyStockByProductCode(stockedenEntity.getScancode()).size()==0)
                {
                    StockEntity stock= new StockEntity();
                    Random randomN = new Random();
                    int rdm = randomN.nextInt();
                    int random = 0 + (int) (Math.random() * 1000000000);
                    stock.setProductCode(stockedenEntity.getScancode());
                    stock.setProductName(stockedenEntity.getDescr());
                    stock.setBuyingPricePerUnit(1);
                    stock.setSellingPricePerUnit(stockedenEntity.getPrice());
                    stock.setReorderLevel(1);
                    stock.setSupplierId(1160644143);
                    //stock.setMarginOrPercent("percent");
                    //stock.setActualMargin(stockedenEntity.getPrice());
                    stock.setPacking(1);
                    Double margin=((stockedenEntity.getPrice().doubleValue() - 1)/1)*100;
                    //stock.setMargin(margin);
                    if (stockedenEntity.getVat().equals("E") || stockedenEntity.getVat().equals("Z")  ){
                        //stock.setVatable("unvatable");
                        //stock.setVatAmount(0.00);
                    }
                    if (stockedenEntity.getVat().equals("S")){
                        //stock.setVatable("vatable");
                        //stock.setVatAmount(stockedenEntity.getPrice()*0.16);
                    }
                    stock.setUnitsInStock(0);
                    stock.setStockId(random);

                    stockService.saveMyStock(stock);

                    //save to logs
                    /*CreateProdLogEntity createProdLogEntity = new CreateProdLogEntity();
                    createProdLogEntity.setcLogId(rdm);
                    createProdLogEntity.setUserId(315535663);
                    createProdLogEntity.setDateCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    createProdLogEntity.setType("create");
                    createProdLogEntity.setStockId(random);

                    createProdLogService.saveMyRunDate(createProdLogEntity);*/
                }
            }
                catch (Exception w){
                    System.out.print(w.getLocalizedMessage());
                    System.out.print(stockedenEntity.getScancode());
                }
        }

    }
    public void testSalesData(){
        //get run date
        List<RunDateTableEntity> runDates = runDateService.getAllMyRunDates();
        for (RunDateTableEntity runDate : runDates) {
            if (runDate.getActiveStatus()) {
                runDateActual = runDate.getRunDate();
                runDateId = runDate.getRunDateId();
            }
        }
        List<StockEntity> stockEntityList=stockService.getAllMyStocks();
        int i=0;
        int prof=0;
        for (StockEntity stockEntity:stockEntityList) {
            //save products and quantities to sales table
            /*IncomeItemsEntity incomeItemsEntity = new IncomeItemsEntity();
            incomeItemsEntity.setUnitsSold(2);
            incomeItemsEntity.setDateOfSale(new java.sql.Date(System.currentTimeMillis()));
            incomeItemsEntity.setRunDate(runDateActual);
            incomeItemsEntity.setReceiptNumber(String.valueOf(i));
            incomeItemsEntity.setStockId(stockEntity.getStockId());
            Random random = new Random();
            incomeItemsEntity.setSaleId(random.nextInt());
            incomeItemsEntity.setTillId(01);
            incomeItemsEntity.setUserId(315535663);
            incomeItemsEntity.setZedClear(false);
            incomeItemsEntity.setProfit(stockEntity.getSellingPricePerUnit() - stockEntity.getBuyingPricePerUnit());
            prof+=stockEntity.getSellingPricePerUnit()-stockEntity.getBuyingPricePerUnit();
            saleService.saveMySale(incomeItemsEntity);*/

            //save individual receipt and total(required creation of receipts table)
            /*IncomeEntity incomeEntity =new IncomeEntity();
            incomeEntity.setReceiptNumber(i+"");
            incomeEntity.setRunDate(runDateActual);
            incomeEntity.setActualDate(new Timestamp(System.currentTimeMillis()));
            incomeEntity.setReceiptTotal(stockEntity.getSellingPricePerUnit());
            incomeEntity.setZedClear(false);
            Random r=new Random();
            incomeEntity.setRid(r.nextInt());

            receiptService.saveMyReceipt(incomeEntity);

            //update the runDate table(customer count and sales)
            RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
            Integer payments = getRunDateValues.getCashPayments();
            Integer pettyCash = getRunDateValues.getPettyCashPayments();
            Integer sales = getRunDateValues.getSales();
            Integer manualCash = getRunDateValues.getManualCashEntry();
            Integer customerCountPrevious = getRunDateValues.getCustomerCount();
            Integer profit=getRunDateValues.getProfits();
            Integer till=getRunDateValues.getTillId();
            Integer uid=getRunDateValues.getUserId();*/
/*
            RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
            setNewRunDateValues.setRunDate(runDateActual);
            setNewRunDateValues.setActiveStatus(true);
            setNewRunDateValues.setCashPayments(payments);
            setNewRunDateValues.setPettyCashPayments(pettyCash);
            setNewRunDateValues.setManualCashEntry(manualCash);
            setNewRunDateValues.setSales(sales + stockEntity.getSellingPricePerUnit());
            setNewRunDateValues.setProfits(profit + prof);
            setNewRunDateValues.setTillId(till);
            setNewRunDateValues.setCustomerCount(customerCountPrevious + 1);
            setNewRunDateValues.setUserId(uid);

            runDateService.updateMyRunDate(setNewRunDateValues);*/

        }
    }
    public void testPaymentsData(){
        //get run date
        List<RunDateTableEntity> runDates = runDateService.getAllMyRunDates();
        for (RunDateTableEntity runDate : runDates) {
            if (runDate.getActiveStatus()) {
                runDateActual = runDate.getRunDate();
                runDateId = runDate.getRunDateId();
            }
        }
        List<SupplierEntity> supplierEntities=supplierService.getAllMySuppliers();
        for(SupplierEntity supplierEntity:supplierEntities){
            CashPaymentEntity cashInsert=new CashPaymentEntity();
            cashInsert.setTillId(1);
            cashInsert.setUserId(315535663);
            cashInsert.setZedClear(false);
            cashInsert.setRunDate(runDateActual);
            cashInsert.setAmountPaid(2000);
            cashInsert.setDateOfPayment(new java.sql.Date(System.currentTimeMillis()));
            cashInsert.setSupplierId(supplierEntity.getSupplierId());
            Random random=new Random();
            cashInsert.setCashPaymentId(random.nextInt());

            cashPaymentService.saveMyCashPayment(cashInsert);

            //save to run date table
            /*RunDateTableEntity getRunDateValues = runDateService.getMyRunDate(runDateId);
            Integer payments = getRunDateValues.getCashPayments();
            Integer pettyCash = getRunDateValues.getPettyCashPayments();
            Integer sales = getRunDateValues.getSales();
            Integer manualCash = getRunDateValues.getManualCashEntry();
            Integer customerCount = getRunDateValues.getCustomerCount();

            RunDateTableEntity setNewRunDateValues = runDateService.getMyRunDate(runDateId);
            setNewRunDateValues.setRunDate(runDateActual);
            setNewRunDateValues.setActiveStatus(true);
            setNewRunDateValues.setCashPayments(payments + 2000);
            setNewRunDateValues.setPettyCashPayments(pettyCash);
            setNewRunDateValues.setManualCashEntry(manualCash);
            setNewRunDateValues.setSales(sales);
            setNewRunDateValues.setCustomerCount(customerCount);*/

            //runDateService.updateMyRunDate(setNewRunDateValues);
        }
    }
    public  static void main(String args[]){
        Reader reader=new Reader();
        //reader.testSalesData();
        //reader.testPaymentsData();
        //reader.openFile();
        //reader.readRecords();
        reader.insertToDb();
    }

}
