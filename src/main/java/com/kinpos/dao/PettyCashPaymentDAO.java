package com.kinpos.dao;

import com.kinpos.models.PettyCashPaymentEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface PettyCashPaymentDAO {
    List<PettyCashPaymentEntity> getAllMyPettyCashPayments();

    PettyCashPaymentEntity getMyPettyCashPayment(Integer myPettyCashPaymentEntityId);

    PettyCashPaymentEntity saveMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity);

    PettyCashPaymentEntity updateMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity);

    void purgeMyPettyCashPayment(PettyCashPaymentEntity pettyCashPaymentEntity);
}
