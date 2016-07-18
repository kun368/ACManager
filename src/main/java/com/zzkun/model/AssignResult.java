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
@Table(name = "assign_result")
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
    private ArrayList<List<Integer>> teamList = new ArrayList<>();

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

    public ArrayList<List<Integer>> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<List<Integer>> teamList) {
        this.teamList = teamList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
