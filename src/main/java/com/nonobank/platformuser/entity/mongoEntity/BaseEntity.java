package com.nonobank.platformuser.entity.mongoEntity;

import com.nonobank.platformuser.component.MyUserException;
import com.nonobank.platformuser.conf.ResponseCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangrubei on 2018/3/5.
 */
public class BaseEntity {

    private static final Pattern ENTITY_NAME_REGEX = Pattern.compile("(\\w+)Entity$");


    public String getCollectionName(){

        Matcher matcher = ENTITY_NAME_REGEX.matcher(this.getClass().getName());

        if(matcher.find()){
            return String.valueOf(matcher.group(1)).toLowerCase();
        }else{
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(),"数据库对象"+this.getClass()+"不存在");
        }
    }
}
