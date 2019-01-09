package com.nonobank.platformuser.repository.mysqlRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nonobank.platformuser.entity.mysqlEntity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByIdEquals(Integer id);

    User findByUsernameEqualsAndOptstatusNot(String username,Short options);
    
    @Query(value="select u.id, u.username, u.nickname,  ur.role_id, r.role_name from user u, user_roles ur, role r where u.id=ur.user_id and ur.role_id=r.id and u.username like %:username%", nativeQuery = true)
    List<Object[]> findByUsernameLike(@Param("username") String username);
    
//    @Query(value="select u.id, u.username,u.nickname, ur.role_id from user u left join user_roles ur on u.id=ur.user_id ", nativeQuery = true)
//    @Query("select new com.nonobank.platformuser.entity.mysqlEntity.UserFront(id,username,roleId) from Role where id in (select roleId from UserRoles where userId=:userId) and optstatus <> 2")

    @Query(value="select u.id, u.username, u.nickname,  t.role_id, t.role_name from user u left join (select ur.user_id, ur.role_id, r.role_name from user_roles ur, role r where ur.role_id=r.id ) t on u.id=t.user_id", nativeQuery = true)
    List<Object[]> findAllUsers();
    
    @Query(value="select u.id, u.username, u.nickname, r.role_name as role from user u, user_roles ur, role r where u.id=ur.user_id and ur.role_id=r.id and u.optstatus!=2 and r.optstatus!=2 ORDER BY ?#{#pageable}", 
    		 countQuery = "select count(*) from user u where u.optstatus!=2",
    		nativeQuery = true)
    Page<Object[]> findAllUsers(Pageable pageable);

    @Query(value="select u.email, u.nickname from user u, user_roles ur, role r where r.role_name=:role_name and u.id=ur.user_id and ur.role_id=r.id", nativeQuery = true)
    List<Object[]> findUsersByRoleName(@Param("role_name") String role_name);
}
