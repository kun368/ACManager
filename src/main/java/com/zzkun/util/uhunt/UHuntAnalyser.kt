package com.zzkun.util.uhunt

import com.google.common.collect.HashBiMap
import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.model.OJType
import com.zzkun.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * uhunt数据分析工具类，依赖于dao
 * Created by kun on 2016/7/5.
 */
@Component
open class UHuntAnalyser {

    companion object {
        private val pid2Num = HashBiMap.create<String, String>(5120)
    }

    @Autowired lateinit var extOjPbInfoRepo: ExtOjPbInfoRepo

    /**
     * 分析用户各个节点做题数量
     * 时间复杂度：O(所有节点总题数)
     * @param acPbs 用户所有AC题目uvanum List
     * @param nodes 节点信息，可通过ChapterManager获得
     * @return 用户各个节点做题量
     */
    fun calcNodesCount(acPbs: Set<Int>,
                       nodes: List<UHuntTreeNode>): List<Int> {
        val res = ArrayList<Int>()
        val has = HashSet<Int>() //去重set
        nodes.forEach {
            var cnt = 0
            it.pids.filter { acPbs.contains(it) && !has.contains(it) }.forEach { ++cnt; has.add(it) }
            res.add(cnt)
        }
        return res
    }

    fun userStatistic(users: List<User>,
                      nodes: List<UHuntTreeNode>): Map<Int, List<Int>> {
        val res = HashMap<Int, List<Int>>()
        users.forEach {
            val acSet = it.acPbList
                    .filter { it.ojName == OJType.UVA }
                    .map { it.ojPbId.toInt() }
                    .toHashSet()
            val list = calcNodesCount(acSet, nodes)
            res[it.id] = list
        }
        return res
    }

    // uvapid -> uva题号
    fun pidToNum(pid: String): String? {
        if (!pid2Num.containsKey(pid)) {
            pid2Num.put(pid, extOjPbInfoRepo.findByOjNameAndPid(OJType.UVA, pid).num)
        }
        return pid2Num[pid]
    }

    fun numToPid(num: String): String? {
        if(!pid2Num.containsValue(num)) {
            pid2Num.put(extOjPbInfoRepo.findByOjNameAndNum(OJType.UVA, num).pid, num)
        }
        return pid2Num.inverse()[num]
    }
}
