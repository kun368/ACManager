package com.zzkun.service

import com.zzkun.dao.ContestRepo
import com.zzkun.model.Contest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Pattern

/**
 * Created by kun36 on 2016/12/29.
 */
@Service
open class ContestSearchService {

    companion object {
        private val logger = LoggerFactory.getLogger(ContestSearchService::class.java)
        private val splitPattern = Pattern.compile(";|,|，|；|\\s+")
    }

    @Autowired lateinit private var contestRepo : ContestRepo

    fun splitParms(str: String): List<String> {
        return str.split(splitPattern)
                .filter(String::isNotBlank)
                .map(String::trim)
    }

    fun find(qu: String): List<Contest> {
        val parms = splitParms(qu)
        val keys = HashMap<Int, String>()
        contestRepo.findAll().forEach {
            keys[it.id] = "${it.name}|${it.source}|${it.sourceDetail}|${it.sourceUrl}"
            if(it.id == 142)
                println(keys[it.id])
        }
        val ok = ArrayList<Int>()
        for ((key, value) in keys) {
            val flag = parms.all { value.contains(it, true) }
            if(flag)
                ok.add(key)
        }
        return ok.reversed()
                .map { contestRepo.findOne(it) }
    }
}
