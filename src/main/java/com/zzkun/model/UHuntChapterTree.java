package com.zzkun.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kun on 2016/7/5.
 */
public class UHuntChapterTree implements Comparable<UHuntChapterTree>, Serializable {

    public int deep;
    public int id;
    public String name;
    public String type;
    public List<UHuntChapterTree> son = new ArrayList<>();

    public UHuntChapterTree() {
    }

    public UHuntChapterTree(int deep, int id, String name, String type) {
        this.deep = deep;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "UHuntChapterTree{" +
                "deep=" + deep +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int compareTo(UHuntChapterTree o) {
        return Integer.compare(id, o.id);
    }
}
