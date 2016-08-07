package com.zzkun.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.zip.Inflater;

/**
 * 队伍的原始解题情况
 * Created by Administrator on 2016/6/27.
 */
public class TeamRanking implements Serializable, Comparable<TeamRanking> {

    private static final long serialVersionUID = 3741267226756056112L;

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

    public Integer calcSumPenalty() {
        int sum = 0;
        for (PbStatus pbStatu : pbStatus)
            sum += pbStatu.calcPenalty();
        return sum;
    }

    public String memberToString() {
        StringJoiner joiner = new StringJoiner(" ");
        for (String s : member)
            joiner.add(s);
        return joiner.toString();
    }

    @Override
    public int compareTo(TeamRanking o) {
        if(solvedCount != o.solvedCount)
            return Integer.compare(o.solvedCount, solvedCount);
        return calcSumPenalty().compareTo(o.calcSumPenalty());
    }

    public boolean[] judgeTValid(int maxWA) {
        boolean[] ok = new boolean[pbStatus.size()];
        //TODO
        return null;
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
