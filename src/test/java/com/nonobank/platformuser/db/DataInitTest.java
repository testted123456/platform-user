package com.nonobank.platformuser.db;

import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import com.nonobank.platformuser.repository.mysqlRepository.RoleRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRolesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangrubei on 2018/4/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataInitTest {
    @Autowired
    RoleRepository roleRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    static UserRoles createUserRoles(User user){
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(user.getId());
        if(String.valueOf(user.getOrganization()).endsWith("QC")){
            userRoles.setRoleId(7);
        }else{
            userRoles.setRoleId(10);
        }

        userRoles.setOptstatus((short)0);
        userRoles.setCreatedTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        userRoles.setCreatedBy("system");
        return userRoles;
    }

    @Test
    public void initData(){
        List<User> users = userRepository.findAll();
        List<UserRoles> userRolesList = users.stream().map(us->{
            return createUserRoles(us);
        }).collect(Collectors.toList());
        userRolesRepository.save(userRolesList);
    }

}
