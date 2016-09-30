package com.zzkun.service.userac

import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.util.web.VJudgeWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class VJudgeUserACService : IOJUserAC {

    @Autowired
    lateinit var vjudgeWebGetter : VJudgeWebGetter

    override fun userACPbs(user: User): List<UserACPb> {
        try {
            val acMap = vjudgeWebGetter.getUserACMap(user.vjname)
            val res = arrayListOf<UserACPb>()
            for((key, value) in acMap)
                for(pbId in value)
                    res.add(UserACPb(user, UserACPb.OJType.valueOf(key), pbId))
            return res
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }
}