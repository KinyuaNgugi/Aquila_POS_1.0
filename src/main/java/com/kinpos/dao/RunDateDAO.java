package com.kinpos.dao;

import com.kinpos.models.RunDateTableEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface RunDateDAO {
    List<RunDateTableEntity> getAllMyRunDates();

    List<RunDateTableEntity> getMyActiveRunDate();

    RunDateTableEntity getMyRunDate(Integer myRunDateTableEntityId);

    RunDateTableEntity saveMyRunDate(RunDateTableEntity runDateTableEntity);

    RunDateTableEntity updateMyRunDate(RunDateTableEntity runDateTableEntity);

    void purgeMyRunDate(RunDateTableEntity runDateTableEntity);
}
