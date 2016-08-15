package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 竞赛阶段实体类
 * Created by kun on 2016/7/13.
 */
@Entity
@Table(name = "contest_stage")
public class Stage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 10240)
    private String remark;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime addTime;

    private Integer addUid;

    private boolean countToRating;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainingId")
    private Training training;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contest> contestList;

    public Stage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUid() {
        return addUid;
    }

    public void setAddUid(Integer addUid) {
        this.addUid = addUid;
    }

    public boolean isCountToRating() {
        return countToRating;
    }

    public void setCountToRating(boolean countToRating) {
        this.countToRating = countToRating;
    }

    public List<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", addTime=" + addTime +
                ", addUid=" + addUid +
                ", countToRating=" + countToRating +
                ", training=" + training +
                '}';
    }
}
