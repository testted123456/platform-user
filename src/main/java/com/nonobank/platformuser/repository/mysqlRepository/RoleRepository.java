package com.nonobank.platformuser.repository.mysqlRepository;

import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("select new com.nonobank.platformuser.entity.mysqlEntity.Role(id,roleName) from Role where id in (select roleId from UserRoles where userId=:userId) and optstatus <> 2")
    List<Role> findRoleByUserId(@Param("userId") Integer userId);

    Role findRoleByRoleNameEqualsAndOptstatusNot(String roleName,Short optStatus);
    
    Role findByRoleName(String name);
    
    Role findById(Integer id);

}
