package com.nonobank.platformuser.db;

import com.nonobank.platformuser.component.LdapComponent;
import com.nonobank.platformuser.entity.ldapEntity.LdapUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tangrubei on 2018/2/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapTest {
//    @Autowired
//    private LdapTemplate ldapTemplate;


    @Autowired
    private LdapComponent ldapComponent;



    @Test
    public void test(){

        boolean b = ldapComponent.loginCheck("tangrubei","Pass2018@");
//        System.out.println(b);
//
//        LdapUserEntity ldapUserEntity = ldapComponent.getUserInfo("tangrubei");
//        System.out.println(ldapUserEntity.getDistinguishedName());

    }
}
