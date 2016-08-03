package com.zzkun.model;

import com.zzkun.util.stder.DataStder;
import com.zzkun.util.stder.RawData;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛整体情况（排行榜）
 * Created by Administrator on 2016/6/27.
 */
@Entity
@Table(name = "contest")
public class Contest implements Serializable {

    public static final String TYPE_PERSONAL = "PERSONAL";
    public static final String TYPE_TEAM = "TEAM";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 10240)
    private String remark;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime addTime;

    private Integer addUid;


    private Integer stageId;

    private Integer pbCnt;

    @Lob
    private ArrayList<TeamRanking> ranks = new ArrayList<>();  //解析后的榜单

    @Lob
    private Pair<String, String> rawData;


    public Contest() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public Integer getPbCnt() {
        return pbCnt;
    }

    public void setPbCnt(Integer pbCnt) {
        this.pbCnt = pbCnt;
    }

    public ArrayList<TeamRanking> getRanks() {
        return ranks;
    }

    public void setRanks(ArrayList<TeamRanking> ranks) {
        this.ranks = ranks;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Pair<String, String> getRawData() {
        return rawData;
    }

    public void setRawData(Pair<String, String> rawData) {
        this.rawData = rawData;
    }


    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", type='" + type + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", addTime=" + addTime +
                ", addUid=" + addUid +
                ", stageId=" + stageId +
                ", pbCnt=" + pbCnt +
                ", rawData=" + rawData +
                '}';
    }
}
