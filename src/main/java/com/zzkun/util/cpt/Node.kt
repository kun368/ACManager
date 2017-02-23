package com.zzkun.util.cpt

import java.util.*

enum class NodeType {
    LEAF, LIST
}

class Node(
        val deep: Int,
        val id: Int,
        val name: String,
        val type: NodeType) : Comparable<Node> {

    var son: ArrayList<Node> = ArrayList()

    override fun compareTo(other: Node): Int {
        return id.compareTo(other.id)
    }

    override fun toString(): String {
        return "Node(deep=$deep, id=$id, name='$name', type=$type, son=$son)"
    }

    fun allPids(): Set<Int> {
        val res = HashSet<Int>()
        val qu = ArrayDeque<Node>()
        qu.addLast(this)
        while (qu.isNotEmpty()) {
            val cur = qu.pollFirst()!!
            if (cur.type == NodeType.LEAF) {
                res.add(cur.name.toInt())
                continue
            }
            for (son in cur.son)
                qu.addLast(son)
        }
        return res
    }

    fun deepKSons(k: Int): List<Node> {
        val res = ArrayList<Node>()
        val qu = ArrayDeque<Node>()
        qu.addLast(this)
        while (qu.isNotEmpty()) {
            val cur = qu.pollFirst()!!
            if (cur.deep == this.deep + k) {
                res.add(cur)
                continue
            }
            for (son in cur.son)
                qu.addLast(son)
        }
        return res
    }
}
