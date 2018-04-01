package com.zzkun.util.cpt

import com.alibaba.fastjson.JSON
import com.zzkun.dao.CptTreeRepo
import com.zzkun.dao.ExtOjPbInfoRepo
import com.zzkun.model.ExtOjPbInfo
import com.zzkun.model.OJType
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
        @Autowired private val cptTreeRepo: CptTreeRepo,
        @Autowired private val extOjPbInfoRepo: ExtOjPbInfoRepo) {

    companion object {
        private val logger = LoggerFactory.getLogger(NodeAnalyser::class.java)
    }

    fun getNode(treeId: Int): Node? {
        val date = cptTreeRepo.getOne(treeId)
        return parseJson(date?.rootNode)
    }

    fun getNode(treeId: Int, nodeId: Int): Node? {
        return getNode(treeId)?.findSon(nodeId)
    }

    fun saveRootNode(treeId: Int, node: Node?) {
        if (node == null)
            return
        val date = cptTreeRepo.getOne(treeId)
        date.rootNode = JSON.toJSONString(node)
        cptTreeRepo.save(date)
    }

    // 获取一个节点下的所有题目具体信息
    fun getNodePbInfos(treeId: Int, nodeId: Int): List<ExtOjPbInfo>? {
        val pids = getNode(treeId, nodeId)?.allPids() ?: return emptyList()
        // 按数据量采取不同数据库查询方式
        if (pids.size < 66) {
            return pids.map {
                val split = it.split("@")
                extOjPbInfoRepo.findByOjNameAndNum(OJType.valueOf(split[1]), split[0])
            }
        }
        else {
            val all = extOjPbInfoRepo.findAll()
            val map = HashMap<String, ExtOjPbInfo>(all.size)
            for (it in all) {
                map["${it.num}@${it.ojName}"] = it
            }
            return pids.mapNotNull { map[it] }
        }
    }

    // 删除某tree下的node
    fun deleteSon(treeId: Int, nodeId: Int): Boolean {
        try {
            val newNode = getNode(treeId)?.deleteSon(nodeId)
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
            val rootNode = getNode(treeId)
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
            val rootNode = getNode(treeId)
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