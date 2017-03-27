package com.kinpos.gui.dummies;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by kinyua on 11/1/16.
 */
public class JavaTestKinyuaNgugiNLG100Index2
{
    BufferedReader reader;
    List<String> records=new LinkedList<String>();
    List<String> share_numbers=new LinkedList<String>();
    List<String> share_prices=new LinkedList<String>();

    private void openFile()
    {
        try
        {
            String path_to_file=System.getProperty("user.dir")+"/companies.txt";
            reader=new BufferedReader(new InputStreamReader
                    ((new BufferedInputStream(new FileInputStream
                            (new File(path_to_file))))));
        }
        catch ( FileNotFoundException f)
        {
            System.err.println("file not found");
            System.exit(1);
        }
    }

    private void readAndProcessRecords()
    {
        // read records from file using Scanner object
        try
        {
            String line=null;
            while ((line=reader.readLine())!=null)
            {
                records.add(line);

                // display records on terminal
                System.out.println(line);
            }
        }
        catch (NoSuchElementException n)
        {
            System.err.println("File improperly formed");
        }
        catch (IllegalStateException stateException)
        {
            System.err.println("error reading");
            System.exit(0);
        }
        catch (IOException ioe)
        {
            System.err.println("Input/Output error");
        }

        try
        {
            //process records and identify the share number and price then insert them into linked lists
            for (String record:records)
            {
                String[] parts = record.split("-");

                //add the first part to the share number linked list
                share_numbers.add(parts[0]);
                share_prices.add(parts[1]);
            }
        }
        catch (Exception ex)
        {

        }

    }
    private Double calculateIndex()
    {
        Double index=0.00;
        try
        {
            if (share_numbers.size()==share_prices.size())
            {
                Double total=0.0;
                for (int count=0;count<share_numbers.size();count++)
                {
                    total+=Double.parseDouble(share_numbers.get(count))*
                            Double.parseDouble(share_prices.get(count));
                }
                index=total/share_prices.size();
            }
        }
        catch (IllegalArgumentException ilae)
        {

        }
        return index;
    }
    public static void main(String [] args)
    {
        System.out.println("Hello, Please ensure the text file with share number and share prices is in the same " +
                "folder as this program");
        System.out.println("Also ensure it is in the format below:");
        System.out.println("Number of shares-Price per Share");
        System.out.println("To continue type 1");
        System.out.println("To exit type 2");

        Scanner keyboard = new Scanner(System.in);
        int proceed = keyboard.nextInt();

        if (proceed==1)
        {
            //Instantiate the NGL100Index2 object
            JavaTestKinyuaNgugiNLG100Index2 nlg100Index2=new JavaTestKinyuaNgugiNLG100Index2();
            nlg100Index2.openFile();
            nlg100Index2.readAndProcessRecords();

            System.out.println("Index for NLG 100 market is "+nlg100Index2.calculateIndex());
        }
        else if (proceed==2)
        {
            System.exit(1);
        }
        else
        {
            System.out.println("Invalid input");
            System.exit(2);
        }
    }
}
