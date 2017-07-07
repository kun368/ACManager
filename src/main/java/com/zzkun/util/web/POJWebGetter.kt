package com.zzkun.util.web

import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kun on 2016/10/15.
 */
@Component
open class POJWebGetter {

    companion object {
        val logger = LoggerFactory.getLogger(POJWebGetter::class.java)
    }

    @Autowired lateinit var httpUtil: HttpUtil

    fun userACPbs(pojName: String?, link: String): List<String> {
        if(pojName == null)
            return ArrayList()
        val url = String.format(link, pojName)
//        logger.info("开始获取poj用户${pojName}AC题目:$url")
        val body = httpUtil.readJsoupURL(url).body().toString()
        val patten = Pattern.compile("""p\(([\d]+)\)""")
        val matcher = patten.matcher(body)
        val res = sortedSetOf<String>()
        while(matcher.find())
            res.add(matcher.group(1))
        logger.info("获取到poj用户${pojName}的${res.size}条纪录")
        return res.toList()
    }

    fun pbInfomation(pbId: String, link: String): ExtOjPbInfo? {
        try {
            val url = String.format(link, pbId)
            val body = httpUtil.readJsoupURL(url).body().toString()
            val res = ExtOjPbInfo()
            res.pid = pbId
            res.num = pbId
            res.ojName = OJType.POJ
            var mattcher = Pattern.compile(""",([\d]+),'status""").matcher(body)
            if(mattcher.find()) {
                res.dacu = mattcher.group(1).toInt()
            }
            mattcher = Pattern.compile("""sa\[1\]\[\d+\]='([A-Za-z\s]+)';\ssa\[0\]\[\d+\]=([\d]+);""").matcher(body)
            while(mattcher.find()) {
                val name = mattcher.group(1)
                val cnt = mattcher.group(2).toInt()
                when(name) {
                    "Accepted" -> res.ac = cnt
                    "Presentation Error" -> res.pe = cnt
                    "Time Limit Exceeded" -> res.tle = cnt
                    "Memory Limit Exceeded" -> res.mle = cnt
                    "Wrong Answer" -> res.wa = cnt
                    "Runtime Error" -> res.re = cnt
                    "Output Limit Exceeded" -> res.ole = cnt
                    "Compile Error" -> res.ce = cnt
                    "System Error" -> res.sube = cnt
                    "Waiting" -> res.inq += cnt
                    "Compiling" -> res.inq += cnt
                }
            }
            res.totSubmit = res.calcTotSubmits()
            return if(res.totSubmit != 0) res else null
        } catch(e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun allPbInfo(link: String): List<ExtOjPbInfo> {
        logger.info("开始获取POJ所有题目数据...")
        val res = arrayListOf<ExtOjPbInfo>()
        for(i in 1000..999999) {
            val info = pbInfomation(i.toString(), link)
            if(info != null)
                res.add(info)
            else break
        }
        return res
    }
}
