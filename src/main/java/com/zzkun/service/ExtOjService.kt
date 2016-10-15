package com.zzkun.service

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.service.extoj.HDUService
import com.zzkun.service.extoj.IExtOJAdapter
import com.zzkun.service.extoj.UVaService
import com.zzkun.service.extoj.VJudgeService
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

    fun allExtOjServices(): List<IExtOJAdapter> {
        return listOf(
                vjudgeService,
                uvaService,
                hduService
        )
    }

    fun flushACDB() {
        userACPbRepo.deleteAll()
        val set = sortedSetOf<UserACPb>()
        val users = userService.allUser()
        for(oj in allExtOjServices())
            for(user in users)
                set.addAll(oj.getUserACPbsOnline(user))
        userACPbRepo.save(set)
    }

    fun flushPbInfoDB() {
        extOjPbInfoRepo.deleteAll()
        val list = arrayListOf<ExtOjPbInfo>()
        for(oj in allExtOjServices())
            list.addAll(oj.getAllPbInfoOnline())
        extOjPbInfoRepo.save(list)
    }

    fun getUserAC(user: User): List<UserACPb> {
        return userACPbRepo.findByUser(user)
    }
}
