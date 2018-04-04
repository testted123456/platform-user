package com.nonobank.platformuser.component;

import com.nonobank.platformuser.entity.ldapEntity.LdapUserEntity;
import com.nonobank.platformuser.utils.CharsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.stereotype.Component;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by tangrubei on 2018/2/28.
 */
@Component
public class LdapComponent {
    @Autowired
    private LdapTemplate ldapTemplate;


    private final String LDAP_BASE = "OU=nonobank";


    //  查询后缀
    private static final String MAI_SUFFIX = "@nonobank.com";

    //  查询属性
    private static final String ATT_FILTER_NAME = "mail";


    //  创建用户名的过滤器
    private static Function<String, Filter> function_mailFilter = username -> new EqualsFilter(ATT_FILTER_NAME, username + MAI_SUFFIX);


    /**
     * 根据attr属性，动态构建
     *
     * @return
     */
    private static AttributesMapper getAttMappter() {
        return new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws javax.naming.NamingException {
                LdapUserEntity ldapUserEntity = new LdapUserEntity();
                Field[] fields = ldapUserEntity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Attribute att = attributes.get(field.getName());
                    String setMethodName = CharsUtil.createSetMethodName(CharsUtil.functionFirstLetterUp.apply(field.getName()));
                    try {
                        Method method = ldapUserEntity.getClass().getDeclaredMethod(setMethodName, String.class);
                        if (att != null) {
                            method.invoke(ldapUserEntity, String.valueOf(att.get()));
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getCause() + e.getMessage());
                    }
                }
                return ldapUserEntity;
            }
        };
    }


    /**
     * 根据用户名密码校验用户是否可登陆
     *
     * @param username 用户名
     * @param Password 密码
     * @return
     */
    public boolean loginCheck(String username, String Password) {
        return ldapTemplate.authenticate(LDAP_BASE, function_mailFilter.apply(username).encode(), Password);
    }

    /**
     * 根据用户名查找用户信息
     *
     * @param username
     * @return 返回封装好的用户信息
     */
    public LdapUserEntity getUserInfo(String username) {
        List<LdapUserEntity> list = ldapTemplate.search(LDAP_BASE, function_mailFilter.apply(username).encode(), LdapComponent.getAttMappter());
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        LdapUserEntity ldapUserEntity = new LdapUserEntity();

        Field[] fields = ldapUserEntity.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(f -> System.out.println(f.getName()));
        System.out.println("ok");

    }

}
