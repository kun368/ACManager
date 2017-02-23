package com.zzkun.util.cpt

import java.util.*

/**
 * Created by Administrator on 2017/2/23 0023.
 */
interface NodeBuilder {
    fun parse(txt: List<String>): Node?
}

/**
 * 规则：
 * 不含表头，4列，英文逗号分隔无空格，分别为：
 * id：当前节点id；根节点必为0，且出现在第0行；保证id唯一
 * pid：父节点id；根节点的父节点为0；父节点需出现在子节点之前
 * name：当前节点名字，如章节名；若为叶节点（题目）则为题目编号（int）
 * type：类型；大写；叶节点（题目）为LEAF，其余为LIST
 */
class CSVNodeBuilder : NodeBuilder {

    private var root: Node? = null

    override fun parse(txt: List<String>): Node? {
        var root: Node? = null
        val id2Node = HashMap<Int, Node>()
        for (s in txt) {
            if(s.isBlank())
                continue

            val split = s.split(",")
            val id = split[0].toInt()
            val pid = split[1].toInt()
            val name = split[2].trim()
            val type = NodeType.valueOf(split[3])

            val pdeep = id2Node[pid]?.deep ?:-1
            val cur = Node(pdeep + 1, id, name, type)

            id2Node[pid]?.son?.add(cur)
            id2Node[id] = cur
            if(id == 0)
                root = cur
        }
        return root
    }
}

//fun main(args: Array<String>) {
//    val txt = """0,0,UVA,LIST
//1,0,入门经典,LIST
//2,1,第一章,LIST
//3,2,10086,LEAF
//4,2,10010,LEAF
//5,1,第二章,LIST
//6,5,10000,LEAF
//7,0,训练指南,LIST"""
//
//    val hehe = txt.split("\n").toList();
//    println(hehe)
//    val rootNode: List<Node> = CSVNodeBuilder().parse(hehe)?.deepKSons(0)!!
//    for (node in rootNode) {
//        println(node.name + " " + node.allPids())
//        println(node.toJsonStr())
//    }
//}