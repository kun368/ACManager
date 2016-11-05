package com.zzkun.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kun on 2016/10/15.
 */
@Entity
@Table(name = "extoj_pb_info")
public class ExtOjPbInfo implements Serializable, Comparable<ExtOjPbInfo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OJType ojName;

    private String pid;

    private String num;

    @Column(length = 10240)
    private String title;

    private int dacu; //不同AC人数
    private int mrun; //最佳runtime
    private int mmem; //最佳runmemory
    private int nover;
    private int sube; //Submission Error
    private int noj;  //不能被judge
    private int inq;  //In Queue
    private int ce;   //Compilation Error
    private int rf;   //限制函数
    private int re;   //Runtime Error
    private int ole;  //Output Limit Exceeded
    private int tle;  //Time Limit Exceeded
    private int mle;  //Memory Limit Exceeded
    private int wa;   //Wrong Answer
    private int pe;   //Presentation Error
    private int ac;   //Accepted
    private int rtl;  //时间限制
    private int status;
    private int rej;
    private int totSubmit; //总提交


    public int calcTotSubmits() {
        return ce + rf + re + ole + tle + mle + wa + pe + ac + sube + inq;
    }

    //--------------------------

    public ExtOjPbInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OJType getOjName() {
        return ojName;
    }

    public void setOjName(OJType ojName) {
        this.ojName = ojName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
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

    public int getTotSubmit() {
        return totSubmit;
    }

    public void setTotSubmit(int totSubmit) {
        this.totSubmit = totSubmit;
    }

    @Override
    public String toString() {
        return "ExtOjPbInfo{" +
                "id=" + id +
                ", ojName=" + ojName +
                ", pid='" + pid + '\'' +
                ", num='" + num + '\'' +
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
                ", totSubmit=" + totSubmit +
                '}';
    }

    @Override
    public int compareTo(@NotNull ExtOjPbInfo o) {
        return new CompareToBuilder()
                .append(getOjName(), o.getOjName())
                .append(getPid(), o.getPid())
                .toComparison();
    }
}
