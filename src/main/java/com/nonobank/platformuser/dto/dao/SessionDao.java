package com.nonobank.platformuser.dto.dao;

import com.nonobank.platformuser.entity.mongoEntity.SessionEntity;
import java.util.List;

/**
 * Created by tangrubei on 2018/2/24.
 */


public interface SessionDao {


    void insertSession(SessionEntity sessionEntity);

    List<SessionEntity> findAll();


    SessionEntity findById(String id);




}
