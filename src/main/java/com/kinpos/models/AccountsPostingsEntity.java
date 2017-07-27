package com.kinpos.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by openworldkin on 5/16/17.
 */
@Entity
@Table(name = "accounts_postings", schema = "pos2", catalog = "")
public class AccountsPostingsEntity {
    private int id;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer orgId;

    @Basic
    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    private Integer accountId;

    @Basic
    @Column(name = "account_id")
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    private float credit;

    @Basic
    @Column(name = "credit")
    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    private float debit;

    @Basic
    @Column(name = "debit")
    public float getDebit() {
        return debit;
    }

    public void setDebit(float debit) {
        this.debit = debit;
    }

    private String currency;

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private Double rate;

    @Basic
    @Column(name = "rate")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    private String description;


    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    private Date date;

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountsPostingsEntity that = (AccountsPostingsEntity) o;

        if (id != that.id) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
