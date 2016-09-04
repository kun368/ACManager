package com.zzkun.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/31.
 */
@Entity
@Table(name = "cf_user_info")
public class CFUserInfo implements Serializable {

    @Id
    private String cfname;

    private Integer rating;

    private Integer maxRating;

    private String rank;

    private String maxRank;

    public CFUserInfo() {
    }

    public CFUserInfo(String cfname, Integer rating, Integer maxRating, String rank, String maxRank) {
        this.cfname = cfname;
        this.rating = rating;
        this.maxRating = maxRating;
        this.rank = rank;
        this.maxRank = maxRank;
    }

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }

    @Override
    public String toString() {
        return "CFUserInfo{" +
                "cfname='" + cfname + '\'' +
                ", rating=" + rating +
                ", maxRating=" + maxRating +
                ", rank='" + rank + '\'' +
                ", maxRank='" + maxRank + '\'' +
                '}';
    }
}
