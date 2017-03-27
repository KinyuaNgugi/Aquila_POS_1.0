package com.kinpos.models;

import javax.persistence.*;

/**
 * Created by kinyua on 6/30/15.
 */
@Entity
@Table(name = "user", schema = "", catalog = "pos")
public class UserEntity {
    private Integer userId;

    @Id
    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private String userName;

    //private EmployeeEntity employee;

   /* @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",insertable = false,updatable = false,nullable= false)
    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }*/

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String role;

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private Integer employeeId;

    @Basic
    @Column(name = "employeeId")
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    private Boolean createEditProduct;

    @Basic
    @Column(name = "CreateEditProduct")
    public Boolean getCreateEditProduct() {
        return createEditProduct;
    }

    public void setCreateEditProduct(Boolean createEditProduct) {
        this.createEditProduct = createEditProduct;
    }

    private Boolean manageStock;

    @Basic
    @Column(name = "manageStock")
    public Boolean getManageStock() {
        return manageStock;
    }

    public void setManageStock(Boolean manageStock) {
        this.manageStock = manageStock;
    }

    private Boolean createEditSupplier;

    @Basic
    @Column(name = "createEditSupplier")
    public Boolean getCreateEditSupplier() {
        return createEditSupplier;
    }

    public void setCreateEditSupplier(Boolean createEditSupplier) {
        this.createEditSupplier = createEditSupplier;
    }

    private Boolean createEditUserAndRights;

    @Basic
    @Column(name = "createEditUserAndRights")
    public Boolean getCreateEditUserAndRights() {
        return createEditUserAndRights;
    }

    public void setCreateEditUserAndRights(Boolean createEditUserAndRights) {
        this.createEditUserAndRights = createEditUserAndRights;
    }

    private Boolean createEditPayDeleteEmployee;

    @Basic
    @Column(name = "createEditPayDeleteEmployee")
    public Boolean getCreateEditPayDeleteEmployee() {
        return createEditPayDeleteEmployee;
    }

    public void setCreateEditPayDeleteEmployee(Boolean createEditPayDeleteEmployee) {
        this.createEditPayDeleteEmployee = createEditPayDeleteEmployee;
    }

    private Boolean enterGoodsReceived;

    @Basic
    @Column(name = "enterGoodsReceived")
    public Boolean getEnterGoodsReceived() {
        return enterGoodsReceived;
    }

    public void setEnterGoodsReceived(Boolean enterGoodsReceived) {
        this.enterGoodsReceived = enterGoodsReceived;
    }

    private Boolean adjustStock;

    @Basic
    @Column(name = "adjustStock")
    public Boolean getAdjustStock() {
        return adjustStock;
    }

    public void setAdjustStock(Boolean adjustStock) {
        this.adjustStock = adjustStock;
    }

    private Boolean viewPurchaseSaleReports;

    @Basic
    @Column(name = "viewPurchaseSaleReports")
    public Boolean getViewPurchaseSaleReports() {
        return viewPurchaseSaleReports;
    }

    public void setViewPurchaseSaleReports(Boolean viewPurchaseSaleReports) {
        this.viewPurchaseSaleReports = viewPurchaseSaleReports;
    }

    private Boolean enterCheque;

    @Basic
    @Column(name = "enterCheque")
    public Boolean getEnterCheque() {
        return enterCheque;
    }

    public void setEnterCheque(Boolean enterCheque) {
        this.enterCheque = enterCheque;
    }

    private Boolean viewChequeCashPayments;

    @Basic
    @Column(name = "viewChequeCashPayments")
    public Boolean getViewChequeCashPayments() {
        return viewChequeCashPayments;
    }

    public void setViewChequeCashPayments(Boolean viewChequeCashPayments) {
        this.viewChequeCashPayments = viewChequeCashPayments;
    }

    private Boolean directSales;

    @Basic
    @Column(name = "directSales")
    public Boolean getDirectSales() {
        return directSales;
    }

    public void setDirectSales(Boolean directSales) {
        this.directSales = directSales;
    }

    private Boolean viewPosSidePayments;

    @Basic
    @Column(name = "viewPosSidePayments")
    public Boolean getViewPosSidePayments() {
        return viewPosSidePayments;
    }

    public void setViewPosSidePayments(Boolean viewPosSidePayments) {
        this.viewPosSidePayments = viewPosSidePayments;
    }

    private Boolean viewPosSideReceipts;

    @Basic
    @Column(name = "viewPosSideReceipts")
    public Boolean getViewPosSideReceipts() {
        return viewPosSideReceipts;
    }

    public void setViewPosSideReceipts(Boolean viewPosSideReceipts) {
        this.viewPosSideReceipts = viewPosSideReceipts;
    }

