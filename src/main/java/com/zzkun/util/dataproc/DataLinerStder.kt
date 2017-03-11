package com.zzkun.util.dataproc

/**
 * Created by Administrator on 2017/3/11 0011.
 */
fun std(datas: List<RawData>, limit: Int): DoubleArray {
    val res = DoubleArray(datas.size, {0.0})
    val best = datas
            .filter { it.isValid }
            .map { it.data }
            .max()
    if (best != null) {
        datas.indices
                .filter { datas[it].isValid }
                .forEach { res[it] = 1.0 - (best - datas[it].data) / limit }
    }
    for (i in res.indices) {
        if (!datas[i].isValid)
            continue
        if (res[i] < 0)
            res[i] = 0.0
        res[i] = 60.0 + 40.0 * res[i]
    }
    return res
}