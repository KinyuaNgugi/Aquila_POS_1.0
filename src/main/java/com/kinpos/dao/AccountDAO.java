package com.kinpos.dao;

import com.kinpos.models.AccountEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface AccountDAO {
    List<AccountEntity> getAllMyRunDates();

    AccountEntity getMyRunDate(Integer myAccountEntityId);

    AccountEntity saveMyRunDate(AccountEntity accountEntity);

    AccountEntity updateMyRunDate(AccountEntity accountEntity);

    void purgeMyRunDate(AccountEntity accountEntity);
}
