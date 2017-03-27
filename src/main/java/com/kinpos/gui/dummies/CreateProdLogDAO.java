package com.kinpos.gui.dummies;

import com.kinpos.models.CreateProdLogEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface CreateProdLogDAO {
    List<CreateProdLogEntity> getAllMyRunDates();

    CreateProdLogEntity getMyRunDate(Integer myCreateProdLogEntityId);

    CreateProdLogEntity saveMyRunDate(CreateProdLogEntity createProdLogEntity);

    CreateProdLogEntity updateMyRunDate(CreateProdLogEntity createProdLogEntity);

    void purgeMyRunDate(CreateProdLogEntity createProdLogEntity);
}
