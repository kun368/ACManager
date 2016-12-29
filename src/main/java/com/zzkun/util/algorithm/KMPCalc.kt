package com.zzkun.util.algorithm


/**
 * Created by kun36 on 2016/12/29.
 */
object KMPCalc {

    private var fail = IntArray(0)
    private var len = 0
    private var pat = ""

    fun init(p: String) {
        if(p.isEmpty()) {
            len = 0
            return
        }
        len = p.length
        pat = p.toLowerCase() + (0).toChar()

        fail = IntArray(len + 1)
        fail[0] = 0
        fail[1] = 0
        for(i in 1..len-1) {
            var j = fail[i]
            while(j != 0 && pat[i] != pat[j])
                j = fail[j]
            fail[i+1] = if(pat[i] == pat[j]) j + 1 else 0
        }
    }

    fun count(qu: String): Int {
        if(qu.isEmpty() || len == 0)
            return 0
        val t = qu.toLowerCase()
        var cnt = 0
        var j = 0
        for(i in t.indices) {
            while(j != 0 && pat[j] != t[i])
                j = fail[j]
            if(pat[j] == t[i]) ++j
            if(j == len) cnt++
        }
        return cnt
    }
}
//
//fun main(args: Array<String>) {
//    KMPCalc.init("aBc");
//    println(KMPCalc.count(""));
//    println(KMPCalc.count("aba"));
//    println(KMPCalc.count("acccabcfdsa"));
//    println(KMPCalc.count("fdsABcfdsabferabcwq"));
//}