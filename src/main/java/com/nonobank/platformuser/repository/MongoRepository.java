package com.nonobank.platformuser.repository;

import com.nonobank.platformuser.component.MyUserException;
import com.nonobank.platformuser.entity.mongoEntity.BaseEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangrubei on 2018/3/5.
 */
@Repository
public class MongoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;


    private static final Pattern ENTITY_NAME_REGEX = Pattern.compile("(\\w+)Entity$");


    public static Function<String, Query> generateUsersNameQuery = username -> new Query().addCriteria(Criteria.where("username").is(username));

    public static Function<String, Query> generateRoleNameQuery = rolename -> new Query().addCriteria(Criteria.where("rolename").is(rolename));


    public static Function<String, Query> generateUserRoleByUserIdQuery = userid -> new Query().addCriteria(Criteria.where("user").is(new ObjectId(userid)));





    /**
     * 根据类属性获取entity的名称
     *
     * @param classz
     * @return
     */
    public static String getEntityName(Class<? extends BaseEntity> classz) {
        Matcher matcher = ENTITY_NAME_REGEX.matcher(classz.getName());

        if (matcher.find()) {
            return String.valueOf(matcher.group(1)).toLowerCase();
        } else {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "数据库对象" + classz.getName() + "不存在");
        }
    }


    public BaseEntity getEntityById(String id, Class<? extends BaseEntity> classz) {
        return mongoTemplate.findById(id, classz, getEntityName(classz));
    }

    public void saveEntity(BaseEntity baseEntity) {
        mongoTemplate.save(baseEntity, getEntityName(baseEntity.getClass()));
    }

    public BaseEntity getOneEntityByWhere(Query query, Class<? extends BaseEntity> classz) {
        return mongoTemplate.findOne(query, classz, getEntityName(classz));
    }


    public List<? extends BaseEntity> getEntitysByWhere(Query query, Class<? extends BaseEntity> classz) {
        return mongoTemplate.find(query, classz, getEntityName(classz));
    }

}