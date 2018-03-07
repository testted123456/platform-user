package com.nonobank.platformuser.dto.dao;

import com.nonobank.platformuser.entity.mongoEntity.UserrolesEntity;

/**
 * Created by tangrubei on 2018/3/5.
 */
public interface UserRolesDao {
    UserrolesEntity getUserRolesByUserId(String uesrId);
}
