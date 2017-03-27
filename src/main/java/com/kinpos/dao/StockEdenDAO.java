package com.kinpos.dao;

import com.kinpos.models.StockedenEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface StockEdenDAO {
    List<StockedenEntity> getAllMyRunDates();

    StockedenEntity getMyRunDate(Integer myStockedenEntityId);

    StockedenEntity saveMyRunDate(StockedenEntity stockedenEntity);

    StockedenEntity updateMyRunDate(StockedenEntity stockedenEntity);

    void purgeMyRunDate(StockedenEntity stockedenEntity);
}
