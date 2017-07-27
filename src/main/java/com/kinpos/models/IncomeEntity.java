package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by kinyua on 4/14/16.
 */
@Entity
@Table(name = "income", schema = "", catalog = "pos2")
public class IncomeEntity {
    private Integer rid;
    private String receiptNumber;
    private Date runDate;
    private Timestamp actualDate;
    private Boolean zedClear;
    private Integer creditStatus;
    private Integer tillId;
    private Integer userId;
    private Integer clientId;

    @Basic
    @Column(name = "client_id")
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "paid")
    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
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
    @Column(name = "zedClear")
    public Boolean getZedClear() {
        return zedClear;
    }

    public void setZedClear(Boolean zedClear) {
        this.zedClear = zedClear;
    }

    @Id
    @Column(name = "rid")
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
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
    @Column(name = "runDate")
    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    @Basic
    @Column(name = "actualDate")
    public Timestamp getActualDate() {
        return actualDate;
    }

    public void setActualDate(Timestamp actualDate) {
        this.actualDate = actualDate;
    }

    @Basic
    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeEntity that = (IncomeEntity) o;

        if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;
        if (receiptNumber != null ? !receiptNumber.equals(that.receiptNumber) : that.receiptNumber != null)
            return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (actualDate != null ? !actualDate.equals(that.actualDate) : that.actualDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (receiptNumber != null ? receiptNumber.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (actualDate != null ? actualDate.hashCode() : 0);
        return result;
    }
}
