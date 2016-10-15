package com.zzkun.service.extoj

import com.zzkun.dao.UVaSubmitRepo
import com.zzkun.dao.UserRepo
import com.zzkun.model.*
import com.zzkun.util.uhunt.UHuntAnalyser
import com.zzkun.util.uhunt.UhuntTreeManager
import com.zzkun.util.web.UHuntWebGetter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by kun on 2016/9/29.
 */
@Service
open class UVaService : IExtOJAdapter {

    companion object {
        private val logger = LoggerFactory.getLogger(UVaService::class.java)
    }

    @Autowired lateinit var uhuntTreeManager: UhuntTreeManager
    @Autowired lateinit var uVaSubmitRepo: UVaSubmitRepo
    @Autowired lateinit var userRepo: UserRepo
    @Autowired lateinit var uHuntWebGetter: UHuntWebGetter
    @Autowired lateinit var uHuntAnalyser: UHuntAnalyser
    @Autowired lateinit var webgetter: UHuntWebGetter
    @Autowired lateinit var uhuntAnalyser : UHuntAnalyser

    override fun getUserACPbsOnline(user: User): List<UserACPb> {
        try {
            val list = webgetter.userACSubmits(user.uvaId)
            return list.map { UserACPb(user, OJType.UVA, uhuntAnalyser.pidToNum(it.pbId).toString()) }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override fun getAllPbInfoOnline(): List<ExtOjPbInfo> {
        return uHuntWebGetter.allPbInfo()
    }



    //-------------------------

    fun getBookName(): List<String> {
        return uhuntTreeManager.bookMap.keys.map({ it.name }).toList()
    }

    fun getChapterName(): List<String> {
        return uhuntTreeManager.chapterMap.keys.map({ it.name }).toList()
    }

    fun getCptCnt(users: List<Int>): List<List<Int>> {
        return uHuntAnalyser.getCnt(users, uhuntTreeManager.chapterMap)
    }

    fun getBookCnt(users: List<Int>): List<List<Int>> {
        return uHuntAnalyser.getCnt(users, uhuntTreeManager.bookMap)
    }

    /**
     * 更新所有用户的提交数据
     * 多线程...
     */
    @Scheduled(cron = "0 0 0/6 * * ?")
    fun flushUVaSubmit() {
        logger.info("收到更新uva提交db请求...")
        val userList = userRepo.findAll()
        val futureList = ArrayList<Future<List<UVaSubmit>>>()
        val service = Executors.newFixedThreadPool(5)
        for (user in userList) {
            if (user.uvaId != null) {
                futureList.add(service.submit<List<UVaSubmit>> { uHuntWebGetter.userACSubmits(user.uvaId) })
            }
        }
        service.shutdown()
        val submits = ArrayList<UVaSubmit>()
        for (future in futureList) {
            val submitList = future.get(20, TimeUnit.SECONDS)
            if (submitList != null && !submitList.isEmpty())
                submits.addAll(submitList)
        }
        uVaSubmitRepo.save(submits)
        logger.info("数据库用户AC题目数据更新完毕！")
    }


}