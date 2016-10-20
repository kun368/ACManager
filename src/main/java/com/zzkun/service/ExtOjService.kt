package com.zzkun.service

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.service.extoj.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2016/9/30.
 */
@Service
open class ExtOjService {

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
        for(oj in allExtOjServices())
            for(user in users)
                set.addAll(oj.getUserACPbsOnline(user))
        userACPbRepo.deleteAll()
        userACPbRepo.save(set)
    }

    fun flushPbInfoDB() {
        val list = arrayListOf<ExtOjPbInfo>()
        for(oj in allExtOjServices())
            list.addAll(oj.getAllPbInfoOnline())
        extOjPbInfoRepo.deleteAll()
        extOjPbInfoRepo.save(list)
    }

    fun getUserAC(user: User): List<UserACPb> {
        return userACPbRepo.findByUser(user)
    }
}
