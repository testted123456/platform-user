package com.nonobank.platformuser.repository.mysqlRepository;

import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by tangrubei on 2018/4/11.
 */
public interface RoleUrlPathRepository extends JpaRepository<RoleUrlPath, Integer> {

    List<RoleUrlPath> findBySystemEqualsAndOptstatusNot(String system,Short optstatus);
}