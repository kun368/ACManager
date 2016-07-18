package com.zzkun.model;



import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 竞赛集（集训）实体类
 * Created by kun on 2016/7/13.
 */
@Entity
@Table(name = "contestGroup")
public class ContestGroup implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "contestGroup", fetch = FetchType.EAGER)
    private List<Contest> contests = new ArrayList<>();

    public ContestGroup() {
    }

    public ContestGroup(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }

    @Override
    public String toString() {
        return "ContestGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
