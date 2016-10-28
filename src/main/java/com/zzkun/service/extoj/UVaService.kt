package com.zzkun.service.extoj

import com.zzkun.dao.UserRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.util.uhunt.UHuntAnalyser
import com.zzkun.util.uhunt.UhuntTreeManager
import com.zzkun.util.web.UHuntWebGetter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2016/9/29.
 */
@Service
open class UVaService : IExtOJAdapter {

    companion object {
        private val logger = LoggerFactory.getLogger(UVaService::class.java)
    }

    @Autowired lateinit var uhuntTreeManager: UhuntTreeManager
    @Autowired lateinit var userRepo: UserRepo
    @Autowired lateinit var uHuntWebGetter: UHuntWebGetter
    @Autowired lateinit var webgetter: UHuntWebGetter
    @Autowired lateinit var uhuntAnalyser : UHuntAnalyser

    override fun getUserACPbsOnline(user: User): List<UserACPb> {
        try {
            val list = webgetter.userACSubmits(user.uvaId)
            return list.map { UserACPb(user, OJType.UVA, uhuntAnalyser.pidToNum(it).toString()) }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override fun getAllPbInfoOnline(): List<ExtOjPbInfo> {
        return uHuntWebGetter.allPbInfo() ?: uHuntWebGetter.allPbInfo2()
    }
}