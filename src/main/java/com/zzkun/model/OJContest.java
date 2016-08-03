package com.zzkun.model;

/**
 * Created by Administrator on 2016/8/3.
 */
public class OJContest {

    private Integer id;
    private String oj;
    private String link;
    private String name;
    private String start_time;
    private String week;
    private String access;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOj() {
        return oj;
    }

    public void setOj(String oj) {
        this.oj = oj;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "OJContest{" +
                "id=" + id +
                ", oj='" + oj + '\'' +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", start_time='" + start_time + '\'' +
                ", week='" + week + '\'' +
                ", access='" + access + '\'' +
                '}';
    }
}
