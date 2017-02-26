package com.zzkun.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
        Admin;           //管理员

        public String toShortStr() {
            switch (this) {
                case New:
                    return "用户";
                case Verifying:
                    return "申请";
                case Reject:
                    return "拒绝";
                case Acmer:
                    return "队员";
                case Expeled:
                    return "除名";
                case Retired:
                    return "退役";
                case Quit:
                    return "退出";
                case Coach:
                    return "教练";
                case Admin:
                    return "管理员";
                default:
                    return "未知";
            }
        }
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

    @Column(length = 1024)
    private String blogUrl;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private LocalDate lastACDate = LocalDate.of(2017, 2, 19);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UJoinT> uJoinTList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserACPb> acPbList;


    @OneToMany(mappedBy = "addUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CptTree> addCptTreeList;

    public User() {
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

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
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

    public LocalDate getLastACDate() {
        return lastACDate;
    }

    public void setLastACDate(LocalDate lastACDate) {
        this.lastACDate = lastACDate;
    }

    public List<CptTree> getAddCptTreeList() {
        return addCptTreeList;
    }

    public void setAddCptTreeList(List<CptTree> addCptTreeList) {
        this.addCptTreeList = addCptTreeList;
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
                ", hduName='" + hduName + '\'' +
                ", pojName='" + pojName + '\'' +
                ", major='" + major + '\'' +
                ", blogUrl='" + blogUrl + '\'' +
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
