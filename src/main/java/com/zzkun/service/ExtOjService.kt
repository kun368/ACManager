package com.zzkun.service

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.service.extoj.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by kun on 2016/9/30.
 */
@Service
open class ExtOjService {

    companion object {
        private val logger = LoggerFactory.getLogger(ExtOjService::class.java)
    }

    @Autowired lateinit private var userService: UserService
    @Autowired lateinit private var userACPbRepo: UserACPbRepo
    @Autowired lateinit private var extOjPbInfoRepo: ExtOjPbInfoRepo

    @Autowired lateinit private var uvaService: UVaService
    @Autowired lateinit private var vjudgeService: VJudgeService
    @Autowired lateinit private var hduService: HDUService
    @Autowired lateinit private var pojService: POJService

    fun allExtOjServices(): List<IExtOJAdapter> {
        return listOf(vjudgeService, uvaService, hduService, pojService)
    }

    fun flushACDB() {
        val set = sortedSetOf<UserACPb>()
        val users = userService.allUser()
        logger.info("所有用户数量：{}", users.size)
        val futureList: Vector<Future<List<UserACPb>>> = Vector()
        val service = Executors.newFixedThreadPool(7)
        for(oj in allExtOjServices()) {
            for(user in users) {
                val cur = Callable { oj.getUserACPbsOnline(user) }
                futureList.add(service.submit(cur))
            }
        }
        service.shutdown()
        futureList.forEach {
            try {
                set.addAll(it.get(17, TimeUnit.SECONDS))
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        logger.info("所有人AC题目数：${set.size}")
        userACPbRepo.deleteAll()
        userACPbRepo.save(set)
        logger.info("更新完毕！")
    }

    fun flushPbInfoDB() {
        val list = arrayListOf<ExtOjPbInfo>()
        for(oj in allExtOjServices())
            list.addAll(oj.getAllPbInfoOnline())
        logger.info("所有题目数量总计：{}", list.size)
        extOjPbInfoRepo.deleteAll()
        extOjPbInfoRepo.save(list)
    }

    fun getUserAC(user: User): List<UserACPb> {
        return userACPbRepo.findByUser(user)
    }

    fun getUserPerOjACMap(users: List<User>): Map<Int, Map<String, Int>> {
        val res = HashMap<Int, Map<String, Int>>()
        users.forEach {
            val cur = HashMap<String, Int>()
            it.acPbList.forEach {
                val oj = it.ojName.toString()
                if(cur.contains(oj)) cur[oj] = cur[oj]!! + 1
                else cur[oj] = 1
            }
            cur["SUM"] = it.acPbList.size
            res[it.id] = cur
        }
        return res
    }
}
