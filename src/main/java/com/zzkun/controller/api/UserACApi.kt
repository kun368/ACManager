package com.zzkun.controller.api

import com.alibaba.fastjson.JSONArray
import com.zzkun.service.UserService
import com.zzkun.service.userac.UserACService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by kun on 2016/9/30.
 */
@RestController
@RequestMapping("/api/userac")
class UserACApi {

    @Autowired
    lateinit var userACService : UserACService

    @Autowired
    lateinit var userService: UserService

    @RequestMapping(value = "/{username}/list",
            method = arrayOf(RequestMethod.GET))
    fun list(@PathVariable username: String): String {
        val user = userService.getUserByUsername(username)
        val list = userACService.getUserAC(user)
        val json = JSONArray(list.size)
        list.forEach {
            val cur = JSONArray(2)
            cur.add(it.ojName.toString())
            cur.add(it.ojPbId)
            json.add(cur)
        }
        return json.toString()
    }
}