package com.zzkun.config;

import org.springframework.stereotype.Component;

/**
 * Created by kun on 2016/7/5.
 */
@Component
public class UHuntConfig {
    private double fixedScore = 1;
    private double additionScore = 1;
    private double threshold = 0.1;

    public double getFixedScore() {
        return fixedScore;
    }

    public void setFixedScore(double fixedScore) {
        this.fixedScore = fixedScore;
    }

    public double getAdditionScore() {
        return additionScore;
    }

    public void setAdditionScore(double additionScore) {
        this.additionScore = additionScore;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "UHuntConfig{" +
                "fixedScore=" + fixedScore +
                ", additionScore=" + additionScore +
                ", threshold=" + threshold +
                '}';
    }
}
