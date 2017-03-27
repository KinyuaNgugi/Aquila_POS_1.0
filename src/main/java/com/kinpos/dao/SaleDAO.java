package com.kinpos.dao;

import com.kinpos.models.SaleEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface SaleDAO {
    List<SaleEntity> getAllMySales();

    List<SaleEntity> getAllMyUnclearedSales();

    SaleEntity getMySale(Integer mySaleEntityId);

    SaleEntity saveMySale(SaleEntity saleEntity);

    SaleEntity updateMySale(SaleEntity saleEntity);

    void purgeMySale(SaleEntity saleEntity);

    List<SaleEntity> getCreditSalesByReceipt(String receipt, java.sql.Date runDate);
}
