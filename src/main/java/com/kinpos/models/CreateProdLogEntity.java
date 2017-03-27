package com.kinpos.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kinyua on 9/6/15.
 */
@Entity
@Table(name = "createprodlog", schema = "", catalog = "pos")
public class CreateProdLogEntity {
    private Integer cLogId;
    private Timestamp dateCreated;
    private Integer userId;
    private Integer stockId;
    private String type;

    @Id
    @Column(name = "cLogId")
    public Integer getcLogId() {
        return cLogId;
    }

    public void setcLogId(Integer cLogId) {
        this.cLogId = cLogId;
    }

    @Basic
    @Column(name = "dateCreated")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
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
    @Column(name = "stockId")
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

        CreateProdLogEntity that = (CreateProdLogEntity) o;

        if (cLogId != null ? !cLogId.equals(that.cLogId) : that.cLogId != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cLogId != null ? cLogId.hashCode() : 0;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
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
