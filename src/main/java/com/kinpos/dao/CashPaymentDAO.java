package com.kinpos.dao;

import com.kinpos.models.CashPaymentEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface CashPaymentDAO {
    List<CashPaymentEntity> getAllMyCashPayments();

    List<CashPaymentEntity> getTillCashPayments();

    CashPaymentEntity getMyCashPayment(Integer myCashPaymentEntityId);

    CashPaymentEntity saveMyCashPayment(CashPaymentEntity cashPaymentEntity);

    CashPaymentEntity updateMyCashPayment(CashPaymentEntity cashPaymentEntity);

    void purgeMyCashPayment(CashPaymentEntity cashPaymentEntity);
}
