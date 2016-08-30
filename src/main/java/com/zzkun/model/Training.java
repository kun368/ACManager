package com.zzkun.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 集训
 * Created by Administrator on 2016/7/20.
 */
@Entity
@Table(name = "training")
public class Training implements Serializable {

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

    private Double standard = 70.0;
    private Double expand = 10.0; //数据标准化时的两个参数

    private Integer waCapcity = -99;  //题目错误处理

    private Double mergeLimit = 1.0099; //Rank聚类时的合并阈值

    private Double ratingBase; //计算rating的基准值g
    private Double ratingMultiple; //计算rating的倍数

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stage> stageList;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(value = "date")
    private List<AssignResult> assignResultList;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UJoinT> uJoinTList;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FixedTeam> fixedTeamList;

    public Training() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Double getStandard() {
        return standard;
    }

    public void setStandard(Double standard) {
        this.standard = standard;
    }

    public Double getExpand() {
        return expand;
    }

    public void setExpand(Double expand) {
        this.expand = expand;
    }

    public Integer getWaCapcity() {
        return waCapcity;
    }

    public void setWaCapcity(Integer waCapcity) {
        this.waCapcity = waCapcity;
    }

    public Double getMergeLimit() {
        return mergeLimit;
    }

    public void setMergeLimit(Double mergeLimit) {
        this.mergeLimit = mergeLimit;
    }

    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }

    public List<AssignResult> getAssignResultList() {
        return assignResultList;
    }

    public void setAssignResultList(List<AssignResult> assignResultList) {
        this.assignResultList = assignResultList;
    }

    public List<UJoinT> getuJoinTList() {
        return uJoinTList;
    }

    public void setuJoinTList(List<UJoinT> uJoinTList) {
        this.uJoinTList = uJoinTList;
    }

    public Double getRatingBase() {
        return ratingBase;
    }

    public void setRatingBase(Double ratingBase) {
        this.ratingBase = ratingBase;
    }

    public Double getRatingMultiple() {
        return ratingMultiple;
    }

    public void setRatingMultiple(Double ratingMultiple) {
        this.ratingMultiple = ratingMultiple;
    }

    public List<FixedTeam> getFixedTeamList() {
        return fixedTeamList;
    }

    public void setFixedTeamList(List<FixedTeam> fixedTeamList) {
        this.fixedTeamList = fixedTeamList;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", addTime=" + addTime +
                ", addUid=" + addUid +
                ", standard=" + standard +
                ", expand=" + expand +
                ", waCapcity=" + waCapcity +
                ", mergeLimit=" + mergeLimit +
                ", ratingBase=" + ratingBase +
                ", ratingMultiple=" + ratingMultiple +
                '}';
    }

    ////

    public int contestCount() {
        int sum = 0;
        for (Stage stage : stageList)
            sum += stage.getContestList().size();
        return sum;
    }
}
