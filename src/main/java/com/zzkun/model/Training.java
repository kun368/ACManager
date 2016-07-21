package com.zzkun.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 集训
 * Created by Administrator on 2016/7/20.
 */
@Entity
@Table(name = "training")
public class Training implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String name;

    @Column(length = 1024)
    private String remark;

    private LocalDate startDate;

    private LocalDate endDate;

    public Training() {
    }

    public Training(String name, String remark, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.remark = remark;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Training(String name, String remark, String startDate, String endDate) {
        this.name = name;
        this.remark = remark;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
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

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
