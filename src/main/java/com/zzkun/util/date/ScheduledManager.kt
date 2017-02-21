package com.zzkun.util.date

import com.zzkun.service.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

/**
 * Created by Administrator on 2017/2/19 0019.
 */
@Component
open class ScheduledManager(
        @Autowired val ojContestService: OJContestService,
        @Autowired val extOjService: ExtOjService,
        @Autowired val ratingService: RatingService,
        @Autowired val cfbcService: CFBCService,
        @Autowired val systemService: SystemService) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ScheduledManager::class.java)
    }

    val timer = Timer()

    // 注册计划任务
    @PostConstruct
    fun run() {

        // 做题统计:6小时
        timer.schedule(object : TimerTask() {
            override fun run() {
                extOjService.flushACDB()
            }
        }, 3600 * 1000L, 6 * 3600 * 1000L)

        // 近期比赛:1小时
        timer.schedule(object : TimerTask() {
            override fun run() {
                ojContestService.flushOJContests()
            }
        }, 3600 * 1000L, 1 * 3600 * 1000L)

        // 全局比赛Rating:1天
        timer.schedule(object : TimerTask() {
            override fun run() {
                ratingService.flushGlobalUserRating()
            }
        }, 3600 * 1000L, 24 * 3600 * 1000L)

        // CF/BC Rating:12小时
        timer.schedule(object : TimerTask() {
            override fun run() {
                cfbcService.flushBCUserInfo()
                cfbcService.flushCFUserInfo()
            }
        }, 3600 * 1000L, 12 * 3600 * 1000L)

        // 更新系统状态:1天
        timer.schedule(object : TimerTask() {
            override fun run() {
                systemService.saveCurState()
            }
        }, 60 * 1000L, 24 * 3600 * 1000L)

        logger.info("注册计划任务完毕...")
    }
}