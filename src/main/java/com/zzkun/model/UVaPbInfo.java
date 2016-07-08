package com.zzkun.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static java.lang.Math.*;

/**
 * uva每个题目的信息
 * Created by kun on 2016/7/6.
 */
@Entity
@Table(name = "uva_pb_info")
public class UVaPbInfo implements Serializable {

    @Id
    private int pid;

    private int num;
    private String title;
    private int dacu;
    private int mrun;
    private int mmem;
    private int nover;
    private int sube;
    private int noj;
    private int inq;
    private int ce;
    private int rf;
    private int re;
    private int ole;
    private int tle;
    private int mle;
    private int wa;
    private int pe;
    private int ac;
    private int rtl;
    private int status;
    private int rej;

    public UVaPbInfo() {
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDacu() {
        return dacu;
    }

    public void setDacu(int dacu) {
        this.dacu = dacu;
    }

    public int getMrun() {
        return mrun;
    }

    public void setMrun(int mrun) {
        this.mrun = mrun;
    }

    public int getMmem() {
        return mmem;
    }

    public void setMmem(int mmem) {
        this.mmem = mmem;
    }

    public int getNover() {
        return nover;
    }

    public void setNover(int nover) {
        this.nover = nover;
    }

    public int getSube() {
        return sube;
    }

    public void setSube(int sube) {
        this.sube = sube;
    }

    public int getNoj() {
        return noj;
    }

    public void setNoj(int noj) {
        this.noj = noj;
    }

    public int getInq() {
        return inq;
    }

    public void setInq(int inq) {
        this.inq = inq;
    }

    public int getCe() {
        return ce;
    }

    public void setCe(int ce) {
        this.ce = ce;
    }

    public int getRf() {
        return rf;
    }

    public void setRf(int rf) {
        this.rf = rf;
    }

    public int getRe() {
        return re;
    }

    public void setRe(int re) {
        this.re = re;
    }

    public int getOle() {
        return ole;
    }

    public void setOle(int ole) {
        this.ole = ole;
    }

    public int getTle() {
        return tle;
    }

    public void setTle(int tle) {
        this.tle = tle;
    }

    public int getMle() {
        return mle;
    }

    public void setMle(int mle) {
        this.mle = mle;
    }

    public int getWa() {
        return wa;
    }

    public void setWa(int wa) {
        this.wa = wa;
    }

    public int getPe() {
        return pe;
    }

    public void setPe(int pe) {
        this.pe = pe;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getRtl() {
        return rtl;
    }

    public void setRtl(int rtl) {
        this.rtl = rtl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRej() {
        return rej;
    }

    public void setRej(int rej) {
        this.rej = rej;
    }

    @Override
    public String toString() {
        return "UVaPbInfo{" +
                "pid=" + pid +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", dacu=" + dacu +
                ", mrun=" + mrun +
                ", mmem=" + mmem +
                ", nover=" + nover +
                ", sube=" + sube +
                ", noj=" + noj +
                ", inq=" + inq +
                ", ce=" + ce +
                ", rf=" + rf +
                ", re=" + re +
                ", ole=" + ole +
                ", tle=" + tle +
                ", mle=" + mle +
                ", wa=" + wa +
                ", pe=" + pe +
                ", ac=" + ac +
                ", rtl=" + rtl +
                ", status=" + status +
                ", rej=" + rej +
                '}';
    }

    public static UVaPbInfo parseJSONArray(JSONArray curPb) {
        UVaPbInfo info = new UVaPbInfo();
        info.setPid(curPb.getInteger(0));
        info.setNum(curPb.getInteger(1));
        info.setTitle(curPb.getString(2));
        info.setDacu(curPb.getInteger(3));
        info.setMrun(curPb.getInteger(4));
        info.setMmem(curPb.getInteger(5));
        info.setNover(curPb.getInteger(6));
        info.setSube(curPb.getInteger(7));
        info.setNoj(curPb.getInteger(8));
        info.setInq(curPb.getInteger(9));
        info.setCe(curPb.getInteger(10));
        info.setRf(curPb.getInteger(11));
        info.setRe(curPb.getInteger(12));
        info.setOle(curPb.getInteger(13));
        info.setTle(curPb.getInteger(14));
        info.setMle(curPb.getInteger(15));
        info.setWa(curPb.getInteger(16));
        info.setPe(curPb.getInteger(17));
        info.setAc(curPb.getInteger(18));
        info.setRtl(curPb.getInteger(19));
        info.setStatus(curPb.getInteger(20));
        info.setRej(curPb.getInteger(21));
        return info;
    }

    public int allSubmitTimes() {
        return ce + rf + re + ole + tle + mle + wa + pe + ac;
    }

    public double calcScore() {
        double log2_x = log(max(dacu, 400)) / log(2);
        double ans1 = (1 + 100 * pow(E, 4 - log2_x));
        double ans2 = (1 + (tle+mle) / (re+ole+tle+mle+wa+pe+ac+1) / 2.0);
        return ans1 * ans2;
    }
}
