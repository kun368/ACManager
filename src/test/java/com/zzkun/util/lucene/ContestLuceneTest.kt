package com.zzkun.util.lucene

import com.zzkun.dao.ContestRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by kun on 16-12-7.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:springmvc-servlet.xml"))
class ContestLuceneTest {

    @Autowired lateinit var contestLucene: ContestLucene
    @Autowired lateinit var contestRepo: ContestRepo

    @Test
    fun init() {
        val list = contestRepo.findAll()
        contestLucene.init(list)
        contestLucene.init(list)
        contestLucene.init(list)
    }

    @Test
    fun query() {
        val res = contestLucene.query("shanghai")
        for (re in res) {
            println(contestRepo.findOne(re).name)
        }
    }
}