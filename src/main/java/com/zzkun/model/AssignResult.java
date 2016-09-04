package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * 随机分队结果
 * Created by kun on 2016/7/14.
 */
@Entity
@Table(name = "assign_result")
public class AssignResult implements Serializable {

    public enum Type {
        RANDOM,
        NoRepeat
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date = LocalDate.now();

    @Lob
    private ArrayList<List<Integer>> teamList = new ArrayList<>();

    @Lob
    private ArrayList<String> accountList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainingId")
    private Training training;

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

    public ArrayList<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<String> accountList) {
        this.accountList = accountList;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setAccount(Integer pos, String account) {
        if(pos >= teamList.size()) return;
        if(accountList == null)
            accountList = new ArrayList<>();
        while(accountList.size() < teamList.size())
            accountList.add("");
        accountList.set(pos, account);
    }

    @Override
    public String toString() {
        return "AssignResult{" +
                "id=" + id +
                ", date=" + date +
                ", teamList=" + teamList +
                ", accountList=" + accountList +
                ", type=" + type +
                ", training=" + training +
                '}';
    }
}
