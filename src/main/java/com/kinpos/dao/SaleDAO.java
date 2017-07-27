package com.kinpos.dao;

import com.kinpos.models.IncomeItemsEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface SaleDAO {
    List<IncomeItemsEntity> getAllMySales();

    List<IncomeItemsEntity> getAllMyUnclearedSales();

    List<IncomeItemsEntity> getItemsForReceipt(Integer receipt_id);

    IncomeItemsEntity getMySale(Integer mySaleEntityId);

    IncomeItemsEntity saveMySale(IncomeItemsEntity incomeItemsEntity);

    IncomeItemsEntity updateMySale(IncomeItemsEntity incomeItemsEntity);

    void purgeMySale(IncomeItemsEntity incomeItemsEntity);

    List<IncomeItemsEntity> getCreditSalesByReceipt(String receipt, java.sql.Date runDate);
}
