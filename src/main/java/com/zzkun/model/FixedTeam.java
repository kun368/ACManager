package com.zzkun.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/30.
 */
@Entity
@Table(name = "fixed_team")
public class FixedTeam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name1;

    private String name2;

    private ArrayList<Integer> uids = new ArrayList<>();

    public FixedTeam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public ArrayList<Integer> getUids() {
        return uids;
    }

    public void setUids(ArrayList<Integer> uids) {
        this.uids = uids;
    }

    @Override
    public String toString() {
        return "FixedTeam{" +
                "id=" + id +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", uids=" + uids +
                '}';
    }
}
