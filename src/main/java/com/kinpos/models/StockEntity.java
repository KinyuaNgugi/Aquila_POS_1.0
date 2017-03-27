package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by kinyua on 7/20/15.
 */
@Entity
@Table(name = "stock", schema = "", catalog = "pos")
public class StockEntity {
    private String productCode;
    private String productName;
    private Integer buyingPricePerUnit;
    private Integer sellingPricePerUnit;
    private Double margin;
    private Integer reorderLevel;
    private Integer supplierId;
    private Integer unitsInStock;
    private Integer stockId;
    private Integer packing;
    private Double vatAmount;
    private String vatable;
    private String marginOrPercent;
    private Integer actualMargin;

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
    @Column(name = "buyingPricePerUnit")
    public Integer getBuyingPricePerUnit() {
        return buyingPricePerUnit;
    }

    public void setBuyingPricePerUnit(Integer buyingPricePerUnit) {
        this.buyingPricePerUnit = buyingPricePerUnit;
    }

    @Basic
    @Column(name = "sellingPricePerUnit")
    public Integer getSellingPricePerUnit() {
        return sellingPricePerUnit;
    }

    public void setSellingPricePerUnit(Integer sellingPricePerUnit) {
        this.sellingPricePerUnit = sellingPricePerUnit;
    }

    @Basic
    @Column(name = "margin")
    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    @Basic
    @Column(name = "reorderLevel")
    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
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
    @Column(name = "unitsInStock")
    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    @Id
    @Column(name = "stockId")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
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
    @Column(name = "vatAmount")
    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    @Basic
    @Column(name = "vatable")
    public String getVatable() {
        return vatable;
    }

    public void setVatable(String vatable) {
        this.vatable = vatable;
    }

    @Basic
    @Column(name = "marginOrPercent")
    public String getMarginOrPercent() {
        return marginOrPercent;
    }

    public void setMarginOrPercent(String marginOrPercent) {
        this.marginOrPercent = marginOrPercent;
    }

    @Basic
    @Column(name = "actualMargin")
    public Integer getActualMargin() {
        return actualMargin;
    }

    public void setActualMargin(Integer actualMargin) {
        this.actualMargin = actualMargin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntity that = (StockEntity) o;

        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (buyingPricePerUnit != null ? !buyingPricePerUnit.equals(that.buyingPricePerUnit) : that.buyingPricePerUnit != null)
            return false;
        if (sellingPricePerUnit != null ? !sellingPricePerUnit.equals(that.sellingPricePerUnit) : that.sellingPricePerUnit != null)
            return false;
        if (margin != null ? !margin.equals(that.margin) : that.margin != null) return false;
        if (reorderLevel != null ? !reorderLevel.equals(that.reorderLevel) : that.reorderLevel != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (unitsInStock != null ? !unitsInStock.equals(that.unitsInStock) : that.unitsInStock != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (packing != null ? !packing.equals(that.packing) : that.packing != null) return false;
        if (vatAmount != null ? !vatAmount.equals(that.vatAmount) : that.vatAmount != null) return false;
        if (vatable != null ? !vatable.equals(that.vatable) : that.vatable != null) return false;
        if (marginOrPercent != null ? !marginOrPercent.equals(that.marginOrPercent) : that.marginOrPercent != null)
            return false;
        if (actualMargin != null ? !actualMargin.equals(that.actualMargin) : that.actualMargin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productCode != null ? productCode.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (buyingPricePerUnit != null ? buyingPricePerUnit.hashCode() : 0);
        result = 31 * result + (sellingPricePerUnit != null ? sellingPricePerUnit.hashCode() : 0);
        result = 31 * result + (margin != null ? margin.hashCode() : 0);
        result = 31 * result + (reorderLevel != null ? reorderLevel.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (unitsInStock != null ? unitsInStock.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (packing != null ? packing.hashCode() : 0);
        result = 31 * result + (vatAmount != null ? vatAmount.hashCode() : 0);
        result = 31 * result + (vatable != null ? vatable.hashCode() : 0);
        result = 31 * result + (marginOrPercent != null ? marginOrPercent.hashCode() : 0);
        result = 31 * result + (actualMargin != null ? actualMargin.hashCode() : 0);
        return result;
    }
}
