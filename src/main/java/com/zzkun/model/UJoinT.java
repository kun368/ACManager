package com.zzkun.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/21.
 */
@Entity
@Table(name = "user_join_training")
public class UJoinT implements Serializable {

    public enum Status {
        Pending, Success, Reject
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainingId")
    private Training training;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public UJoinT() {
    }

    public UJoinT(User user, Training training, Status status) {
        this.user = user;
        this.training = training;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UJoinT{" +
                "id=" + id +
                ", user=" + user +
                ", training=" + training +
                ", status=" + status +
                '}';
    }
}
