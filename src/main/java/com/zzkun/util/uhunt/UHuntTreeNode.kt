package com.zzkun.util.uhunt

import java.io.Serializable
import java.util.*

/**
 * Created by kun on 2016/7/5.
 */
class UHuntTreeNode : Comparable<UHuntTreeNode>, Serializable {

    var deep: Int = 0
    var id: Int = 0
    var name: String = ""
    var type: String = ""
    var son: List<UHuntTreeNode> = ArrayList()
    var pids: List<Int> = ArrayList()

    constructor() {
    }

    constructor(deep: Int, id: Int, name: String, type: String) {
        this.deep = deep
        this.id = id
        this.name = name
        this.type = type
    }



    override fun compareTo(other: UHuntTreeNode): Int {
        return Integer.compare(id, other.id)
    }

    override fun toString(): String {
        return "UHuntTreeNode(deep=$deep, id=$id, name='$name', type='$type', son=${son.size}, pids=${pids.size})"
    }

}
