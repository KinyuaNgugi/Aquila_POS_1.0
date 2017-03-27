package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/4/15.
 */
@Entity
@Table(name = "sale", schema = "", catalog = "pos")
public class SaleEntity {
    private Integer saleId;
    private Integer unitsSold;
    private String receiptNumber;
    private Date dateOfSale;
    private Integer userId;
    private Integer tillId;
    private Integer stockId;
    private Date runDate;
    private Boolean zedClear;
    private Integer profit;
    private Integer pricePerUnit;

    @Basic
    @Column(name = "pricePerUnit")
    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Id
    @Column(name = "saleId")
    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    @Basic
    @Column(name = "unitsSold")
    public Integer getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(Integer unitsSold) {
        this.unitsSold = unitsSold;
    }

    @Basic
    @Column(name = "receiptNumber")
    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Basic
    @Column(name = "dateOfSale")
    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    @Basic
    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "stockId")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
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
    @Column(name = "zedClear")
    public Boolean getZedClear() {
        return zedClear;
    }

    public void setZedClear(Boolean zedClear) {
        this.zedClear = zedClear;
    }

    @Basic
    @Column(name = "profit")
    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleEntity that = (SaleEntity) o;

        if (saleId != null ? !saleId.equals(that.saleId) : that.saleId != null) return false;
        if (unitsSold != null ? !unitsSold.equals(that.unitsSold) : that.unitsSold != null) return false;
        if (receiptNumber != null ? !receiptNumber.equals(that.receiptNumber) : that.receiptNumber != null)
            return false;
        if (dateOfSale != null ? !dateOfSale.equals(that.dateOfSale) : that.dateOfSale != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (tillId != null ? !tillId.equals(that.tillId) : that.tillId != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;
        if (profit != null ? !profit.equals(that.profit) : that.profit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleId != null ? saleId.hashCode() : 0;
        result = 31 * result + (unitsSold != null ? unitsSold.hashCode() : 0);
        result = 31 * result + (receiptNumber != null ? receiptNumber.hashCode() : 0);
        result = 31 * result + (dateOfSale != null ? dateOfSale.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (tillId != null ? tillId.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        return result;
    }
}
