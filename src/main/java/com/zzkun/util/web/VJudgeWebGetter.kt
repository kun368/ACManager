package com.zzkun.util.web

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Created by kun on 2016/9/29.
 */
@Component
open class VJudgeWebGetter {

    companion object {
        val logger = LoggerFactory.getLogger(VJudgeWebGetter::class.java)
    }

    private fun getUserACJsonDate(vjname: String): String {
        val html = Jsoup.connect("http://vjudge.net/user/${vjname}").timeout(7777).get().body()
        return html?.select("textarea[name=dataJson]")?.text() ?: ""
    }

    fun getUserACMap(vjname: String): Map<String, List<String>> {
        val acmap: JSONObject? = JSON.parseObject(getUserACJsonDate(vjname))?.getJSONObject("acRecords")
        val res = mutableMapOf<String, List<String>>()
        if(acmap != null) {
            for(key in acmap.keys) {
                val pbList = arrayListOf<String>()
                val pbs = acmap.getJSONArray(key)
                for(i in pbs.indices)
                    pbList.add(pbs.getString(i))
                res[key] = pbList
            }
        }
        return res
    }
}