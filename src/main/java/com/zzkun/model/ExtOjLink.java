package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kun on 2016/10/31.
 */
@Entity
@Table(name = "extoj_link")
public class ExtOjLink implements Serializable {

    @Id
    @Enumerated(value = EnumType.STRING)
    private OJType oj;

    @Column(length = 1024)
    private String indexLink;

    @Column(length = 1024)
    private String userInfoLink;

    @Column(length = 1024)
    private String pbStatusLink;

    @Column(length = 1024)
    private String problemLink;

    public ExtOjLink() {
    }

    public OJType getOj() {
        return oj;
    }

    public void setOj(OJType oj) {
        this.oj = oj;
    }

    public String getIndexLink() {
        return indexLink;
    }

    public void setIndexLink(String indexLink) {
        this.indexLink = indexLink;
    }

    public String getUserInfoLink() {
        return userInfoLink;
    }

    public void setUserInfoLink(String userInfoLink) {
        this.userInfoLink = userInfoLink;
    }

    public String getPbStatusLink() {
        return pbStatusLink;
    }

    public void setPbStatusLink(String pbStatusLink) {
        this.pbStatusLink = pbStatusLink;
    }

    public String getProblemLink() {
        return problemLink;
    }

    public void setProblemLink(String problemLink) {
        this.problemLink = problemLink;
    }

    @Override
    public String toString() {
        return "ExtOjLink{" +
                "oj=" + oj +
                ", indexLink='" + indexLink + '\'' +
                ", userInfoLink='" + userInfoLink + '\'' +
                ", pbStatusLink='" + pbStatusLink + '\'' +
                ", problemLink='" + problemLink + '\'' +
                '}';
    }
}
