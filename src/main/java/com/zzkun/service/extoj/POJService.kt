package com.zzkun.service.extoj

import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.util.web.POJWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2016/10/20.
 */
@Service
class POJService : IExtOJAdapter {

    @Autowired lateinit var pojWebGetter: POJWebGetter

    override fun getUserACPbsOnline(user: User): List<UserACPb> {
        val acPbs = pojWebGetter.userACPbs(user.pojName)
        return acPbs.map { UserACPb(user, OJType.POJ, it) } .toList()
    }

    override fun getAllPbInfoOnline(): List<ExtOjPbInfo> {
        return pojWebGetter.allPbInfo()
    }
}