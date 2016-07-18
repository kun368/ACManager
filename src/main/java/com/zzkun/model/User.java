package com.zzkun.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户类
 * Created by kun on 2016/7/6.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    /**
     * 用户类型
     */
    public enum Type {
        Retired,        //退役
        Expeled,        //开除
        Normal,         //正常
        Verifying,      //待进队
        Admin           //管理员
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true)
    private String realName;

    @Column(unique = true)
    private Integer uvaId;

    @Column(unique = true)
    private String cfname;

    public User() {
    }

    public User(String username, String password, String realName, Integer uvaId, String cfname) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.uvaId = uvaId;
        this.cfname = cfname;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getUvaId() {
        return uvaId;
    }

    public void setUvaId(Integer uvaId) {
        this.uvaId = uvaId;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getCfname() {
        return cfname;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", uvaId=" + uvaId +
                ", cfname='" + cfname + '\'' +
                '}';
    }
}
