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
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private Integer userId;

    private Integer trainingId;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public UJoinT() {
    }

    public Integer getId() {
        return id;
    }

    public UJoinT(Integer userId, Integer trainingId, Status status) {
        this.userId = userId;
        this.trainingId = trainingId;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
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
                ", userId=" + userId +
                ", trainingId=" + trainingId +
                ", status=" + status +
                '}';
    }
}
