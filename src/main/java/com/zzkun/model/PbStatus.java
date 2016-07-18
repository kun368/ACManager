package com.zzkun.model;

import java.io.Serializable;

/**
 * 题目的一个人解题情况
 * Created by Administrator on 2016/6/27.
 */
public class PbStatus implements Serializable {

    private static final long serialVersionUID = 8822201624970861661L;

    private boolean solved;
    private int time;
    private int waCount;

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

    public int calcPenalty() {
        if(!isSolved()) return 0;
        return time + 1200 * waCount + 600 * (waCount) * (waCount - 1) / 2;
    }


    @Override
    public String toString() {
        return "PbStatus{" +
                "solved=" + solved +
                ", time=" + time +
                ", waCount=" + waCount +
                '}';
    }
}
