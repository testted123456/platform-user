package com.nonobank.platformuser.repository.mysqlRepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nonobank.platformuser.entity.mysqlEntity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByIdEquals(Integer id);

    User findByUsernameEqualsAndOptstatusNot(String username,Short options);
    
    List<User> findByUsernameLike(String username);
    
    @Query(value="select u.id, u.username,u.nickname, ur.role_id from user u left join user_roles ur on u.id=ur.user_id ", nativeQuery = true)
//    @Query("select new com.nonobank.platformuser.entity.mysqlEntity.UserFront(id,username,roleId) from Role where id in (select roleId from UserRoles where userId=:userId) and optstatus <> 2")

    List<Object[]> findAllUsers();

}
