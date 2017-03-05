package com.zzkun.controller.api

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zzkun.model.User
import com.zzkun.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*


fun User.toJson(): JSONObject {
    val user = this
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
    res["typeChs"] = user.type.toShortStr()
    return JSONObject(res)
}

@RestController
@RequestMapping("/api/user")
class UserApi(
        @Autowired val userService: UserService) {

    @RequestMapping(value = "/{username}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun detail(@PathVariable username: String): String {
        return userService.getUserByUsername(username).toJson().toString()
    }

    @RequestMapping(value = "/{userId}/detailById",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun detailById(@PathVariable userId: Int): String {
        return userService.getUserById(userId).toJson().toString()
    }

    @RequestMapping(value = "/normalUsers",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun normalUsers(): String {
        val res = JSONArray()
        userService.allNormalNotNullUsers().forEach {
            res.add(it.toJson())
        }
        return res.toString()
    }
}