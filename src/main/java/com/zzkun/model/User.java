package com.zzkun.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户类
 * Created by kun on 2016/7/6.
 */
@Entity
@Table(name = "user")
public class User implements Serializable, Comparable<User> {

    /**
     * 用户类型
     */
    public enum Type {
        Retired,        //退役
        Expeled,        //开除
        Quit,           //退出
        Acmer,          //正式集训队员
        Reject,         //拒绝进队
        Verifying,      //已申请进队，待进队
        New,            //新人
        Coach,          //教练
        Admin           //管理员
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String realName;

    @Column(unique = true)
    private Integer uvaId;

    @Column(unique = true)
    private String cfname;

    @Column(unique = true)
    private String vjname;

    @Column(unique = true)
    private String bcname;

    @Column(unique = true)
    private String hduName;

    @Column(unique = true)
    private String pojName;

    private String major;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UJoinT> uJoinTList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserACPb> acPbList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Type type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(String username, String password, String realName, Integer uvaId, String cfname, String major, Type type) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.uvaId = uvaId;
        this.cfname = cfname;
        this.major = major;
        this.type = type;
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

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getVjname() {
        return vjname;
    }

    public void setVjname(String vjname) {
        this.vjname = vjname;
    }

    public String getBcname() {
        return bcname;
    }

    public void setBcname(String bcname) {
        this.bcname = bcname;
    }

    public String getHduName() {
        return hduName;
    }

    public void setHduName(String hduName) {
        this.hduName = hduName;
    }

    public String getPojName() {
        return pojName;
    }

    public void setPojName(String pojName) {
        this.pojName = pojName;
    }

    public List<UJoinT> getuJoinTList() {
        return uJoinTList;
    }

    public void setuJoinTList(List<UJoinT> uJoinTList) {
        this.uJoinTList = uJoinTList;
    }

    public List<UserACPb> getAcPbList() {
        return acPbList;
    }

    public void setAcPbList(List<UserACPb> acPbList) {
        this.acPbList = acPbList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", uvaId=" + uvaId +
                ", cfname='" + cfname + '\'' +
                ", vjname='" + vjname + '\'' +
                ", bcname='" + bcname + '\'' +
                ", major='" + major + '\'' +
                ", type=" + type +
                '}';
    }

    ///////

    public boolean isAdmin() {
        return getType().equals(Type.Admin) || getType().equals(Type.Coach);
    }

    public boolean isACMer() {
        return getType().equals(Type.Acmer);
    }

    @Override
    public int compareTo(User o) {
        return new CompareToBuilder()
                .append(getId(), o.getId())
                .toComparison();
    }
}
