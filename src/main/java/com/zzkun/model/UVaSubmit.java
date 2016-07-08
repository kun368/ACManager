package com.zzkun.model;

import com.alibaba.fastjson.JSONArray;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by kun on 2016/7/7.
 */
@Entity
@Table(name = "uva_submit")
public class UVaSubmit implements Serializable {

    @Id
    private Long submitId;

    private Integer uvaId;
    private Integer pbId;
    private Integer verdictId;
    private Integer runtime;
    private Long submitTime;
    private Integer lanId;
    private Integer rank;

    public UVaSubmit() {
    }

    public Long getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Long submitId) {
        this.submitId = submitId;
    }

    public Integer getUvaId() {
        return uvaId;
    }

    public void setUvaId(Integer uvaId) {
        this.uvaId = uvaId;
    }

    public Integer getPbId() {
        return pbId;
    }

    public void setPbId(Integer pbId) {
        this.pbId = pbId;
    }

    public Integer getVerdictId() {
        return verdictId;
    }

    public void setVerdictId(Integer verdictId) {
        this.verdictId = verdictId;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getLanId() {
        return lanId;
    }

    public void setLanId(Integer lanId) {
        this.lanId = lanId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UVaSubmit{" +
                "submitId=" + submitId +
                ", pbId=" + pbId +
                ", verdictId=" + verdictId +
                ", runtime=" + runtime +
                ", submitTime=" + submitTime +
                ", lanId=" + lanId +
                ", rank=" + rank +
                '}';
    }

    public static UVaSubmit parseJSONArray(int uvaId, JSONArray json) {
        UVaSubmit submit = new UVaSubmit();
        submit.setUvaId(uvaId);
        submit.setSubmitId(json.getLong(0));
        submit.setPbId(json.getInteger(1));
        submit.setVerdictId(json.getInteger(2));
        submit.setRuntime(json.getInteger(3));
        submit.setSubmitTime(json.getLong(4));
        submit.setLanId(json.getInteger(5));
        submit.setRank(json.getInteger(5));
        return submit;
    }
}
