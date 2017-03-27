package com.kinpos.dao;

import com.kinpos.models.SupplierEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface SupplierDAO {
    List<SupplierEntity> getAllMySuppliers();
    SupplierEntity getMySupplier(Integer mySupplierEntityId);
    SupplierEntity saveMySupplier(SupplierEntity supplierEntity);
    SupplierEntity updateMySupplier(SupplierEntity supplierEntity);
    void purgeMySupplier(SupplierEntity supplierEntity);
    List<SupplierEntity> getMySupplierBySupplierName(String supName);
}
