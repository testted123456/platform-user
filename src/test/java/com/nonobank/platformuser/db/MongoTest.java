package com.nonobank.platformuser.db;

import com.nonobank.platformuser.dto.MongoRepository;
import com.nonobank.platformuser.entity.mongoEntity.PermissionsEntity;
import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by tangrubei on 2018/2/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {


    @Autowired
    private MongoRepository mongoRepository;

    @Test
    /**
     * 查询测试
     */
    public void exampleTest() {

//        SessionEntity sessionEntity = new SessionEntity();
//        sessionEntity.setExpires(new Date());
//        mongoRepository.saveEntity(sessionEntity);
//        System.out.println("ok");
//        int i=5;
//        for(;i<10;i++){
//            SessionEntity sessionEntity = new SessionEntity();
//            sessionEntity.setSession("test"+i);
//            sessionEntity.setExpires(new Date());
//            sessionDao.insertSession(sessionEntity);
//        }
//        SessionEntity sessionEntity = sessionDao.findById("5a9502060fcb7a2b920c4317");
//        System.out.println(sessionEntity.get_id());

//        List<SessionEntity> ls = sessionDao.findAll();
//        System.out.println("ok");
    }


    @Test
    public void userSearchTest() {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setUsername("tangrubei5");
//        mongoRepository.saveEntity(usersEntity);
//        UsersEntity usersEntity = usersDao.getUserById("591dd85c6dfe2725e663e735");
//        System.out.println(usersEntity.getNickname());


//        UsersEntity usersEntity2 = usersDao.getUserByName("tangrubei");
//        System.out.println(usersEntity2.getNickname());

    }

    @Test
    public void insertUserEntity() {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setEmail("test@nonobank.com");
//        usersEntity.setUsername("testbyidea");
//        usersDao.saveUser(usersEntity);

//        UsersEntity usersEntity2 = usersDao.getUserByName("testbyidea");
//        usersEntity2.setEmail("test@@nonobank.com");
//        usersDao.saveUser(usersEntity2);
//        UsersEntity usersEntity3 = usersDao.getUserByName("testbyidea");
//
//        System.out.println(usersEntity3.getUsername());


    }


    @Test
    public void userEntityTest2() {

        int permissionCode = 0;

        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateUsersNameQuery.apply("tangrubei"),UsersEntity.class);
        System.out.println("ok");

//        Query permissionQuer = new Query().addCriteria(Criteria.where(""))
//        PermissionsEntity permissionsEntity = (PermissionsEntity) mongoRepository.getEntityById("591d691ff5f93b26abedad6a", PermissionsEntity.class);

//        Query rolePermissionQuery = new Query().addCriteria(Criteria.where("role").is(new ObjectId("591dd7ba6dfe2725e663e734")).and("rights").ne(0));
//
//        List<RolepermissionsEntity> rolepermissionsEntity = (List<RolepermissionsEntity>) mongoRepository.getEntitysByWhere(rolePermissionQuery,RolepermissionsEntity.class);
//        System.out.println("ok");


//        Query roleQuery = new Query().addCriteria(Criteria.where("rolename").is("guest"));
//        RolesEntity rolesEntity = (RolesEntity) mongoRepository.getOneEntityByWhere(roleQuery, RolesEntity.class);
//        UserrolesEntity userrolesEntity1 = new UserrolesEntity();
//        userrolesEntity1.setUser(new ObjectId("591dd85c6dfe2725e663e735"));
//        userrolesEntity1.setRole(new ObjectId(rolesEntity.get_id()));
//
//        mongoRepository.saveEntity(userrolesEntity1);


//        Query userRoleQuery = new Query().addCriteria(Criteria.where("user").is("591dd85c6dfe2725e663e735"));


//        Query userRoleQuery = new Query().addCriteria(Criteria.where("user").is(new ObjectId("591dd85c6dfe2725e663e735")));
//
//
//
//        UserrolesEntity userrolesEntity = (UserrolesEntity) mongoRepository.getOneEntityByWhere(userRoleQuery, UserrolesEntity.class);
//        Query rolepermissionQuery = new Query().addCriteria(Criteria.where("role").is(new ObjectId(userrolesEntity.getRole())));
//        List<RolepermissionsEntity> rolepermissionsEntitys = (List<RolepermissionsEntity>) mongoRepository.getEntitysByWhere(rolepermissionQuery, RolepermissionsEntity.class);
//        List<String> permissionIds = rolepermissionsEntitys.stream().map(rolePerm -> rolePerm.getPermission()).collect(Collectors.toList());
//        Query permissionsQeury = new Query().addCriteria(Criteria.where("_id").in(permissionIds));
//        List<PermissionsEntity> permissionsEntities = (List<PermissionsEntity>) mongoRepository.getEntitysByWhere(permissionsQeury, PermissionsEntity.class);


//        for (PermissionsEntity p : permissionsEntities) {
//            String p_code = permission_map.get(p.getPermission());
//            if (p_code != null && p_code.length() > 0) {
//                permissionCode = permissionCode | Integer.valueOf(p_code, 2);
//            }
//        }
//        return permissionCode;

//        UsersEntity usersEntity = (UsersEntity) mongoRepository.getEntityById("591dd85c6dfe2725e663e735",UsersEntity.class);
//        Query userRoleQuery = new Query().addCriteria(Criteria.where("user").is(new ObjectId("591dd85c6dfe2725e663e735")));
//
//
//        UserrolesEntity userrolesEntity = (UserrolesEntity) mongoRepository.getOneEntityByWhere(userRoleQuery, UserrolesEntity.class);
//        Query rolepermissionQuery = new Query().addCriteria(Criteria.where("role").is(new ObjectId(userrolesEntity.getRole())));
//        List<RolepermissionsEntity> rolepermissionsEntitys = (List<RolepermissionsEntity>) mongoRepository.getEntitysByWhere(rolepermissionQuery, RolepermissionsEntity.class);
//        List<String> permissionIds = rolepermissionsEntitys.stream().map(rolePerm -> rolePerm.getPermission()).collect(Collectors.toList());
//        Query permissionsQeury = new Query().addCriteria(Criteria.where("_id").in(permissionIds));
//        List<PermissionsEntity> permissionsEntities = (List<PermissionsEntity>) mongoRepository.getEntitysByWhere(permissionsQeury, PermissionsEntity.class);


//        for(PermissionsEntity p:permissionsEntities){
//            String p_code = permission_map.get(p.getPermission());
//            if (p_code!=null&&p_code.length()>0){
//                permissionCode =permissionCode|Integer.valueOf(p_code,2);
//            }
//        }


//        UserrolesEntity userrolesEntity = (UserrolesEntity) mongoRepository.getOneEntityByWhere(userRoleQuery,UserrolesEntity.class);
//
//        RolesEntity rolesEntity = (RolesEntity) mongoRepository.getEntityById("591dd7ba6dfe2725e663e734",RolesEntity.class);
//        System.out.println(usersEntity.getNickname());


    }


}
