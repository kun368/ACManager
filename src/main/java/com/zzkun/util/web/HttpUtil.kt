@file:Suppress("DEPRECATION")

package com.zzkun.util.web

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.scheme.Scheme
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.URL
import java.security.cert.X509Certificate
import java.util.concurrent.Callable
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 网页抓取工具类
 * 提供Java原生抓取，JSoup，Https抓取
 * Created by kun36 on 2016/7/4.
 */
@Component
open class HttpUtil {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HttpUtil::class.java)
    }

    /**
     * 重复执行任务并返回值或抛出异常
     */
    fun <T> repeatDo(task: Callable<T>, time: Int): T {
        var result: T? = null
        var exception: Exception? = null
        for (i in 1..time) {
            try {
                result = task.call()
                if (result != null)
                    break
            } catch (e: Exception) {
                exception = e
            }
        }
        if (result == null)
            throw exception!!
        return result
    }

    fun readURL(url: String): String {
        return repeatDo(Callable {
            logger.info("[*] readURL: ${url}")
            IOUtils.toString(URL(url), "utf8")
        }, 5)
    }

    fun readJsoupURL(url: String): Document {
        return repeatDo(Callable {
            logger.info("[*] readJsoupURL: ${url}")
            Jsoup.connect(url).timeout(8888).get()
        }, 5)
    }

    fun readHttpsURL(url: String): String {
        return repeatDo(Callable {
            logger.info("[*] readHttpsURL: ${url}")
            val httpclient = DefaultHttpClient()
            val ctx = SSLContext.getInstance("SSL")
            val tm = object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                }
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                }
                override fun getAcceptedIssuers(): Array<out X509Certificate>? {
                    return null
                }
            }
            ctx.init(null, arrayOf<TrustManager>(tm), null)
            val ssf = org.apache.http.conn.ssl.SSLSocketFactory(ctx)
            val ccm = httpclient.getConnectionManager()
            val sr = ccm.schemeRegistry
            sr.register(Scheme("https", 443, ssf))
            val httpget = HttpGet(url)
            val params = httpclient.getParams()
            httpget.params = params
            val responseHandler = BasicResponseHandler()
            httpclient.execute(httpget, responseHandler)
        }, 5)
    }
}
