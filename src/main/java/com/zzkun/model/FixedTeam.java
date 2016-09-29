package com.zzkun.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

//    @Column(unique = true)
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

    public double calcTeamScoreStr(String team, String a, String b, String c) {
        List<Double> list = new ArrayList<>();
        if(!StringUtils.hasText(team))
            team = "0";
        if(StringUtils.hasText(a))
            list.add(Double.parseDouble(a));
        if(StringUtils.hasText(b))
            list.add(Double.parseDouble(b));
        if(StringUtils.hasText(c))
            list.add(Double.parseDouble(c));
        return calcTeamScore(Double.parseDouble(team), list);
    }

    public double calcTeamScore(double team, List<Double> val) {
        Collections.sort(val, (a, b) -> b.compareTo(a));
        double teamRate =
                Math.max(0, 1.0 - (training.getTeamScoreRate1() + training.getTeamScoreRate2() + training.getTeamScoreRate3()));
        double sum = teamRate * team;
        if(val.size() >= 1) sum += training.getTeamScoreRate1() * val.get(0);
        if(val.size() >= 2) sum += training.getTeamScoreRate2() * val.get(1);
        if(val.size() >= 3) sum += training.getTeamScoreRate3() * val.get(2);
        return sum;
    }
}
