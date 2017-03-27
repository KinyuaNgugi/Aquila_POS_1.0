package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by kinyua on 4/14/16.
 */
@Entity
@Table(name = "stockeden", schema = "", catalog = "pos")
public class StockedenEntity {
    private String scancode;
    private String descr;
    private String vat;
    private Integer price;
    private Integer stockedenId;

    @Basic
    @Column(name = "scancode")
    public String getScancode() {
        return scancode;
    }

    public void setScancode(String scancode) {
        this.scancode = scancode;
    }

    @Basic
    @Column(name = "descr")
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Basic
    @Column(name = "vat")
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockedenEntity that = (StockedenEntity) o;

        if (scancode != null ? !scancode.equals(that.scancode) : that.scancode != null) return false;
        if (descr != null ? !descr.equals(that.descr) : that.descr != null) return false;
        if (vat != null ? !vat.equals(that.vat) : that.vat != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scancode != null ? scancode.hashCode() : 0;
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        result = 31 * result + (vat != null ? vat.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Id
    @Column(name = "stockedenId")
    public Integer getStockedenId() {
        return stockedenId;
    }

    public void setStockedenId(Integer stockedenId) {
        this.stockedenId = stockedenId;
    }
}
