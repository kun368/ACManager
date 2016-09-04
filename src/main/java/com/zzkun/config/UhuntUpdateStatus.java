package com.zzkun.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2016/7/22.
 */
@Component
public class UhuntUpdateStatus {

    private static final Logger logger = LoggerFactory.getLogger(UhuntUpdateStatus.class);

    private boolean updating = false;

    private LocalDateTime lastTime = null;

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public boolean canUpdate() {
        if(updating) return false;
        if(lastTime == null) return true;
        Duration between = Duration.between(lastTime, LocalDateTime.now());
        logger.info("updatedb时间信息：距离上次更新{}", between);
        return between.getSeconds() > 3 * 60;
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
