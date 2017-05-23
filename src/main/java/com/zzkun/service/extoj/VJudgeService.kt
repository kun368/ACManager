package com.zzkun.service.extoj

import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.*
import com.zzkun.util.web.VJudgeWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class VJudgeService : IExtOJAdapter {

    @Autowired lateinit var vjudgeWebGetter : VJudgeWebGetter
    @Autowired lateinit var extOjLinkRepo: ExtOjLinkRepo

    override fun getOjType(): OJType {
        return OJType.Vjudge
    }

    override fun getOjLink(): ExtOjLink {
        return extOjLinkRepo.findOne(getOjType())
    }

    override fun getUserACPbsOnline(user: User, link: String): List<UserACPb> {
        try {
            val acMap = vjudgeWebGetter.getUserACMap(user.vjname, link)
            val res = arrayListOf<UserACPb>()
            for((key, value) in acMap) {
                for(pbId in value) {
                    val cur = try { UserACPb(user, OJType.valueOf(key), pbId) } catch (e: Exception) { null }
                    if(cur != null) res.add(cur)
                }
            }
            return res
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo> {
        return emptyList()
    }
}