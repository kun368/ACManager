package com.zzkun.service

import com.zzkun.dao.ContestRepo
import com.zzkun.model.Contest
import com.zzkun.util.algorithm.KMPCalc
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
        val res = ArrayList<String>()
        for (s in str.split(splitPattern)) {
            if(s.isNotBlank())
                res.add(s.trim())
        }
        return res
    }

    fun find(qu: String): List<Contest> {
        val parms = splitParms(qu)
        val keys = HashMap<Int, String>()
        contestRepo.findAll().forEach {
            keys[it.id] = "${it.name}|${it.source}|${it.sourceDetail}|${it.sourceUrl}"
        }
        val weights = HashMap<Int, Int>()
        for (parm in parms) {
            KMPCalc.init(parm)
            for (key in keys) {
                val plus = KMPCalc.count(key.value)
                if(plus != 0)
                    weights[key.key] = weights[key.key]?:0 + plus
            }
        }
        return weights
                .map { it.key }
                .sortedByDescending { weights[it] }
                .map { contestRepo.findOne(it) }
    }
}
