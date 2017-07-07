package com.zzkun.service.extoj

import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.*
import com.zzkun.util.web.HDUWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
open class HDUService : IExtOJAdapter {

    @Autowired lateinit var hduWebGetter : HDUWebGetter
    @Autowired lateinit var extOjLinkRepo: ExtOjLinkRepo

    override fun getOjType(): OJType {
        return OJType.HDU
    }

    override fun getOjLink(): ExtOjLink {
        return extOjLinkRepo.findOne(getOjType())
    }

    override fun getUserACPbsOnline(user: User, link: String): List<UserACPb> {
        val acPbs = hduWebGetter.userACPbs(user.hduName, link)
        return acPbs.map { UserACPb(user, OJType.HDU, it) } .toList()
    }

    override fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo> {
        return hduWebGetter.allPbInfo(link)
    }
}