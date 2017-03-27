/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kinpos.gui.dummies;

/**
 *
 * @author GEORGE
 */

import com.kinpos.gui.resources.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.util.Properties;

public class Print {

public static void main(String args[]){

    JFrame frame=new JFrame();
    UtilDateModel model = new UtilDateModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
    //model.setSelected(true);
    frame.add(datePicker);
    frame.setSize(500,500);
    frame.setVisible(true);



}
}

