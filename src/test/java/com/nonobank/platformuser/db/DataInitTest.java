package com.nonobank.platformuser.db;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import com.nonobank.platformuser.repository.MongoRepository;
import com.nonobank.platformuser.repository.mysqlRepository.RoleRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRolesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    MongoRepository mongoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;


    static User createUser(UsersEntity usersEntity){
        User user = new User();
        user.setUsername(String.valueOf(usersEntity.getUsername()));
        user.setNickname(usersEntity.getNickname());
        user.setEmail(usersEntity.getEmail());
        user.setPassword(String.valueOf(usersEntity.getPassword()));
        user.setCreatedBy("system");
        user.setCreatedTime(LocalDateTime.now());
        user.setOrganization(usersEntity.getDepartment());
        user.setOptstatus((short)0);
        return user;

    }

    static UserRoles createUserRoles(User user){
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(user.getId());
        if(String.valueOf(user.getOrganization()).endsWith("QC")){
            userRoles.setRoleId(7);
        }else{
            userRoles.setRoleId(10);
        }

        userRoles.setOptstatus((short)0);
        userRoles.setCreatedTime(LocalDateTime.now());
        userRoles.setCreatedBy("system");
        return userRoles;
    }

    @Test
    public void initData(){
//        List<UsersEntity> usersEntities = (List<UsersEntity>) mongoRepository.getEntitysByWhere(null, UsersEntity.class);
//
//        List<User> users = usersEntities.stream().map(ue->{
//
//            return createUser(ue);
//        }).filter(us->us.getEmail()!=null).collect(Collectors.toList());

//        userRepository.save(users);
//        System.out.println("ok");
//        System.out.println("ok");
        List<User> users = userRepository.findAll();
        List<UserRoles> userRolesList = users.stream().map(us->{
            return createUserRoles(us);
        }).collect(Collectors.toList());
        userRolesRepository.save(userRolesList);
















//        UserRoles userRoles = new UserRoles();
//        userRoles.setRoleId(1);
//        userRoles.setUserId(8);
//        userRoles.setOptstatus((short)0);
//        userRoles.setCreatedBy("System");
//        userRoles.setCreatedTime(LocalDateTime.now());


    }

}
