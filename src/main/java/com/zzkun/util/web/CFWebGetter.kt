package com.zzkun.util.web

import com.alibaba.fastjson.JSON
import com.zzkun.model.CFUserInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
open class CFWebGetter {

    companion object {
        private val logger = LoggerFactory.getLogger(CFWebGetter::class.java)
    }

    @Autowired lateinit var httpUtil: HttpUtil

    fun getUserInfos(cfNameList: List<String>): List<CFUserInfo>? {
        val joiner = StringJoiner(";", "http://codeforces.com/api/user.info?handles=", "")
        cfNameList.forEach { joiner.add(it) }
        logger.info("收到获取CF用户信息请求，url：{}", joiner.toString())
        try {
            val infoList = ArrayList<CFUserInfo>()
            val str = httpUtil.readURL(joiner.toString())
            val jsonObject = JSON.parseObject(str)
            if ("OK" != jsonObject.getString("status"))
                return null
            val result = jsonObject.getJSONArray("result")
            for (i in result.indices) {
                val curUser = result.getJSONObject(i)
                val info = CFUserInfo(
                        curUser.getString("handle"),
                        curUser.getInteger("rating"),
                        curUser.getInteger("maxRating"),
                        curUser.getString("rank"),
                        curUser.getString("maxRank"))
                infoList.add(info)
            }
            return infoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getUserInfos2(cfNameList: List<String>): List<CFUserInfo> {
        val url = "http://codeforces.com/api/user.rating?handle="
        val res = ArrayList<CFUserInfo>()
        for(it in cfNameList) {
            Thread.sleep(300)
            try {
                val str = httpUtil.readURL(url + it)
                val jsonObject = JSON.parseObject(str)
                if ("OK" != jsonObject.getString("status"))
                    continue
                val result = jsonObject.getJSONArray("result")
                val last = result.getJSONObject(result.size - 1)
                if(last != null) {
                    val info = CFUserInfo(last.getString("handle"), last.getInteger("newRating"), null, last.getString("rank"), null)
                    res.add(info)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        return res
    }

//    fun userACPbs(cfName: String?, link: String): List<String> {
//        if(cfName == null)
//            return ArrayList()
//        logger.info("开始获取coderforces用户${cfName}AC题目")
//        val url = String.format(link, cfName)
//        val body = Jsoup.connect(url).timeout(7777).get().body().toString()
//        val res = sortedSetOf<String>()
//
//    }
}
