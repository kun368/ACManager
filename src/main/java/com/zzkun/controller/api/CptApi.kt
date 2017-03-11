package com.zzkun.controller.api

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.github.salomonbrys.kotson.jsonObject
import com.zzkun.dao.CptTreeRepo
import com.zzkun.model.User
import com.zzkun.service.UserService
import com.zzkun.util.cpt.Node
import com.zzkun.util.cpt.NodeAnalyser
import com.zzkun.util.cpt.NodeType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.set

/**
 * Created by Administrator on 2017/2/24 0024.
 */
@RestController
@RequestMapping("/api/cpt")
open class CptApi(
        @Autowired private val nodeAnalyser: NodeAnalyser,
        @Autowired private val cptTreeRepo: CptTreeRepo,
        @Autowired private val userService: UserService) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CptApi::class.java)
    }

    ///////////   章节树增删改差

    @RequestMapping(value = "/seeNode/{cptId}/{nodeId}",
                    method = arrayOf(RequestMethod.GET),
                    produces = arrayOf("text/html;charset=UTF-8"))
    fun seeNode(@PathVariable cptId: Int,
                @PathVariable nodeId: Int): String {
        val rootNode = nodeAnalyser.getNode(cptId)!!
        val curNode = rootNode.findSon(nodeId)!!
        val sons = JSONArray()
        curNode.son.forEach {
            sons.add(mapOf("id" to it.id, "name" to it.name))
        }
        return JSONObject(mapOf("id" to curNode.id, "name" to curNode.name, "sons" to sons)).toJSONString()
    }

    @RequestMapping(value = "/{cptId}/ztreestr",
                    method = arrayOf(RequestMethod.GET),
                    produces = arrayOf("text/html;charset=UTF-8"))
    fun zTreeStr(@PathVariable cptId: Int):String {
        val rootNode = nodeAnalyser.getNode(cptId)
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
            return jsonObject(
                    "ok" to "false",
                    "status" to "没有权限！").toString()
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
            return jsonObject(
                    "ok" to "false",
                    "status" to "没有权限！").toString()
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
            return jsonObject(
                    "ok" to "false",
                    "status" to "没有权限！").toString()
        }
        if (nodeAnalyser.addSon(treeID, pId, id, isParent, name)) {
            return JSON.toJSONString(mapOf("ok" to "true", "status" to "操作成功！"))
        } else {
            return JSON.toJSONString(mapOf("ok" to "false", "status" to "操作有误，情重试！"))
        }
    }

    //////// 统计数据

    @RequestMapping(value = "/statistic/{cptId}/{nodeId}",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("text/html;charset=UTF-8"))
    fun statistic(@PathVariable cptId: Int,
                  @PathVariable nodeId: Int): String {
        val rootNode = nodeAnalyser.getNode(cptId)!!
        val curNode = rootNode.findSon(nodeId)!!
        val list = ArrayList<Node>()
        list.add(curNode)
        list.addAll(curNode.son)

        val users = userService.allNormalNotNullUsers()

        val res = ArrayList<HashMap<String, Any>>()
        for (user in users) {
            val map = LinkedHashMap<String, Any>()
            map["userId"] = user.id
            map["userName"] = user.username
            map["userReal"] = user.realName
            map["userMajor"] = user.major
            val ac = user.acPbList.map { "${it.ojPbId}@${it.ojName}" }.toHashSet()
            for (node in list) {
                map["acCount${node.id}"] = node.allPids().intersect(ac).size
                map["sumProb${node.id}"] = node.allPids().size
            }
            res.add(map)
        }
        return JSON.toJSONString(mapOf("data" to res))
    }
}