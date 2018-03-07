package com.nonobank.platformuser.dto.dao;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;

/**
 * Created by tangrubei on 2018/2/28.
 */

public interface UsersDao {


    void saveUser(UsersEntity usersEntity);

    UsersEntity getUserById(String id);

    UsersEntity getUserByName(String Name);



}
