package com.nonobank.platformuser.entity.mysqlEntity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tangrubei on 2018/4/10.
 */
@Entity
public class UserRoles {

    @Id
    @GeneratedValue
    Integer id;


    Integer userId;

    Integer roleId;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '创建人'")
    String createdBy;

    @Column(columnDefinition = "datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdTime;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '修改人'")
    String updatedBy;

    @Column(columnDefinition = " datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime updatedTime;

    @Column(nullable = false, columnDefinition = "smallint(1) COMMENT '0:正常，1:已更新，2:已删除'")
    Short optstatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Short getOptstatus() {
        return optstatus;
    }

    public void setOptstatus(Short optstatus) {
        this.optstatus = optstatus;
    }
}
