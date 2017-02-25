package com.zzkun.util.cpt

import java.util.*

enum class NodeType {
    LEAF, LIST
}

class Node(
        val deep: Int,
        val id: Int,
        var name: String,
        val type: NodeType) : Comparable<Node> {

    var son: ArrayList<Node> = ArrayList()

    override fun compareTo(other: Node): Int {
        return id.compareTo(other.id)
    }

    override fun toString(): String {
        return "Node(deep=$deep, id=$id, name='$name', type=$type, son=$son)"
    }

    fun allPids(): Set<String> {
        val res = HashSet<String>()
        val qu = ArrayDeque<Node>()
        qu.addLast(this)
        while (qu.isNotEmpty()) {
            val cur = qu.pollFirst()!!
            if (cur.type == NodeType.LEAF) {
                res.add(cur.name)
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

    fun findSon(id: Int): Node? {
        val qu = ArrayDeque<Node>()
        qu.addLast(this)
        while (qu.isNotEmpty()) {
            val cur = qu.pollFirst()!!
            if (cur.id == id)
                return cur
            for (son in cur.son)
                qu.addLast(son)
        }
        return null
    }

    fun deleteSon(id: Int): Node {
        val qu = ArrayDeque<Node>()
        qu.addLast(this)
        val deleted = false
        while (!deleted && qu.isNotEmpty()) {
            val cur = qu.pollFirst()!!
            for (son in cur.son) {
                if (son.id == id) {
                    cur.son.remove(son)
                    break
                }
                qu.addLast(son)
            }
        }
        return this
    }
}
