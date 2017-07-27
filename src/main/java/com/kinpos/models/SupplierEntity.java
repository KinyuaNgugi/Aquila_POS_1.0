package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by kinyua on 9/6/15.
 */
@Entity
@Table(name = "supplier", schema = "", catalog = "pos2")
public class SupplierEntity {
    private String supplierName;
    private Integer supplierId;
    private String vatNumber;
    private String kraPin;
    private String phoneNumber;
    private String email;
    private int methodOfPayment;

    @Basic
    @Column(name = "supplierName")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Id
    @Column(name = "supplierId")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "vatNumber")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Basic
    @Column(name = "kraPin")
    public String getKraPin() {
        return kraPin;
    }

    public void setKraPin(String kraPin) {
        this.kraPin = kraPin;
    }

    @Basic
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "methodOfPayment")
    public int getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(int methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierEntity that = (SupplierEntity) o;

        if (supplierName != null ? !supplierName.equals(that.supplierName) : that.supplierName != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (vatNumber != null ? !vatNumber.equals(that.vatNumber) : that.vatNumber != null) return false;
        if (kraPin != null ? !kraPin.equals(that.kraPin) : that.kraPin != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierName != null ? supplierName.hashCode() : 0;
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (vatNumber != null ? vatNumber.hashCode() : 0);
        result = 31 * result + (kraPin != null ? kraPin.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
