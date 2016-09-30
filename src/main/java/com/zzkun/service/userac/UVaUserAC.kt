package com.zzkun.service.userac

import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.util.uhunt.UHuntAnalyser
import com.zzkun.util.web.UHuntWebGetter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by kun on 2016/9/29.
 */
@Component
open class UVaUserAC : IOJUserAC {

    @Autowired
    lateinit var webgetter: UHuntWebGetter

    @Autowired
    lateinit var uhuntAnalyser : UHuntAnalyser

    override fun userACPbs(user: User): List<UserACPb> {
        val list = webgetter.userACSubmits(user.uvaId)
        return list.map { UserACPb(user, UserACPb.OJType.UVA, uhuntAnalyser.pidToNum(it.pbId).toString(), false) }
    }
}