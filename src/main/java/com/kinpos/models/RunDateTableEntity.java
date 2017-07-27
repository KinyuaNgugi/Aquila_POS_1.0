package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/4/15.
 */
@Entity
@Table(name = "runDateTable", schema = "", catalog = "pos2")
public class RunDateTableEntity {
    private Integer runDateId;
    private Date runDate;
    private float sales;
    private float cash_sales;
    private float credit_sales;
    private float cashPayments;
    private float pettyCashPayments;
    private Boolean activeStatus;
    private Integer manualCashEntry;
    private Integer customerCount;
    private float vatableTotals;
    private float unvatableTotals;
    private Boolean zedClear;
    private float profits;
    private Integer tillId;
    private Integer userId;


    @Basic
    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "runDateId")
    public Integer getRunDateId() {
        return runDateId;
    }

    public void setRunDateId(Integer runDateId) {
        this.runDateId = runDateId;
    }

    @Basic
    @Column(name = "runDate")
    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }


    @Basic
    @Column(name = "cashPayments")
    public float getCashPayments() {
        return cashPayments;
    }

    public void setCashPayments(float cashPayments) {
        this.cashPayments = cashPayments;
    }

    @Basic
    @Column(name = "pettyCashPayments")
    public float getPettyCashPayments() {
        return pettyCashPayments;
    }

    public void setPettyCashPayments(float pettyCashPayments) {
        this.pettyCashPayments = pettyCashPayments;
    }

    @Basic
    @Column(name = "activeStatus")
    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Basic
    @Column(name = "manualCashEntry")
    public Integer getManualCashEntry() {
        return manualCashEntry;
    }

    public void setManualCashEntry(Integer manualCashEntry) {
        this.manualCashEntry = manualCashEntry;
    }

    @Basic
    @Column(name = "customerCount")
    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    @Basic
    @Column(name = "vatableTotals")
    public float getVatableTotals() {
        return vatableTotals;
    }

    public void setVatableTotals(float vatableTotals) {
        this.vatableTotals = vatableTotals;
    }

    @Basic
    @Column(name = "unvatableTotals")
    public float getUnvatableTotals() {
        return unvatableTotals;
    }

    public void setUnvatableTotals(float unvatableTotals) {
        this.unvatableTotals = unvatableTotals;
    }

    @Basic
    @Column(name = "zedClear")
    public Boolean getZedClear() {
        return zedClear;
    }

    public void setZedClear(Boolean zedClear) {
        this.zedClear = zedClear;
    }

    @Basic
    @Column(name = "profits")
    public float getProfits() {
        return profits;
    }

    public void setProfits(float profits) {
        this.profits = profits;
    }

    @Basic
    @Column(name = "tillId")
    public Integer getTillId() {
        return tillId;
    }

    public void setTillId(Integer tillId) {
        this.tillId = tillId;
    }

    @Basic
    @Column(name = "cash_sales")
    public float getCash_sales() {
        return cash_sales;
    }

    public void setCash_sales(float cash_sales) {
        this.cash_sales = cash_sales;
    }

    @Basic
    @Column(name = "credit_sales")
    public float getCredit_sales() {
        return credit_sales;
    }

    public void setCredit_sales(float credit_sales) {
        this.credit_sales = credit_sales;
    }

    @Basic
    @Column(name = "sales")
    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunDateTableEntity that = (RunDateTableEntity) o;

        if (runDateId != null ? !runDateId.equals(that.runDateId) : that.runDateId != null) return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (activeStatus != null ? !activeStatus.equals(that.activeStatus) : that.activeStatus != null) return false;
        if (manualCashEntry != null ? !manualCashEntry.equals(that.manualCashEntry) : that.manualCashEntry != null)
            return false;
        if (customerCount != null ? !customerCount.equals(that.customerCount) : that.customerCount != null)
            return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = runDateId != null ? runDateId.hashCode() : 0;
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (activeStatus != null ? activeStatus.hashCode() : 0);
        result = 31 * result + (manualCashEntry != null ? manualCashEntry.hashCode() : 0);
        result = 31 * result + (customerCount != null ? customerCount.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        return result;
    }


}
