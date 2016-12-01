package com.zzkun.controller.api

import com.alibaba.fastjson.JSONObject
import com.zzkun.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api/user")
class UserApi {

    @Autowired lateinit var userService: UserService

    @RequestMapping(value = "/{username}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun detail(@PathVariable username: String): String {
        val user = userService.getUserByUsername(username)
        val res = LinkedHashMap<String, Any?>()
        res["id"] = user.id
        res["username"] = user.username
        res["realName"] = user.realName
        res["uvaId"] = user.uvaId
        res["cfname"] = user.cfname
        res["vjname"] = user.vjname
        res["bcname"] = user.bcname
        res["hduName"] = user.hduName
        res["pojName"] = user.pojName
        res["major"] = user.major
        res["blogUrl"] = user.blogUrl
        res["type"] = user.type.toString()
        return JSONObject(res).toString()
    }
}