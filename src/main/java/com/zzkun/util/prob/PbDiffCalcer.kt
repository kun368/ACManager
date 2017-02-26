package com.zzkun.util.prob

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.OJType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Math.log
import java.util.*

/**
 * Created by Administrator on 2017/2/26 0026.
 */
@Component
open class PbDiffCalcer(
        @Autowired private val extOjPbInfoRepo: ExtOjPbInfoRepo,
        @Autowired private val userACPbRepo: UserACPbRepo) {

    companion object {
        private val DEFAULT_MAX = log(1000.0)
    }

    fun calcPbDiff(ojName: OJType, pbId: String): Double {
        val info = extOjPbInfoRepo.findByOjNameAndPid(ojName, pbId)
        val weAC = userACPbRepo.countByOjNameAndOjPbId(ojName, pbId)
        val res = (DEFAULT_MAX - log(1.0 + info.ac)) / log(1.7 + weAC)
        println(",$ojName,${info.ac},${weAC},${res}")
        return res
    }

    fun allPbDiff(): HashMap<String, Double> {
        val res = HashMap<String, Double>()
        val list = extOjPbInfoRepo.findAll()
        list.forEach {
            print(it.num)
            res["${it.num}@${it.ojName}"] = calcPbDiff(it.ojName, it.pid)
        }
        return res
    }
}