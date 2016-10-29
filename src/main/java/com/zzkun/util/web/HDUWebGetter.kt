package com.zzkun.util.web

import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kun on 2016/10/14.
 */
@Component
open class HDUWebGetter {

    companion object {
        val logger = LoggerFactory.getLogger(HDUWebGetter::class.java)
    }

    fun userACPbs(hduName: String?): List<String> {
        if(hduName == null)
            return ArrayList()
        logger.info("开始获取hdu用户${hduName}AC题目")
        val url = "http://acm.hdu.edu.cn/userstatus.php?user=$hduName"
        val body = Jsoup.connect(url).timeout(7777).get().body().toString()
        val res = sortedSetOf<String>()
        val patten = Pattern.compile("""p\(([\d]*),([\d]*),([\d]*)\);""")
        val matcher = patten.matcher(body)
        while(matcher.find()) {
            val id = matcher.group(1)
            val ac = matcher.group(2).toInt()
            if(ac > 0) res.add(id)
        }
        return res.toList()
    }

    private fun pbInfomation(pbid: String): ExtOjPbInfo? {
        try {
            val url: String = "http://acm.hdu.edu.cn/statistic.php?pid=$pbid"
            val body = Jsoup.connect(url).timeout(7777).get().body()
            val table = body?.select("table[class=table_header]")?.get(0)
            val status = table?.child(0)?.children()!!
            val res = ExtOjPbInfo()
            res.ojName = OJType.HDU
            res.pid = pbid
            res.num = pbid
            for(i in status) {
                val name = i.child(0).text()
                val cnt = i.child(1).text().toInt()
                when(name) {
                    "Total Submission(s)" -> res.totSubmit = cnt
                    "User Accepted" -> res.dacu = cnt
                    "Accepted" -> res.ac = cnt
                    "Presentation Error" -> res.pe = cnt
                    "Wrong Answer" -> res.wa = cnt
                    "Runtime Error" -> res.re = cnt
                    "Compilation Error" -> res.ce = cnt
                    "Time Limit Exceeded" -> res.tle = cnt
                    "Memory Limit Exceeded" -> res.mle = cnt
                    "Output Limit Exceeded" -> res.ole = cnt
                    "System Error" -> res.sube = cnt
                }
            }
            return res
        } catch(e: Exception) {
            return null
        }
    }

    fun allPbInfo(): List<ExtOjPbInfo> {
        logger.info("开始获取HDU所有题目数据...")
        val res = arrayListOf<ExtOjPbInfo>()
        for(i in 1000..999999) {
            val info = pbInfomation(i.toString())
            if(info != null) res.add(info)
            else break
            if(i % 100 == 0) logger.info("已经获取到：$i")
        }
        return res
    }
}
//
//fun main(args: Array<String>) {
//    HDUWebGetter().allPbInfo()
//}
