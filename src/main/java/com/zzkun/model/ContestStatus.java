package com.zzkun.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛整体情况（排行榜）
 * Created by Administrator on 2016/6/27.
 */
public class ContestStatus implements Serializable {

    public static final String TYPE_PERSONAL = "PERSONAL";
    public static final String TYPE_TEAM = "TEAM";

    private String name;
    private String type;
    private LocalDate time;
    private int pbCnt;
    private List<TeamRanking> ranks = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public int getPbCnt() {
        return pbCnt;
    }

    public void setPbCnt(int pbCnt) {
        this.pbCnt = pbCnt;
    }

    public List<TeamRanking> getRanks() {
        return ranks;
    }

    public void setRanks(List<TeamRanking> ranks) {
        this.ranks = ranks;
    }

    @Override
    public String toString() {
        return "ContestStatus{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", pbCnt=" + pbCnt +
                ", ranks=" + ranks +
                '}';
    }
}
