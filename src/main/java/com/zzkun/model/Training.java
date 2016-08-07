package com.zzkun.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Double standard, expand; //数据标准化时的两个参数

    private Integer waCapcity;  //题目错误处理

    private Double mergeLimit; //Rank聚类时的合并阈值


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
                '}';
    }
}
