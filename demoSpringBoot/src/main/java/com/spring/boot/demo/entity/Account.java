package com.spring.boot.demo.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Account {
    @Id
    private int id;
    private String account;
    @Column(name = "phone")
    private String phone;
    @Column(name = "nickname")
    private String nickname;
    private String password;
    private String salt;
    //    @Column(name = "user_type")
    private int userType;
    //    @Column(name = "create_user")
    private String createUser;
    //    @Column(name = "create_time")
    private Timestamp createTime;
    private int state;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void  copy(Account account)
    {
        this.account=account.account;
        this.createTime=account.createTime;
        this.createUser=account.createUser;
        this.id=account.id;
        this.nickname=account.nickname;
        this.password=account.password;
        this.phone=account.phone;
        this.salt=account.salt;
        this.state=account.state;
        this.userType=account.userType;
    }


}