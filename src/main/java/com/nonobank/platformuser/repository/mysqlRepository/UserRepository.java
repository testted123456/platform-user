package com.nonobank.platformuser.repository.mysqlRepository;

import com.nonobank.platformuser.entity.mysqlEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByIdEquals(Integer id);

    User findByUsernameEqualsAndOptstatusNot(String username,Short options);

}
