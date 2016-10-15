package com.zzkun.service.extoj

import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.util.web.VJudgeWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class VJudgeService : IExtOJAdapter {

    @Autowired lateinit var vjudgeWebGetter : VJudgeWebGetter

    override fun getUserACPbsOnline(user: User): List<UserACPb> {
        try {
            val acMap = vjudgeWebGetter.getUserACMap(user.vjname)
            val res = arrayListOf<UserACPb>()
            for((key, value) in acMap)
                for(pbId in value)
                    res.add(UserACPb(user, OJType.valueOf(key), pbId))
            return res
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override fun getAllPbInfoOnline(): List<ExtOjPbInfo> {
        return emptyList()
    }
}