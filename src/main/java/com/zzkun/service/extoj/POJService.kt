package com.zzkun.service.extoj

import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.*
import com.zzkun.util.web.POJWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2016/10/20.
 */
@Service
class POJService : IExtOJAdapter {

    @Autowired lateinit var pojWebGetter: POJWebGetter
    @Autowired lateinit var extOjLinkRepo: ExtOjLinkRepo

    override fun getOjType(): OJType {
        return OJType.POJ
    }

    override fun getOjLink(): ExtOjLink {
        return extOjLinkRepo.getOne(getOjType())
    }


    override fun getUserACPbsOnline(user: User, link: String): List<UserACPb> {
        val acPbs = pojWebGetter.userACPbs(user.pojName, link)
        return acPbs.map { UserACPb(user, OJType.POJ, it) } .toList()
    }

    override fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo> {
        return pojWebGetter.allPbInfo(link)
    }
}