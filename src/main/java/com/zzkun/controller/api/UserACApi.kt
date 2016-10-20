package com.zzkun.controller.api

import com.alibaba.fastjson.JSONObject
import com.zzkun.service.ExtOjService
import com.zzkun.service.UserService
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

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var extojService: ExtOjService

    @RequestMapping(value = "/{username}/list",
            method = arrayOf(RequestMethod.GET))
    fun list(@PathVariable username: String): String {
        val user = userService.getUserByUsername(username)
        val list = extojService.getUserAC(user)
        val map = mutableMapOf<String, MutableList<String>>()
        val set = mutableSetOf<String>()
        list.forEach {
            val oj = it.ojName.toString()
            val id = it.ojPbId
            set.add(oj)
            if(!map.contains(oj))
                map[oj] = mutableListOf<String>()
            map[oj]?.add(id)
        }
        val json = JSONObject(true)
        json["ojs"] = set.toList()
        json["ac"] = map as Map<String, Any>?
        return json.toString()
    }
}