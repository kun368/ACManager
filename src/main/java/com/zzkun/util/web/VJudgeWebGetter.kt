package com.zzkun.util.web

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by kun on 2016/9/29.
 */
@Component
open class VJudgeWebGetter {

    companion object {
        val logger = LoggerFactory.getLogger(VJudgeWebGetter::class.java)
    }

    private fun getUserACJsonDate(vjname: String, link: String): String {
        val url = String.format(link, vjname)
        val html = Jsoup.connect(url).timeout(7777).get().body()
        return html?.select("textarea[name=dataJson]")?.text() ?: ""
    }

    fun getUserACMap(vjname: String?, link: String): Map<String, List<String>> {
        if(vjname == null)
            return HashMap()
        logger.info("开始爬取vjudge用户${vjname}AC纪录")
        val acmap: JSONObject? = JSON.parseObject(getUserACJsonDate(vjname, link))?.getJSONObject("acRecords")
        val res = HashMap<String, List<String>>()
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