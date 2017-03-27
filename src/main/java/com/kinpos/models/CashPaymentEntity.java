package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 7/24/15.
 */
@Entity
@Table(name = "cashpayment", schema = "", catalog = "pos")
public class CashPaymentEntity {
    private Integer cashPaymentId;
    private Integer amountPaid;
    private Date dateOfPayment;
    private Integer userId;
    private Integer supplierId;
    private Integer tillId;
    private Date runDate;
    private Boolean zedClear;

    @Id
    @Column(name = "cashPaymentId")
    public Integer getCashPaymentId() {
        return cashPaymentId;
    }

    public void setCashPaymentId(Integer cashPaymentId) {
        this.cashPaymentId = cashPaymentId;
    }

    @Basic
    @Column(name = "amountPaid")
    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Basic
    @Column(name = "dateOfPayment")
    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
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
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashPaymentEntity that = (CashPaymentEntity) o;

        if (cashPaymentId != null ? !cashPaymentId.equals(that.cashPaymentId) : that.cashPaymentId != null)
            return false;
        if (amountPaid != null ? !amountPaid.equals(that.amountPaid) : that.amountPaid != null) return false;
        if (dateOfPayment != null ? !dateOfPayment.equals(that.dateOfPayment) : that.dateOfPayment != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (tillId != null ? !tillId.equals(that.tillId) : that.tillId != null) return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cashPaymentId != null ? cashPaymentId.hashCode() : 0;
        result = 31 * result + (amountPaid != null ? amountPaid.hashCode() : 0);
        result = 31 * result + (dateOfPayment != null ? dateOfPayment.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (tillId != null ? tillId.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        return result;
    }
}
