package com.zzkun.controller.api

import com.alibaba.fastjson.JSONObject
import com.zzkun.dao.ExtOjLinkRepo
import com.zzkun.model.OJType
import com.zzkun.service.ExtOjService
import com.zzkun.service.UserService
import com.zzkun.util.uhunt.UHuntAnalyser
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
    @Autowired lateinit var extojLinkRepo: ExtOjLinkRepo
    @Autowired lateinit var uhuntAnalyser: UHuntAnalyser

    @RequestMapping(value = ["/{username}/list"],
            method = [(RequestMethod.GET)])
    fun list(@PathVariable username: String): String {
        val user = userService.getUserByUsername(username)
        val list = extojService.getUserAC(user)
        val map = mutableMapOf<String, MutableList<String>>()
        val set = mutableSetOf<String>()
        val visited = mutableSetOf<String>()
        list.forEach {
            val oj = it.ojName.toString()
            val id = it.ojPbId
            if(!visited.contains(oj+id)){
                visited.add(oj + id)
                set.add(oj)
                if(!map.contains(oj))
                    map[oj] = mutableListOf<String>()
                map[oj]?.add(id)
            }
        }
        val json = JSONObject(true)
        json["ojs"] = set.toList()
        json["ac"] = map as Map<String, Any>?
        return json.toString()
    }

    @RequestMapping(value = ["/url/{oj}/{pid}"], method = [(RequestMethod.GET)])
    fun url(@PathVariable oj: String, @PathVariable pid: String): String {
        val type = OJType.valueOf(oj)
        val link = extojLinkRepo.findOne(type).problemLink
        if(type == OJType.UVA) {
            return String.format(link, uhuntAnalyser.numToPid(pid))
        } else if(type == OJType.CodeForces || type == OJType.Gym) {
            return String.format(link, pid.substring(0, pid.length-1), pid.substring(pid.length-1))
        }
        return String.format(link, pid)
    }
}