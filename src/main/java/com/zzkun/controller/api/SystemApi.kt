package com.zzkun.controller.api

import com.alibaba.fastjson.JSONArray
import com.zzkun.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Administrator on 2017/2/21 0021.
 */
@RestController
@RequestMapping("/api/system")
open class SystemApi(
        @Autowired private val systemService: SystemService) {

    @RequestMapping(value = ["/stateHistory"],
            method = [(RequestMethod.GET)],
            produces = ["text/html;charset=UTF-8"])
    fun stateHistory(): String {
        return JSONArray(systemService.stateHistory()).toJSONString()
    }
}
