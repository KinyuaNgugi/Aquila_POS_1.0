package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/4/15.
 */
@Entity
@Table(name = "income_items", schema = "", catalog = "pos2")
public class IncomeItemsEntity {
    private Integer saleId;
    private Integer unitsSold;
    private Integer receipt_id;
    private Date dateOfSale;
    private Integer stockId;
    private Date runDate;
    private Boolean zedClear;
    private float unit_cost;
    private float profit;
    private float t_tax;
    private float total;

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
    @Column(name = "receipt_id")
    public Integer getReceiptNumber() {
        return receipt_id;
    }

    public void setReceiptNumber(Integer receipt_id) {
        this.receipt_id = receipt_id;
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
    @Column(name = "zedClear")
    public Boolean getZedClear() {
        return zedClear;
    }

    public void setZedClear(Boolean zedClear) {
        this.zedClear = zedClear;
    }

    @Basic
    @Column(name = "unit_cost")
    public float getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(float unit_cost) {
        this.unit_cost = unit_cost;
    }

    @Basic
    @Column(name = "profit")
    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    @Basic
    @Column(name = "t_tax")
    public float getT_tax() {
        return t_tax;
    }

    public void setT_tax(float t_tax) {
        this.t_tax = t_tax;
    }

    @Basic
    @Column(name = "total")
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeItemsEntity that = (IncomeItemsEntity) o;

        if (saleId != null ? !saleId.equals(that.saleId) : that.saleId != null) return false;
        if (unitsSold != null ? !unitsSold.equals(that.unitsSold) : that.unitsSold != null) return false;
        if (receipt_id != null ? !receipt_id.equals(that.receipt_id) : that.receipt_id != null)
            return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleId != null ? saleId.hashCode() : 0;
        result = 31 * result + (unitsSold != null ? unitsSold.hashCode() : 0);
        result = 31 * result + (receipt_id != null ? receipt_id.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        return result;
    }
}
