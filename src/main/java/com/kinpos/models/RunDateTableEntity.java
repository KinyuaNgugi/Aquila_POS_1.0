package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/4/15.
 */
@Entity
@Table(name = "runDateTable", schema = "", catalog = "pos")
public class RunDateTableEntity {
    private Integer runDateId;
    private Date runDate;
    private Integer sales;
    private Integer cashPayments;
    private Integer pettyCashPayments;
    private Boolean activeStatus;
    private Integer manualCashEntry;
    private Integer customerCount;
    private Integer vatableTotals;
    private Integer unvatableTotals;
    private Boolean zedClear;
    private Integer profits;
    private Integer tillId;
    private Integer userId;
    private Integer creditSales;

    @Basic
    @Column(name = "credit_sales")
    public Integer getCreditSales() {
        return creditSales;
    }

    public void setCreditSales(Integer creditSales) {
        this.creditSales = creditSales;
    }

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
    @Column(name = "sales")
    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Basic
    @Column(name = "cashPayments")
    public Integer getCashPayments() {
        return cashPayments;
    }

    public void setCashPayments(Integer cashPayments) {
        this.cashPayments = cashPayments;
    }

    @Basic
    @Column(name = "pettyCashPayments")
    public Integer getPettyCashPayments() {
        return pettyCashPayments;
    }

    public void setPettyCashPayments(Integer pettyCashPayments) {
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
    public Integer getVatableTotals() {
        return vatableTotals;
    }

    public void setVatableTotals(Integer vatableTotals) {
        this.vatableTotals = vatableTotals;
    }

    @Basic
    @Column(name = "unvatableTotals")
    public Integer getUnvatableTotals() {
        return unvatableTotals;
    }

    public void setUnvatableTotals(Integer unvatableTotals) {
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
    public Integer getProfits() {
        return profits;
    }

    public void setProfits(Integer profits) {
        this.profits = profits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunDateTableEntity that = (RunDateTableEntity) o;

        if (runDateId != null ? !runDateId.equals(that.runDateId) : that.runDateId != null) return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (sales != null ? !sales.equals(that.sales) : that.sales != null) return false;
        if (cashPayments != null ? !cashPayments.equals(that.cashPayments) : that.cashPayments != null) return false;
        if (pettyCashPayments != null ? !pettyCashPayments.equals(that.pettyCashPayments) : that.pettyCashPayments != null)
            return false;
        if (activeStatus != null ? !activeStatus.equals(that.activeStatus) : that.activeStatus != null) return false;
        if (manualCashEntry != null ? !manualCashEntry.equals(that.manualCashEntry) : that.manualCashEntry != null)
            return false;
        if (customerCount != null ? !customerCount.equals(that.customerCount) : that.customerCount != null)
            return false;
        if (vatableTotals != null ? !vatableTotals.equals(that.vatableTotals) : that.vatableTotals != null)
            return false;
        if (unvatableTotals != null ? !unvatableTotals.equals(that.unvatableTotals) : that.unvatableTotals != null)
            return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;
        if (profits != null ? !profits.equals(that.profits) : that.profits != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = runDateId != null ? runDateId.hashCode() : 0;
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (sales != null ? sales.hashCode() : 0);
        result = 31 * result + (cashPayments != null ? cashPayments.hashCode() : 0);
        result = 31 * result + (pettyCashPayments != null ? pettyCashPayments.hashCode() : 0);
        result = 31 * result + (activeStatus != null ? activeStatus.hashCode() : 0);
        result = 31 * result + (manualCashEntry != null ? manualCashEntry.hashCode() : 0);
        result = 31 * result + (customerCount != null ? customerCount.hashCode() : 0);
        result = 31 * result + (vatableTotals != null ? vatableTotals.hashCode() : 0);
        result = 31 * result + (unvatableTotals != null ? unvatableTotals.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        result = 31 * result + (profits != null ? profits.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "tillId")
    public Integer getTillId() {
        return tillId;
    }

    public void setTillId(Integer tillId) {
        this.tillId = tillId;
    }
}
