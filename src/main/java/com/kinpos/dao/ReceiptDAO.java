package com.kinpos.dao;

import com.kinpos.models.ReceiptEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface ReceiptDAO {
    List<ReceiptEntity> getAllMyReceipts();

    ReceiptEntity getMyReceipt(Integer myReceiptEntityId);

    ReceiptEntity saveMyReceipt(ReceiptEntity receiptEntity);

    ReceiptEntity updateMyReceipt(ReceiptEntity receiptEntity);

    void purgeMyReceipt(ReceiptEntity receiptEntity);
}
