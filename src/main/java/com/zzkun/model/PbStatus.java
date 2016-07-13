package com.zzkun.model;

import java.io.Serializable;

/**
 * 题目的一个人解题情况
 * Created by Administrator on 2016/6/27.
 */
public class PbStatus implements Serializable {
    private boolean solved;
    private int time;
    private int waCount;
    public double score;

    public PbStatus(boolean solved, int time, int waCount) {
        this.solved = solved;
        this.time = time;
        this.waCount = waCount;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWaCount() {
        return waCount;
    }

    public void setWaCount(int waCount) {
        this.waCount = waCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PbStatus{" +
                "solved=" + solved +
                ", time=" + time +
                ", waCount=" + waCount +
                ", score=" + score +
                '}';
    }
}
