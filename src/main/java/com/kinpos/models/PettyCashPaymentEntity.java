package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 7/24/15.
 */
@Entity
@Table(name = "petty_cash_payment", schema = "", catalog = "pos2")
public class PettyCashPaymentEntity {
    private Integer pettyCashPaymentId;
    private Integer amountPaid;
    private Date dateOfPayment;
    private Integer userId;
    private Integer tillId;
    private Date runDate;
    private String payee;
    private Boolean zedClear;

    @Id
    @Column(name = "pettyCashPaymentId")
    public Integer getPettyCashPaymentId() {
        return pettyCashPaymentId;
    }

    public void setPettyCashPaymentId(Integer pettyCashPaymentId) {
        this.pettyCashPaymentId = pettyCashPaymentId;
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
    @Column(name = "payee")
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
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

        PettyCashPaymentEntity that = (PettyCashPaymentEntity) o;

        if (pettyCashPaymentId != null ? !pettyCashPaymentId.equals(that.pettyCashPaymentId) : that.pettyCashPaymentId != null)
            return false;
        if (amountPaid != null ? !amountPaid.equals(that.amountPaid) : that.amountPaid != null) return false;
        if (dateOfPayment != null ? !dateOfPayment.equals(that.dateOfPayment) : that.dateOfPayment != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (tillId != null ? !tillId.equals(that.tillId) : that.tillId != null) return false;
        if (runDate != null ? !runDate.equals(that.runDate) : that.runDate != null) return false;
        if (payee != null ? !payee.equals(that.payee) : that.payee != null) return false;
        if (zedClear != null ? !zedClear.equals(that.zedClear) : that.zedClear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pettyCashPaymentId != null ? pettyCashPaymentId.hashCode() : 0;
        result = 31 * result + (amountPaid != null ? amountPaid.hashCode() : 0);
        result = 31 * result + (dateOfPayment != null ? dateOfPayment.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (tillId != null ? tillId.hashCode() : 0);
        result = 31 * result + (runDate != null ? runDate.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + (zedClear != null ? zedClear.hashCode() : 0);
        return result;
    }
}
