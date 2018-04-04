package com.nonobank.platformuser.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;


/**
 * Created by tangrubei on 2018/2/28.
 * private String URL = "ldap://192.168.1.200:389/";

 */
public class LdapConf {
    public LdapContextSource contextSourceTarget() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://192.168.1.200:389/");
        ldapContextSource.setBase("DC=server,DC=nonobank,DC=com");
        ldapContextSource.setUserDn("owncloud");
        ldapContextSource.setPassword("Shield@NONO");
        ldapContextSource.afterPropertiesSet();


        return ldapContextSource;
    }

    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSourceTarget());
    }

    public static void main(String[] args) throws Exception {

        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://192.168.1.200:389/");
        ldapContextSource.setBase("DC=server,DC=nonobank,DC=com");
        ldapContextSource.setUserDn("owncloud");
        ldapContextSource.setPassword("Shield@NONO");
        ldapContextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource);
        ldapTemplate.afterPropertiesSet();

        Filter filter = new EqualsFilter("mail", "tangrubei@nonobank.com");

        boolean authed = ldapTemplate.authenticate("OU=nonobank",
                filter.encode(),
                "Pass2018@");

        System.out.println(authed);


//        List<User> list = ldapTemplate.search("OU=nonobank", filter.encode(), new AttributesMapper() {
//
//            public Object mapFromAttributes(Attributes attributes) throws NamingException, javax.naming.NamingException {
//                User user = new User();
//
//                Attribute a = attributes.get("distinguishedName");
//                if (a != null) user.setRealname((String) a.get());
//
//                return user;
//            }
//        });
        System.out.println("ok");




// Display the results.
//        System.out.println("Authenticated: " + authed);


}

}
