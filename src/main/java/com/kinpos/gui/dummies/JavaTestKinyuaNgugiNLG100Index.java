package com.kinpos.gui.dummies;

/**
 * Created by kinyua on 11/1/16.
 */
public class JavaTestKinyuaNgugiNLG100Index
{
    public static Double calculateIndex(Integer[] shares, Double[] share_prices) {
        Double index=0.00;
        if (shares.length==share_prices.length) {
            Double total=0.0;
            for (int count=0;count<shares.length;count++) {
                total+=shares[count].doubleValue()*share_prices[count];
            }
            index=total/share_prices.length;
        }

        return index;
    }
    public static void main(String [] args) {
        Integer[] shares={1,2,3};
        Double[] prices={5.0,6.0,7.0};
        System.out.print(calculateIndex(shares,prices));
    }
}
