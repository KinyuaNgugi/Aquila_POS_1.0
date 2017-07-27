package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by openworldkin on 5/16/17.
 */
@Entity
@Table(name = "country_tax_rates", schema = "pos2", catalog = "")
public class CountryTaxRatesEntity {
    private int id;
    private String taxRateName;
    private Integer taxId;
    private float rate;
    private Byte supermarketApplicable;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tax_rate_name")
    public String getTaxRateName() {
        return taxRateName;
    }

    public void setTaxRateName(String taxRateName) {
        this.taxRateName = taxRateName;
    }

    @Basic
    @Column(name = "tax_id")
    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    @Basic
    @Column(name = "rate")
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "supermarket_applicable")
    public Byte getSupermarketApplicable() {
        return supermarketApplicable;
    }

    public void setSupermarketApplicable(Byte supermarketApplicable) {
        this.supermarketApplicable = supermarketApplicable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryTaxRatesEntity that = (CountryTaxRatesEntity) o;

        if (id != that.id) return false;
        if (taxRateName != null ? !taxRateName.equals(that.taxRateName) : that.taxRateName != null) return false;
        if (taxId != null ? !taxId.equals(that.taxId) : that.taxId != null) return false;
        if (supermarketApplicable != null ? !supermarketApplicable.equals(that.supermarketApplicable) : that.supermarketApplicable != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (taxRateName != null ? taxRateName.hashCode() : 0);
        result = 31 * result + (taxId != null ? taxId.hashCode() : 0);
        result = 31 * result + (supermarketApplicable != null ? supermarketApplicable.hashCode() : 0);
        return result;
    }
}
