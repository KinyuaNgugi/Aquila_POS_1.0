package com.kinpos.dao;

import com.kinpos.models.StockEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface StockDAO {
    List<StockEntity> getAllMyStocks();

    StockEntity getMyStock(Integer myStockEntityId);

    StockEntity saveMyStock(StockEntity stockEntity);

    StockEntity updateMyStock(StockEntity stockEntity);

    void purgeMyStock(StockEntity stockEntity);

    List<StockEntity> getMyStockByProductCode(String productCode);
}