    private Boolean reprintReceipt;

    @Basic
    @Column(name = "reprintReceipt")
    public Boolean getReprintReceipt() {
        return reprintReceipt;
    }

    public void setReprintReceipt(Boolean reprintReceipt) {
        this.reprintReceipt = reprintReceipt;
    }

    private Boolean viewDailySales;

    @Basic
    @Column(name = "viewDailySales")
    public Boolean getViewDailySales() {
        return viewDailySales;
    }

    public void setViewDailySales(Boolean viewDailySales) {
        this.viewDailySales = viewDailySales;
    }

    private Boolean operationStatus;

    @Basic
    @Column(name = "operationStatus")
    public Boolean getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(Boolean operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (createEditProduct != null ? !createEditProduct.equals(that.createEditProduct) : that.createEditProduct != null)
            return false;
        if (manageStock != null ? !manageStock.equals(that.manageStock) : that.manageStock != null) return false;
        if (createEditSupplier != null ? !createEditSupplier.equals(that.createEditSupplier) : that.createEditSupplier != null)
            return false;
        if (createEditUserAndRights != null ? !createEditUserAndRights.equals(that.createEditUserAndRights) : that.createEditUserAndRights != null)
            return false;
        if (createEditPayDeleteEmployee != null ? !createEditPayDeleteEmployee.equals(that.createEditPayDeleteEmployee) : that.createEditPayDeleteEmployee != null)
            return false;
        if (enterGoodsReceived != null ? !enterGoodsReceived.equals(that.enterGoodsReceived) : that.enterGoodsReceived != null)
            return false;
        if (adjustStock != null ? !adjustStock.equals(that.adjustStock) : that.adjustStock != null) return false;
        if (viewPurchaseSaleReports != null ? !viewPurchaseSaleReports.equals(that.viewPurchaseSaleReports) : that.viewPurchaseSaleReports != null)
            return false;
        if (enterCheque != null ? !enterCheque.equals(that.enterCheque) : that.enterCheque != null) return false;
        if (viewChequeCashPayments != null ? !viewChequeCashPayments.equals(that.viewChequeCashPayments) : that.viewChequeCashPayments != null)
            return false;
        if (directSales != null ? !directSales.equals(that.directSales) : that.directSales != null) return false;
        if (viewPosSidePayments != null ? !viewPosSidePayments.equals(that.viewPosSidePayments) : that.viewPosSidePayments != null)
            return false;
        if (viewPosSideReceipts != null ? !viewPosSideReceipts.equals(that.viewPosSideReceipts) : that.viewPosSideReceipts != null)
            return false;
        if (reprintReceipt != null ? !reprintReceipt.equals(that.reprintReceipt) : that.reprintReceipt != null)
            return false;
        if (viewDailySales != null ? !viewDailySales.equals(that.viewDailySales) : that.viewDailySales != null)
            return false;
        if (operationStatus != null ? !operationStatus.equals(that.operationStatus) : that.operationStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
        result = 31 * result + (createEditProduct != null ? createEditProduct.hashCode() : 0);
        result = 31 * result + (manageStock != null ? manageStock.hashCode() : 0);
        result = 31 * result + (createEditSupplier != null ? createEditSupplier.hashCode() : 0);
        result = 31 * result + (createEditUserAndRights != null ? createEditUserAndRights.hashCode() : 0);
        result = 31 * result + (createEditPayDeleteEmployee != null ? createEditPayDeleteEmployee.hashCode() : 0);
        result = 31 * result + (enterGoodsReceived != null ? enterGoodsReceived.hashCode() : 0);
        result = 31 * result + (adjustStock != null ? adjustStock.hashCode() : 0);
        result = 31 * result + (viewPurchaseSaleReports != null ? viewPurchaseSaleReports.hashCode() : 0);
        result = 31 * result + (enterCheque != null ? enterCheque.hashCode() : 0);
        result = 31 * result + (viewChequeCashPayments != null ? viewChequeCashPayments.hashCode() : 0);
        result = 31 * result + (directSales != null ? directSales.hashCode() : 0);
        result = 31 * result + (viewPosSidePayments != null ? viewPosSidePayments.hashCode() : 0);
        result = 31 * result + (viewPosSideReceipts != null ? viewPosSideReceipts.hashCode() : 0);
        result = 31 * result + (reprintReceipt != null ? reprintReceipt.hashCode() : 0);
        result = 31 * result + (viewDailySales != null ? viewDailySales.hashCode() : 0);
        result = 31 * result + (operationStatus != null ? operationStatus.hashCode() : 0);
        return result;
    }
}
