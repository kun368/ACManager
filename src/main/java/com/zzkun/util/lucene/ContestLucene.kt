package com.zzkun.util.lucene

import com.zzkun.model.Contest
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by kun on 16-12-7.
 */
@Component
open class ContestLucene {

    companion object {
        private val logger = LoggerFactory.getLogger(ContestLucene::class.java)
    }

    private var analyzer: Analyzer? = null
    private var dict: Directory? = null

    fun init(contests: List<Contest>) {
        analyzer = StandardAnalyzer()
        dict?.close()
        dict = RAMDirectory()
        val iwriter = IndexWriter(dict, IndexWriterConfig(analyzer))
        for (contest in contests) {
            val doc = Document()
            doc.add(Field("id", contest.id.toString(), StringField.TYPE_STORED))
            val name = "${contest.name} | ${contest.source} | ${contest.sourceDetail} | ${contest.sourceUrl}"
            doc.add(Field("ok", name, TextField.TYPE_STORED))
            iwriter.addDocument(doc)
        }
        iwriter.close()
    }

    fun query(str: String): List<Int> {
        val list = ArrayList<Int>()
        if(analyzer == null || dict == null)
            return list
        val isearcher = IndexSearcher(DirectoryReader.open(dict))
        val parser = QueryParser("ok", analyzer)
        val query = parser.parse(str)
        val res = isearcher.search(query, 12)?.scoreDocs
        res?.forEach {
            val id = isearcher.doc(it.doc)?.get("id")?.toInt()
            if(id != null) list.add(id)
        }
        return list
    }
}