package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户类
 * Created by kun on 2016/7/6.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "uva_id")
    private int uvaId;

    @Column(name = "cf_name")
    private String cfname;

    public User() {
    }

    public User(String username, String password, int uvaId, String cfname) {
        this.username = username;
        this.password = password;
        this.uvaId = uvaId;
        this.cfname = cfname;
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

    public int getUvaId() {
        return uvaId;
    }

    public void setUvaId(int uvaId) {
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
                ", uvaId=" + uvaId +
                ", cfname='" + cfname + '\'' +
                '}';
    }
}
