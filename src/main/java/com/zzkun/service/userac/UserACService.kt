package com.zzkun.service.userac

import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.User
import com.zzkun.model.UserACPb
import com.zzkun.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by kun on 2016/9/30.
 */
@Service
open class UserACService {

    @Autowired
    lateinit var userService : UserService

    @Autowired
    lateinit var userACPbRepo : UserACPbRepo

    @Autowired
    lateinit var uvaUserACService: UVaUserACService

    @Autowired
    lateinit var vjudgeUserACService: VJudgeUserACService


    fun userAllAC(user: User): List<UserACPb> {
        val ojServices = listOf(
                uvaUserACService,
                vjudgeUserACService
        )
        val res = sortedSetOf<UserACPb>()
        for(oj in ojServices)
            res.addAll(oj.userACPbs(user))
        return res.toList()
    }

    fun flushACDB() {
        userACPbRepo.deleteAll()
        val date = arrayListOf<UserACPb>()
        for(user in userService.allUser())
            date.addAll(userAllAC(user))
        userACPbRepo.save(date)
    }

}
