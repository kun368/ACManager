package com.zzkun.util.cpt

import com.alibaba.fastjson.JSON
import com.zzkun.dao.CptTreeRepo
import com.zzkun.model.OJType
import com.zzkun.model.User
import com.zzkun.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by Administrator on 2017/2/23 0023.
 */

/**
 * 某一用户在某节点下的统计信息
 */
data class UserNodeStat(
    val nodeId: Int,
    val acCount: Int,
    val sumScore: Double
)


@Component
open class NodeAnalyser(
        @Autowired private val userService: UserService,
        @Autowired private val cptTreeRepo: CptTreeRepo) {

    companion object {
        private val logger = LoggerFactory.getLogger(NodeAnalyser::class.java)
    }

//    // 某一节点所有用户AC统计
//    fun nodeStatistic(node: Node): TreeMap<User, Int> {
//        val res = TreeMap<User, Int>()
//        val pids = node.allPids()
//        userService.allNormalNotNullUsers().forEach {
//            val sz = it.acPbList
//                    .map { "${it.ojPbId}@${it.ojName}" }
//                    .toHashSet()
//                    .intersect(pids)
//                    .size
//            res[it] = sz
//        }
//        return res
//    }

    fun userNodeStatistic(user: User, node: Node): List<UserNodeStat> {
        val acSet = user.acPbList.map { "${it.ojPbId}@${it.ojName}" } .toHashSet()
        val sonNodes = node.deepKSons(1)
        val res = ArrayList<UserNodeStat>()
        res.add(UserNodeStat(node.id, acSet.intersect(node.allPids()).size, 0.0))
        sonNodes.forEach {
            res.add(UserNodeStat(it.id, acSet.intersect(it.allPids()).size, 0.0))
        }
        return res
    }

    fun getRootNode(treeId: Int): Node? {
        val date = cptTreeRepo.findOne(treeId)
        return parseJson(date?.rootNode)
    }

    fun saveRootNode(treeId: Int, node: Node?) {
        if (node == null)
            return
        val date = cptTreeRepo.findOne(treeId)
        date.rootNode = JSON.toJSONString(node)
        cptTreeRepo.save(date)
    }

    // 删除某tree下的node
    fun deleteSon(treeId: Int, nodeId: Int): Boolean {
        try {
            val newNode = getRootNode(treeId)?.deleteSon(nodeId)
            saveRootNode(treeId, newNode)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    // 判断节点名称是否合法
    fun valideNodeName(nodeType: NodeType, str: String): Boolean {
        if (nodeType == NodeType.LIST) {
            return !str.contains("@");
        }
        if (nodeType == NodeType.LEAF) {
            val res = "^(\\w+)@(\\w+)$".toRegex()
                        .matchEntire(str)?.groupValues ?: return false
            try {
                OJType.valueOf(res[2])
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
        return false
    }

    // 重命名某tree下的node
    fun renameSon(treeId: Int, nodeId: Int, newName: String): Boolean {
        try {
            val rootNode = getRootNode(treeId)
            val sonNode = rootNode?.findSon(nodeId)
            if (!valideNodeName(sonNode!!.type, newName))
                return false
            sonNode.name = newName
            saveRootNode(treeId, rootNode)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // 在某tree下添加node
    fun addSon(treeId: Int, nodeId: Int, newId: Int, isParent: Boolean, name: String): Boolean {
        try {
            val rootNode = getRootNode(treeId)
            val sonNode =  rootNode?.findSon(nodeId)!!
            val newNode = Node(sonNode.deep + 1, newId, name,
                    if (isParent) NodeType.LIST else NodeType.LEAF)
            if (!valideNodeName(newNode.type, name))
                return false
            sonNode.son.add(newNode)
            saveRootNode(treeId, rootNode)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}