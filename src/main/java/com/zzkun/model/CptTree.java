package com.zzkun.model;

import com.zzkun.util.cpt.Node;
import com.zzkun.util.cpt.NodeBuilderKt;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
@Entity
@Table(name = "cpt_tree")
public class CptTree implements Serializable, Comparable<CptTree> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Lob
    private String rootNode;

    @Column(length = 10240)
    private String remark;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "addUid")
    private User addUser;


    private LocalDateTime addTime;


    public CptTree() {
    }

    public CptTree(String name, String rootNode, String remark, User addUser, LocalDateTime addTime) {
        this.name = name;
        this.rootNode = rootNode;
        this.remark = remark;
        this.addUser = addUser;
        this.addTime = addTime;
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

    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getAddUser() {
        return addUser;
    }

    public void setAddUser(User addUser) {
        this.addUser = addUser;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CptTree cptTree = (CptTree) o;

        return id != null ? id.equals(cptTree.id) : cptTree.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(@NotNull CptTree o) {
        return Integer.compare(id, o.id);
    }

    ///////

    public Node getNode() {
        return NodeBuilderKt.parseJson(rootNode);
    }
}
