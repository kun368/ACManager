package com.zzkun.service

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.dao.UserRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.service.extoj.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by kun on 2016/9/30.
 */
@Service
open class ExtOjService(
        @Autowired val userService: UserService,
        @Autowired val userRepo: UserRepo,
        @Autowired val userACPbRepo: UserACPbRepo,
        @Autowired val extOjPbInfoRepo: ExtOjPbInfoRepo,
        @Autowired val uvaService: UVaService,
        @Autowired val vjudgeService: VJudgeService,
        @Autowired val hduService: HDUService,
        @Autowired val pojService: POJService,
        @Autowired val cfService: CFService) {

    companion object {
        private val logger = LoggerFactory.getLogger(ExtOjService::class.java)
    }

    private fun allExtOjServices(): List<IExtOJAdapter> {
        return listOf(vjudgeService, uvaService, hduService, pojService, cfService)
    }

    // 从WEB获取用户AC题目
    private fun getUsersACPbsFromWeb(users: List<User>): TreeSet<UserACPb> {
        val set = TreeSet<UserACPb>()
        logger.info("所有用户数量：{}", users.size)
        val futureList: Vector<Future<List<UserACPb>>> = Vector()
        val service = Executors.newFixedThreadPool(7)
        for(oj in allExtOjServices()) {
            val link = oj.getOjLink().userInfoLink
            for(user in users) {
                val cur = Callable { oj.getUserACPbsOnline(user, link) }
                futureList.add(service.submit(cur))
            }
        }
        service.shutdown()
        futureList.forEach {
            try {
                set.addAll(it.get(20, TimeUnit.SECONDS))
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        logger.info("所有人AC题目数：${set.size}")
        return set
    }

    // 从WEB获取所有题目信息
    private fun getPbInfosFromWeb(): TreeSet<ExtOjPbInfo> {
        val set = TreeSet<ExtOjPbInfo>()
        for(oj in allExtOjServices()) {
            val link = oj.getOjLink().pbStatusLink
            if(link != null)
                set.addAll(oj.getAllPbInfoOnline(link))
        }
        logger.info("所有题目数量总计：${set.size}")
        return set
    }

    //更新用户最后一次AC时间
    fun flushUserACDate(set: Set<UserACPb>) {
        val userSet = set.map { it.user }.toSortedSet()
        val curDate = LocalDate.now()
        userSet.forEach {
            it.lastACDate = curDate
        }
        userRepo.save(userSet)
    }

    fun flushACDB() {
        try {
            synchronized(this) {
                logger.info("开始更新用户AC题目纪录...")
                val cur = getUsersACPbsFromWeb(userService.allUser())
                val pre = TreeSet<UserACPb>(userACPbRepo.findAll())
                val new: Set<UserACPb> = cur - pre
                logger.info("pre:${pre.size}, cur:${cur.size}, new:${new.size}")
                userACPbRepo.save(new)
                logger.info("更新用户AC题目数据完毕！")
                flushUserACDate(new)
                logger.info("更新用户最后一次AC时间完毕！")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun flushPbInfoDB() {
        try {
            synchronized(this) {
                val cur = getPbInfosFromWeb()
                val pre = extOjPbInfoRepo.findAll()
                val sum = ArrayList<ExtOjPbInfo>(cur)
                pre.filterNotTo(sum) { cur.contains(it) }
                logger.info("pre:${pre.size}, cur:${cur.size}, sum:${sum.size}")
                extOjPbInfoRepo.deleteAll()
                extOjPbInfoRepo.save(sum)
                logger.info("更新完毕！")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
