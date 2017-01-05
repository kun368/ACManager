package com.zzkun.util.web

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.scheme.Scheme
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.stereotype.Component

import java.io.IOException
import java.net.URL
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 网络抓取工具类
 * Created by kun36 on 2016/7/4.
 */
@Component
open class HttpUtil {

    /**
     * 重试机制强力读取Web内容
     */
    @Throws(IOException::class)
    fun readURL(url: String): String {
        var result: String? = null
        var exception: IOException? = null
        for (i in 0..3) {
            try {
                result = IOUtils.toString(URL(url), "utf8")
                if (result != null)
                    break
            } catch (e: IOException) {
                exception = e
            }
        }
        if (result == null)
            throw exception!!
        return result
    }

    fun readHttpsURL(url: String): String {
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
        val sr = ccm.getSchemeRegistry()
        sr.register(Scheme("https", 443, ssf))
        val httpget = HttpGet(url)
        val params = httpclient.getParams()
        httpget.setParams(params)
        val responseHandler = BasicResponseHandler()
        val responseBody: String
        responseBody = httpclient.execute(httpget, responseHandler)
        return responseBody
    }
}
