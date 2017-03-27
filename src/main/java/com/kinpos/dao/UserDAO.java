package com.kinpos.dao;

import com.kinpos.models.UserEntity;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public interface UserDAO {
    List<UserEntity> getAllMyUsers();

    UserEntity getMyUser(Integer myUserEntityId);

    UserEntity saveMyUser(UserEntity userEntity);

    UserEntity updateMyUser(UserEntity userEntity);

    void purgeMyUser(UserEntity userEntity);

    public List<UserEntity> getMyUserByUserName(String userName);
}
