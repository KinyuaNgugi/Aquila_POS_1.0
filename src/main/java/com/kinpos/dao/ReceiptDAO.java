package com.kinpos.dao;

import com.kinpos.models.IncomeEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface ReceiptDAO {
    List<IncomeEntity> getAllMyReceipts();

    List<IncomeEntity> getAllMyUnclearedReceipts();

    IncomeEntity getMyReceipt(Integer myReceiptEntityId);

    IncomeEntity saveMyReceipt(IncomeEntity incomeEntity);

    IncomeEntity updateMyReceipt(IncomeEntity incomeEntity);

    void purgeMyReceipt(IncomeEntity incomeEntity);
}
