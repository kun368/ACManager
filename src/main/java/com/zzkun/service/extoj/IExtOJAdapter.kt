package com.zzkun.service.extoj

import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.User
import com.zzkun.model.UserACPb

interface IExtOJAdapter {
    /**
     * 在线获取某用户本OJ所有AC的题目
     */
    fun getUserACPbsOnline(user: User): List<UserACPb>

    /**
     * 在线获取某用户本OJ所有题目信息
     */
    fun getAllPbInfoOnline(): List<ExtOjPbInfo>
}
