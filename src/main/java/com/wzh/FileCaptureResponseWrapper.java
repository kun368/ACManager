package com.wzh;

/**
 * Created by wzh on 2017/5/24.
 */

import java.io.CharArrayWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FileCaptureResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter output;
    private HttpServletResponse response;

    public String toString() {
        return output.toString();
    }

    public FileCaptureResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
        output = new CharArrayWriter();
        // 这个是包装PrintWriter的，让所有结果通过这个PrintWriter写入到bufferedWriter中
    }

    // 覆写getWriter()
    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }

    /**
     * 获取原始的HTML页面内容。
     *
     * @return
     */
    public String getResult() {
        return output.toString();
    }

    public void writeFile(String fileName) throws IOException {
        /* FileWriter fw = new FileWriter(fileName,"utf-8");
        fw.write( output.toCharArray(),"utf-8" );
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new
        FileOutputStream(fileName),"utf-8"));*/
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(output.toCharArray());
        osw.close();
    }

    public void writeResponse() throws IOException {
        PrintWriter out = response.getWriter();
        // 重置响应输出的内容长度
        response.setContentLength(-1);
        out.print(output.toCharArray());
        out.flush();
        out.close();
    }
}