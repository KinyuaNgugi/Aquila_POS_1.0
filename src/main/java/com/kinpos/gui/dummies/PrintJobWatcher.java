/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kinpos.gui.dummies;

/**
 *
 * @author GEORGE
 */
import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
public class PrintJobWatcher {
    boolean done;
    public PrintJobWatcher(DocPrintJob job){

    job.addPrintJobListener(new PrintJobAdapter() {
        public void printJobCanceled(PrintJobEvent pje) {
            allDone();
        }
        public void printJobCompleted(PrintJobEvent pje) {
            allDone();
        }
        public void printJobFailed(PrintJobEvent pje) {
            allDone();
        }
        public void printJobNoMoreEvents(PrintJobEvent pje) {
            allDone();
        }
        void allDone() {
            synchronized (PrintJobWatcher.this) {
                done = true;
                System.out.println("Printing done ...");
                PrintJobWatcher.this.notify();
            }
        }
    });
}

public synchronized void waitForDone() {
    try {
        while (!done) {
            wait();
        }
    } catch (InterruptedException e) {
    }
}
}