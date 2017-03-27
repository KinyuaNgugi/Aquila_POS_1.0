package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by kinyua on 8/4/15.
 */
@Entity
@Table(name = "chequepayment", schema = "", catalog = "pos")
public class ChequePaymentEntity {
    private Integer chequePaymentId;
    private Date dateOfIssue;
    private String payee;
    private Date dateDue;
    private Integer amountPaid;
    private Integer supplierId;
    private Integer userId;
    private String chequeNumber;
    private Boolean paid;

    @Id
    @Column(name = "chequePaymentId")
    public Integer getChequePaymentId() {
        return chequePaymentId;
    }

    public void setChequePaymentId(Integer chequePaymentId) {
        this.chequePaymentId = chequePaymentId;
    }

    @Basic
    @Column(name = "dateOfIssue")
    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
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
    @Column(name = "dateDue")
    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
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
    @Column(name = "supplierId")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
    @Column(name = "chequeNumber")
    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    @Basic
    @Column(name = "paid")
    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChequePaymentEntity that = (ChequePaymentEntity) o;

        if (chequePaymentId != null ? !chequePaymentId.equals(that.chequePaymentId) : that.chequePaymentId != null)
            return false;
        if (dateOfIssue != null ? !dateOfIssue.equals(that.dateOfIssue) : that.dateOfIssue != null) return false;
        if (payee != null ? !payee.equals(that.payee) : that.payee != null) return false;
        if (dateDue != null ? !dateDue.equals(that.dateDue) : that.dateDue != null) return false;
        if (amountPaid != null ? !amountPaid.equals(that.amountPaid) : that.amountPaid != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (chequeNumber != null ? !chequeNumber.equals(that.chequeNumber) : that.chequeNumber != null) return false;
        if (paid != null ? !paid.equals(that.paid) : that.paid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chequePaymentId != null ? chequePaymentId.hashCode() : 0;
        result = 31 * result + (dateOfIssue != null ? dateOfIssue.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + (dateDue != null ? dateDue.hashCode() : 0);
        result = 31 * result + (amountPaid != null ? amountPaid.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (chequeNumber != null ? chequeNumber.hashCode() : 0);
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        return result;
    }
}
