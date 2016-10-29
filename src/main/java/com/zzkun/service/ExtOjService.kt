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

    private fun allExtOjServices(): List<IExtOJAdapter> {
        return listOf(vjudgeService, uvaService, hduService, pojService)
    }

    private fun getUsersACPbsFromWeb(users: List<User>): Set<UserACPb> {
        val set = TreeSet<UserACPb>()
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
        return set
    }

    private fun getPbInfosFromWeb(): Set<ExtOjPbInfo> {
        val set = TreeSet<ExtOjPbInfo>()
        for(oj in allExtOjServices())
            set.addAll(oj.getAllPbInfoOnline())
        logger.info("所有题目数量总计：${set.size}")
        return set
    }

    fun flushACDB() {
        val set = TreeSet<UserACPb>()
        val users = userService.allUser()
        set.addAll(getUsersACPbsFromWeb(users))
        set.addAll(userACPbRepo.findAll())
        userACPbRepo.deleteAll()
        userACPbRepo.save(set)
        logger.info("更新完毕！, 现有纪录${set.size}条")
    }

    fun flushPbInfoDB() {
        val set = TreeSet<ExtOjPbInfo>()
        set.addAll(getPbInfosFromWeb())
        set.addAll(extOjPbInfoRepo.findAll())
        extOjPbInfoRepo.deleteAll()
        extOjPbInfoRepo.save(set)
        logger.info("更新完毕！, 现有纪录${set.size}条")
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
