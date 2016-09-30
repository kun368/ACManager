package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kun on 2016/9/29.
 */
@Entity
@Table(name = "user_ac_pb")
public class UserACPb implements Serializable {

    public enum OJType {
        POJ,
        ZOJ,
        UVALive,
        SGU,
        URAL,
        HUST,
        SPOJ,
        HDU,
        HYSBZ,
        UVA,
        CodeForces,
        Aizu,
        LightOJ,
        UESTC,
        NBUT,
        FZU,
        CSU,
        SCU,
        ACdream,
        CodeChef,
        Gym,
        OpenJudge,
        Null
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private OJType ojName;

    private String ojPbId;

    private Boolean fromVJ;

    ///-------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OJType getOjName() {
        return ojName;
    }

    public void setOjName(OJType ojName) {
        this.ojName = ojName;
    }

    public String getOjPbId() {
        return ojPbId;
    }

    public void setOjPbId(String ojPbId) {
        this.ojPbId = ojPbId;
    }

    public Boolean getFromVJ() {
        return fromVJ;
    }

    public void setFromVJ(Boolean fromVJ) {
        this.fromVJ = fromVJ;
    }

    public UserACPb(User user, OJType ojName, String ojPbId, Boolean fromVJ) {
        this.user = user;
        this.ojName = ojName;
        this.ojPbId = ojPbId;
        this.fromVJ = fromVJ;
    }

    @Override
    public String toString() {
        return "UserACPb{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", ojName=" + ojName +
                ", ojPbId='" + ojPbId + '\'' +
                ", fromVJ=" + fromVJ +
                '}';
    }
}
