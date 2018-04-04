package com.nonobank.platformuser.db;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tangrubei on 2018/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UsersService usersService;


    @Test
    public void querybyname() {
        UsersEntity usersEntity = usersService.getUsersEntityByName("tangrubei");
        System.out.println(
                "ok"
        );
    }

}
