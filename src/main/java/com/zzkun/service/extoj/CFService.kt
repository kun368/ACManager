package com.zzkun.service.extoj

import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.*
import com.zzkun.util.web.CFWebGetter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class CFService : IExtOJAdapter {

    companion object {
        private val logger = LoggerFactory.getLogger(CFService::class.java)
    }

    @Autowired lateinit var cfWebGetter : CFWebGetter
    @Autowired lateinit var extOjLinkRepo: ExtOjLinkRepo

    override fun getOjLink(): ExtOjLink {
        return extOjLinkRepo.findOne(getOjType())
    }

    override fun getOjType(): OJType {
        return OJType.CodeForces
    }

    override fun getUserACPbsOnline(user: User, link: String): List<UserACPb> {
        val acPbs = cfWebGetter.userACPbs(user.cfname, link)
        return acPbs.map { UserACPb(user, OJType.CodeForces, it) } .toList()
    }

    override fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo> {
        throw UnsupportedOperationException("not implemented")
    }
}