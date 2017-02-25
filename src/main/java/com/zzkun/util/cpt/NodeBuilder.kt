package com.zzkun.util.cpt

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.util.*

/**
 * 规则：
 * 不含表头，4列，英文逗号分隔无空格，分别为：
 * id：当前节点id；根节点必为0，且出现在第0行；保证id唯一
 * pid：父节点id；根节点的父节点为0；父节点需出现在子节点之前
 * name：当前节点名字，如章节名；若为叶节点（题目）则为题目编号（int）
 * type：类型；大写；叶节点（题目）为LEAF，其余为LIST
 */
fun parseCSV(txt: List<String>): Node? {
    var root: Node? = null
    val id2Node = HashMap<Int, Node>()
    for (s in txt) {
        if(s.isBlank())
            continue

        val split = s.split(",")
        val id = split[0].trim().toInt()
        val pid = split[1].trim().toInt()
        val name = split[2].trim()
        val type = NodeType.valueOf(split[3].trim())

        val pdeep = id2Node[pid]?.deep ?:-1
        val cur = Node(pdeep + 1, id, name, type)

        id2Node[pid]?.son?.add(cur)
        id2Node[id] = cur
        if(id == 0)
            root = cur
    }
    return root
}

fun parseJson(str: String?): Node? {
    try {
        return jsonDFS(JSON.parseObject(str))
    }
    catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

private fun jsonDFS(json: JSONObject): Node? {
    val deep = json.getInteger("deep")
    val id = json.getInteger("id")
    val name = json.getString("name")
    val type = NodeType.valueOf(json.getString("type"))

    val res = Node(deep, id, name, type)
    if(type == NodeType.LEAF)
        return res
    val son = json.getJSONArray("son")
    if(son != null) {
        for (i in son.indices)
            res.son.add(jsonDFS(son.getJSONObject(i))!!)
    }
    return res
}
