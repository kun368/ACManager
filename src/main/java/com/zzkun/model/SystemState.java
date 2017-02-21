package com.zzkun.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
@Entity
@Table(name = "system_state")
public class SystemState {

    @Id
    private LocalDate date;

    private Long userCount;

    private Long sumACCount;

    private Long sumContestCount;

    public SystemState() {
    }

    public SystemState(LocalDate date, Long userCount, Long sumACCount, Long sumContestCount) {
        this.date = date;
        this.userCount = userCount;
        this.sumACCount = sumACCount;
        this.sumContestCount = sumContestCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getSumACCount() {
        return sumACCount;
    }

    public void setSumACCount(Long sumACCount) {
        this.sumACCount = sumACCount;
    }

    public Long getSumContestCount() {
        return sumContestCount;
    }

    public void setSumContestCount(Long sumContestCount) {
        this.sumContestCount = sumContestCount;
    }

    @Override
    public String toString() {
        return "SystemState{" +
                "date=" + date +
                ", userCount=" + userCount +
                ", sumACCount=" + sumACCount +
                ", sumContestCount=" + sumContestCount +
                '}';
    }
}
