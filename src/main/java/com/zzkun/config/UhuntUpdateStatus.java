package com.zzkun.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by Administrator on 2016/7/22.
 */
@Component
public class UhuntUpdateStatus {

    private boolean updating = false;

    private LocalDateTime lastTime = null;

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public boolean canUpdate() {
        if(updating) return false;
        if(lastTime == null) return true;
        int dis = LocalDateTime.now().getSecond() - lastTime.getSecond();
        return dis > 30 * 60;
    }

    public void preUpdate() {
        updating = true;
    }

    public void  afterUpdate() {
        lastTime = LocalDateTime.now();
        updating = false;
    }

    @Override
    public String toString() {
        return "UhuntUpdateStatus{" +
                "updating=" + updating +
                ", lastTime=" + lastTime +
                '}';
    }
}
