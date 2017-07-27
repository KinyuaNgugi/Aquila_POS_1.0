package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by openworldkin on 5/16/17.
 */
@Entity
@Table(name = "stock", schema = "pos2", catalog = "")
public class StockEntity {
    private int stockId;
    private float buyingPricePerUnit;
    private Integer packing;
    private String productCode;
    private String productName;
    private int reorderLevel;
    private float sellingPricePerUnit;
    private int supplierId;
    private Integer unitsInStock;
    private int vat;

    @Id
    @Column(name = "stockId")
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    @Basic
    @Column(name = "buyingPricePerUnit")
    public float getBuyingPricePerUnit() {
        return buyingPricePerUnit;
    }

    public void setBuyingPricePerUnit(float buyingPricePerUnit) {
        this.buyingPricePerUnit = buyingPricePerUnit;
    }

    @Basic
    @Column(name = "packing")
    public Integer getPacking() {
        return packing;
    }

    public void setPacking(Integer packing) {
        this.packing = packing;
    }

    @Basic
    @Column(name = "productCode")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Basic
    @Column(name = "productName")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "reorderLevel")
    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    @Basic
    @Column(name = "sellingPricePerUnit")
    public float getSellingPricePerUnit() {
        return sellingPricePerUnit;
    }

    public void setSellingPricePerUnit(float sellingPricePerUnit) {
        this.sellingPricePerUnit = sellingPricePerUnit;
    }

    @Basic
    @Column(name = "supplierId")
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "unitsInStock")
    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    @Basic
    @Column(name = "vat")
    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntity that = (StockEntity) o;

        if (stockId != that.stockId) return false;
        if (buyingPricePerUnit != that.buyingPricePerUnit) return false;
        if (reorderLevel != that.reorderLevel) return false;
        if (sellingPricePerUnit != that.sellingPricePerUnit) return false;
        if (supplierId != that.supplierId) return false;
        if (vat != that.vat) return false;
        if (packing != null ? !packing.equals(that.packing) : that.packing != null) return false;
        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (unitsInStock != null ? !unitsInStock.equals(that.unitsInStock) : that.unitsInStock != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stockId;
        result = 31 * result + (packing != null ? packing.hashCode() : 0);
        result = 31 * result + (productCode != null ? productCode.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + reorderLevel;
        result = 31 * result + supplierId;
        result = 31 * result + (unitsInStock != null ? unitsInStock.hashCode() : 0);
        result = 31 * result + vat;
        return result;
    }
}
