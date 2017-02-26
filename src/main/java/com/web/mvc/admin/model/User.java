package com.web.mvc.admin.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by xieyuhui on 2017/2/26.
 */
public class User implements Serializable {
    private int id;
    private String name;
    private String passWord;
    private Date addTime;
    private Date updateTime;
    private Date loginTime;
    private String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", loginTime=" + loginTime +
                ", ip='" + ip + '\'' +
                '}';
    }
}
