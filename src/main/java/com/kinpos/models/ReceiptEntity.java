package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by kinyua on 4/14/16.
 */
@Entity
@Table(name = "receipt", schema = "", catalog = "pos")
public class ReceiptEntity {
    private Integer rid;
    private String receiptNumber;
    private Date runDate;
    private Timestamp actualDate;
    private Integer receiptTotal;
    private Boolean zedClear;
    private String creditStatus;

    @Basic
    @Column(name = "credit_status")
    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
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
    @Column(name = "receiptTotal")
    public Integer getReceiptTotal() {
        return receiptTotal;
    }

    public void setReceiptTotal(Integer receiptTotal) {
        this.receiptTotal = receiptTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptEntity that = (ReceiptEntity) o;

        if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;
        if (receiptNumber != null ? !receiptNumber.equals(that.receiptNumber) : that.receiptNumber != null)
            return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (actualDate != null ? !actualDate.equals(that.actualDate) : that.actualDate != null) return false;
        if (receiptTotal != null ? !receiptTotal.equals(that.receiptTotal) : that.receiptTotal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (receiptNumber != null ? receiptNumber.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (actualDate != null ? actualDate.hashCode() : 0);
        result = 31 * result + (receiptTotal != null ? receiptTotal.hashCode() : 0);
        return result;
    }
}
