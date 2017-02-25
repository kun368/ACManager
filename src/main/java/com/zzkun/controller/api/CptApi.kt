package com.zzkun.controller.api

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zzkun.dao.CptTreeRepo
import com.zzkun.model.User
import com.zzkun.util.cpt.Node
import com.zzkun.util.cpt.NodeAnalyser
import com.zzkun.util.cpt.NodeType
import com.zzkun.util.cpt.parseJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Created by Administrator on 2017/2/24 0024.
 */
@RestController
@RequestMapping("/api/cpt")
open class CptApi(
        @Autowired private val nodeAnalyser: NodeAnalyser,
        @Autowired private val cptTreeRepo: CptTreeRepo) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CptApi::class.java)
    }

    @RequestMapping(value = "/{cptId}/{nodeId}/statistic",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun cptNodeStatistic(@PathVariable cptId: Int,
                         @PathVariable nodeId: Int): String {
        val jsonStr = cptTreeRepo.findOne(cptId)?.rootNode
        val rootNode = parseJson(jsonStr)

        return ""
    }

    @RequestMapping(value = "/{cptId}/ztreestr",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun zTreeStr(@PathVariable cptId: Int):String {
        val jsonStr = cptTreeRepo.findOne(cptId)?.rootNode
        val rootNode = parseJson(jsonStr)

        val res = JSONArray()
        if (rootNode != null) {
            val qu = ArrayDeque<Pair<Node, Int>>()
            qu.addLast(Pair(rootNode, 0))
            while (qu.isNotEmpty()) {
                val cur = qu.pollFirst()!!
                val map = HashMap<String, Any>(3)
                map["id"] = cur.first.id
                map["pId"] = cur.second
                map["isParent"] = cur.first.type == NodeType.LIST
                map["name"] = cur.first.name
                res.add(JSONObject(map))
                for (i in cur.first.son)
                    qu.addLast(Pair(i, cur.first.id))
            }
        }
        return res.toJSONString()
    }

    @RequestMapping(value = "/{treeID}/delete/{nodeID}",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun deleteSon(@PathVariable treeID: Int,
                  @PathVariable nodeID: Int,
                  @SessionAttribute(required = false) user: User?): String {
        logger.info("删除节点：${treeID} - ${nodeID}")
        if (user == null || !user.isAdmin) {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "没有权限！"))
        }
        if (nodeAnalyser.deleteSon(treeID, nodeID)) {
            return JSON.toJSONString(mapOf("ok" to "true", "status" to "操作成功！"))
        } else {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "操作有误，情重试！"))
        }
    }

    @RequestMapping(value = "/{treeID}/rename/{nodeID}/{newName}",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun renameNode(@PathVariable treeID: Int,
                   @PathVariable nodeID: Int,
                   @PathVariable newName: String,
                   @SessionAttribute(required = false) user: User?): String {
        logger.info("重命名节点：${treeID} - ${nodeID} - ${newName}")
        if (user == null || !user.isAdmin) {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "没有权限！"))
        }
        if (nodeAnalyser.renameSon(treeID, nodeID, newName)) {
            return JSON.toJSONString(mapOf("ok" to "true", "status" to "操作成功！"))
        } else {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "操作有误，情重试！"))
        }
    }


    @RequestMapping(value = "/{treeID}/addNode",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun addNode(@PathVariable treeID: Int,
                @RequestParam id: Int,
                @RequestParam pId: Int,
                @RequestParam isParent: Boolean,
                @RequestParam name: String,
                @SessionAttribute(required = false) user: User?): String {
        logger.info("添加节点：treeID = [${treeID}], id = [${id}], pId = [${pId}], isParent = [${isParent}], name = [${name}], user = [${user?.username}]")
        if (user == null || !user.isAdmin) {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "没有权限！"))
        }
        if (nodeAnalyser.addSon(treeID, pId, id, isParent, name)) {
            return JSON.toJSONString(mapOf("ok" to "true", "status" to "操作成功！"))
        } else {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "操作有误，情重试！"))
        }
    }
}