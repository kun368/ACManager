package com.zzkun.model;

import com.zzkun.util.stder.DataStder;
import com.zzkun.util.stder.RawData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛整体情况（排行榜）
 * Created by Administrator on 2016/6/27.
 */
@Entity
@Table(name = "contest")
public class Contest implements Serializable {

    public static final String TYPE_PERSONAL = "PERSONAL";
    public static final String TYPE_TEAM = "TEAM";

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String name;

    private String type;

    private LocalDate time;

    private int pbCnt;

    @Lob
    private ArrayList<TeamRanking> ranks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private ContestGroup contestGroup;


    public Contest() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public int getPbCnt() {
        return pbCnt;
    }

    public void setPbCnt(int pbCnt) {
        this.pbCnt = pbCnt;
    }

    public ArrayList<TeamRanking> getRanks() {
        return ranks;
    }

    public void setRanks(ArrayList<TeamRanking> ranks) {
        this.ranks = ranks;
    }

    public ContestGroup getContestGroup() {
        return contestGroup;
    }

    public void setContestGroup(ContestGroup contestGroup) {
        this.contestGroup = contestGroup;
    }

    /**
     * 计算此次竞赛各队标准分
     * 时间复杂度：O(队伍数*题数)
     * @param alpha 放大倍数
     * @param beta 基准分
     * @return 各队标准分
     */
    public double[] calcTemesStdScore(double alpha, double beta) {
        DataStder dataStder = new DataStder();
        double[] ans = new double[ranks.size()];
        for(int i = 0; i < pbCnt; ++i) {
            List<RawData> list = new ArrayList<>();
            for (TeamRanking rank : ranks) {
                PbStatus pbStatus = rank.getPbStatus().get(i);
                list.add(new RawData((double) pbStatus.calcPenalty(), pbStatus.isSolved()));
            }
            double[] std = dataStder.std(list, alpha, beta);
            for(int j = 0; j < ranks.size(); ++j)
                ans[j] += std[j];
        }
        return ans;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", pbCnt=" + pbCnt +
                ", contestGroup=" + contestGroup +
                '}';
    }
}
