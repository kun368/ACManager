package com.zzkun.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kun on 2016/9/29.
 */
@Entity
@Table(name = "user_ac_pb")
public class UserACPb implements Serializable, Comparable<UserACPb> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private OJType ojName;

    private String ojPbId;   //存的是ExtOjPbInfo的num

    ///-------


    public UserACPb() {
    }

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

    public UserACPb(User user, OJType ojName, String ojPbId) {
        this.user = user;
        this.ojName = ojName;
        this.ojPbId = ojPbId;
    }

    @Override
    public String toString() {
        return "UserACPb{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", ojName=" + ojName +
                ", ojPbId='" + ojPbId + '\'' +
                '}';
    }

//---------------

    @Override
    public int compareTo(@NotNull UserACPb o) {
        return new CompareToBuilder()
                .append(user, o.user)
                .append(ojName, o.ojName)
                .append(ojPbId, o.ojPbId)
                .toComparison();
    }
}
