package com.zzkun.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/7/30.
 */
@Entity
@Table(name = "fixed_team")
public class FixedTeam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name1;

    private String name2;

    private String vjname;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "trainingId")
    private Training training;

    private Integer user1Id;
    private Integer user2Id;
    private Integer user3Id;

    ////

    public FixedTeam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getVjname() {
        return vjname;
    }

    public void setVjname(String vjname) {
        this.vjname = vjname;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Integer getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Integer user1Id) {
        this.user1Id = user1Id;
    }

    public Integer getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Integer user2Id) {
        this.user2Id = user2Id;
    }

    public Integer getUser3Id() {
        return user3Id;
    }

    public void setUser3Id(Integer user3Id) {
        this.user3Id = user3Id;
    }

    @Override
    public String toString() {
        return "FixedTeam{" +
                "id=" + id +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", vjname='" + vjname + '\'' +
                ", training=" + training +
                ", user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                ", user3Id=" + user3Id +
                '}';
    }

    ////

    public boolean userEquals(int u1, int u2, int u3) {
        int[] rhs = new int[]{u1, u2, u3};
        int[] lhs = new int[]{user1Id, user2Id, user3Id};
        Arrays.sort(rhs);
        Arrays.sort(lhs);
        return new EqualsBuilder()
                .append(lhs[0], rhs[0])
                .append(lhs[1], rhs[1])
                .append(lhs[2], rhs[2])
                .isEquals();
    }
}
