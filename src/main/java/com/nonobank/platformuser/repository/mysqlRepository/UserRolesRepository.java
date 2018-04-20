package com.nonobank.platformuser.repository.mysqlRepository;

import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tangrubei on 2018/4/11.
 */
public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
	
	UserRoles findByUserId(Integer id);
}
