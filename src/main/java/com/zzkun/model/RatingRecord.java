package com.zzkun.model;

import jskills.Rating;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 计算Rating后保存的纪录实体类
 * Created by kun on 2016/8/13.
 */
@Entity
@Table(name = "rating_record")
public class RatingRecord {

    public enum Scope {
        Global, Training, Stage, Signal
    }

    public enum Type {
        Personal, Team
    }

    public RatingRecord() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private Scope scope;

    private Integer scopeId;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private String identifier; //用户、队唯一标示

    private Integer contestId;

    private Double mean;

    private Double standardDeviation;

    private LocalDateTime generateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public LocalDateTime getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(LocalDateTime generateTime) {
        this.generateTime = generateTime;
    }

    @Override
    public String toString() {
        return "RatingRecord{" +
                "id=" + id +
                ", scope=" + scope +
                ", type=" + type +
                ", identifier='" + identifier + '\'' +
                ", contestId=" + contestId +
                ", mean=" + mean +
                ", standardDeviation=" + standardDeviation +
                ", generateTime=" + generateTime +
                '}';
    }
}
