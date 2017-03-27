package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/3/15.
 */
@Entity
@Table(name = "negativeadjustments", schema = "", catalog = "pos")
public class NegativeAdjustmentEntity {
    private Integer adjNumber;
    private Integer stockId;
    private Integer unitsToRemove;
    private Date dateOfAdjustment;
    private String category;

    @Id
    @Column(name = "adjNumber")
    public Integer getAdjNumber() {
        return adjNumber;
    }

    public void setAdjNumber(Integer adjNumber) {
        this.adjNumber = adjNumber;
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
    @Column(name = "unitsToRemove")
    public Integer getUnitsToRemove() {
        return unitsToRemove;
    }

    public void setUnitsToRemove(Integer unitsToRemove) {
        this.unitsToRemove = unitsToRemove;
    }

    @Basic
    @Column(name = "dateOfAdjustment")
    public Date getDateOfAdjustment() {
        return dateOfAdjustment;
    }

    public void setDateOfAdjustment(Date dateOfAdjustment) {
        this.dateOfAdjustment = dateOfAdjustment;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NegativeAdjustmentEntity that = (NegativeAdjustmentEntity) o;

        if (adjNumber != null ? !adjNumber.equals(that.adjNumber) : that.adjNumber != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (unitsToRemove != null ? !unitsToRemove.equals(that.unitsToRemove) : that.unitsToRemove != null)
            return false;
        if (dateOfAdjustment != null ? !dateOfAdjustment.equals(that.dateOfAdjustment) : that.dateOfAdjustment != null)
            return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adjNumber != null ? adjNumber.hashCode() : 0;
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (unitsToRemove != null ? unitsToRemove.hashCode() : 0);
        result = 31 * result + (dateOfAdjustment != null ? dateOfAdjustment.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
