package com.zzkun.util.prob

import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.model.OJType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by Administrator on 2017/2/26 0026.
 */
@Component
open class PbDiffCalcer(
        @Autowired private val extOjPbInfoRepo: ExtOjPbInfoRepo,
        @Autowired private val userACPbRepo: UserACPbRepo) {

    fun calcPbDiff(ojName: OJType, pbId: String, pbNum: String): Double {
//        val info = extOjPbInfoRepo.findByOjNameAndPid(ojName, pbId)
//        val weAC = userACPbRepo.countByOjNameAndOjPbId(ojName, pbNum)
//        val res = (DEFAULT_MAX - log(1.0 + info.dacu)) / log(1.7 + weAC)
//        println("${ojName},${pbId},${pbNum},${info.dacu},${info.ac},${weAC}")
//        return res
        return 1.0;
    }

    fun allPbDiff(): HashMap<String, Double> {
        val res = HashMap<String, Double>()
        val list = extOjPbInfoRepo.findAll()
        list.forEach {
            res["${it.num}@${it.ojName}"] = calcPbDiff(it.ojName, it.pid, it.num)
        }
        return res
    }
}