package com.zzkun.service.userac

import com.zzkun.model.User
import com.zzkun.model.UserACPb

/**
 * Created by kun on 2016/9/29.
 */
interface IOJUserAC {
    fun userACPbs(user: User): List<UserACPb>
}
