package com.nonobank.platformuser.entity.mysqlEntity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tangrubei on 2018/4/10.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false, columnDefinition = "varchar(300) COMMENT '用户名称'")
    String username;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '用户别名'")
    String nickname;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '邮箱'")
    String email;

    @Column(nullable = false, columnDefinition = "varchar(300) COMMENT '密码'")
    String password;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '组织'")
    String organization;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '创建人'")
    String createdBy;

    @Column(nullable = true, columnDefinition = "varchar(300) COMMENT '修改人'")
    String updatedBy;

    @Column(nullable = false, columnDefinition = "smallint(1) COMMENT '0:正常，1:已更新，2:已删除'")
    Short optstatus;


    @Transient
    List<Role> roles;

    @Column(columnDefinition = "datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdTime;



    @Column(columnDefinition = " datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime updatedTime;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
