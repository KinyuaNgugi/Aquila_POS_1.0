package com.kinpos.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kinyua on 9/6/15.
 */
@Entity
@Table(name = "supplierlog", schema = "", catalog = "pos")
public class SupplierLogEntity {
    private Integer eLogId;
    private Timestamp dateOfEdit;
    private Integer userId;
    private Integer stockId;
    private String type;

    @Id
    @Column(name = "eLogId")
    public Integer geteLogId() {
        return eLogId;
    }

    public void seteLogId(Integer eLogId) {
        this.eLogId = eLogId;
    }

    @Basic
    @Column(name = "dateOfEdit")
    public Timestamp getDateOfEdit() {
        return dateOfEdit;
    }

    public void setDateOfEdit(Timestamp dateOfEdit) {
        this.dateOfEdit = dateOfEdit;
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
    @Column(name = "supplierId")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierLogEntity that = (SupplierLogEntity) o;

        if (eLogId != null ? !eLogId.equals(that.eLogId) : that.eLogId != null) return false;
        if (dateOfEdit != null ? !dateOfEdit.equals(that.dateOfEdit) : that.dateOfEdit != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eLogId != null ? eLogId.hashCode() : 0;
        result = 31 * result + (dateOfEdit != null ? dateOfEdit.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
