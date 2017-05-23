package com.zzkun.service.extoj

import com.zzkun.model.*

interface IExtOJAdapter {
    /**
     * 在线获取某用户本OJ所有AC的题目
     */
    fun getUserACPbsOnline(user: User, link: String): List<UserACPb>

    /**
     * 在线获取某用户本OJ所有题目信息
     */
    fun getAllPbInfoOnline(link: String): List<ExtOjPbInfo>

    /**
     * 从数据库中读取oj链接
     */
    fun getOjLink(): ExtOjLink

    fun getOjType(): OJType
}
