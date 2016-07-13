package com.zzkun.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 队伍的原始解题情况
 * Created by Administrator on 2016/6/27.
 */
public class TeamRanking implements Serializable {
    private String account;
    private String teamName;
    private int solvedCount;
    private List<String> member = new ArrayList<>();
    private List<PbStatus> pbStatus = new ArrayList<>();

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(int solvedCount) {
        this.solvedCount = solvedCount;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public List<PbStatus> getPbStatus() {
        return pbStatus;
    }

    public void setPbStatus(List<PbStatus> pbStatus) {
        this.pbStatus = pbStatus;
    }

    @Override
    public String toString() {
        return "TeamRanking{" +
                "account='" + account + '\'' +
                ", teamName='" + teamName + '\'' +
                ", solvedCount=" + solvedCount +
                ", member=" + member +
                ", pbStatus=" + pbStatus +
                '}';
    }
}
