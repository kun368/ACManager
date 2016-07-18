package com.zzkun.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kun on 2016/7/5.
 */
public class UHuntTreeNode implements Comparable<UHuntTreeNode>, Serializable {

    public int deep;
    public int id;
    public String name;
    public String type;
    public List<UHuntTreeNode> son = new ArrayList<>();

    public UHuntTreeNode() {
    }

    public UHuntTreeNode(int deep, int id, String name, String type) {
        this.deep = deep;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "UHuntTreeNode{" +
                "deep=" + deep +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int compareTo(UHuntTreeNode o) {
        return Integer.compare(id, o.id);
    }
}
