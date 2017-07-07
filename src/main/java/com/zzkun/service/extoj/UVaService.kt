package com.zzkun.service.extoj

import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.*
import com.zzkun.util.uhunt.UHuntAnalyser
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

    @Autowired lateinit var uHuntWebGetter: UHuntWebGetter
    @Autowired lateinit var uhuntAnalyser : UHuntAnalyser
    @Autowired lateinit var extOjLinkRepo: ExtOjLinkRepo

    override fun getOjType(): OJType {
        return OJType.UVA
    }

    override fun getOjLink(): ExtOjLink {
        return extOjLinkRepo.findOne(getOjType())
    }


    override fun getUserACPbsOnline(user: User, link: String): List<UserACPb> {
        try {
            val list = uHuntWebGetter.userACSubmits(user.uvaId, link)
            return list.map { UserACPb(user, OJType.UVA, uhuntAnalyser.pidToNum(it.toString())) }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo> {
        return uHuntWebGetter.allPbInfo() ?: uHuntWebGetter.allPbInfo2(link)
    }
}