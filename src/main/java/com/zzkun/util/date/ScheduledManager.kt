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
        @Autowired private val ojContestService: OJContestService,
        @Autowired private val extOjService: ExtOjService,
        @Autowired private val ratingService: RatingService,
        @Autowired private val cfbcService: CFBCService,
        @Autowired private val systemService: SystemService) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ScheduledManager::class.java)
    }

    val timer = Timer()

    // 注册计划任务
    @PostConstruct
    fun run() {

        timer.schedule(object : TimerTask() {
            override fun run() {
                logger.info("做题统计:6小时")
                extOjService.flushACDB()
            }
        }, 3600 * 1000L, 6 * 3600 * 1000L)

        timer.schedule(object : TimerTask() {
            override fun run() {
                logger.info("近期比赛:1小时")
                ojContestService.flushOJContests()
            }
        }, 3600 * 1000L, 1 * 3600 * 1000L)

        timer.schedule(object : TimerTask() {
            override fun run() {
                logger.info("全局比赛Rating:1天")
                ratingService.flushGlobalUserRating()
            }
        }, 3600 * 1000L, 24 * 3600 * 1000L)

        timer.schedule(object : TimerTask() {
            override fun run() {
                logger.info("CF/BC Rating:12小时")
                cfbcService.flushBCUserInfo()
                cfbcService.flushCFUserInfo()
            }
        }, 3600 * 1000L, 12 * 3600 * 1000L)

        timer.schedule(object : TimerTask() {
            override fun run() {
                logger.info("更新系统状态:1天")
                systemService.saveCurState()
            }
        }, 60 * 1000L, 24 * 3600 * 1000L)

        logger.info("注册计划任务完毕...")
    }
}