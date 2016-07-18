package com.zzkun.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * 随机分队结果
 * Created by kun on 2016/7/14.
 */
@Entity
@Table(name = "team_assign_result")
public class AssignResult implements Serializable {

    public enum Type {
        RANDOM
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private LocalDate date = LocalDate.now();

    @Lob
    private ArrayList<String> teamList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Type type;

    public AssignResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<String> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<String> teamList) {
        this.teamList = teamList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 往分对结果里添加一个队伍
     * @param users 本队id列表List
     */
    public void addTeam(List<Integer> users) {
        Collections.sort(users, (x, y) -> (Integer.compare(x, y)));
        StringJoiner joiner = new StringJoiner("_");
        for (Integer user : users)
            joiner.add(user.toString());
        teamList.add(joiner.toString());
    }

    public List<List<Integer>> getAllTeam() {
        List<List<Integer>> res = new ArrayList<>();
        for (String s : teamList) {
            String[] split = s.split("_");
            List<Integer> team = new ArrayList<>();
            for (String s1 : split)
                team.add(Integer.parseInt(s1));
            res.add(team);
        }
        return res;
    }

    @Override
    public String toString() {
        return "AssignResult{" +
                "id=" + id +
                ", date=" + date +
                ", teamList=" + teamList +
                ", type=" + type +
                '}';
    }
}
