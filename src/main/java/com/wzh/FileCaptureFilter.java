package com.wzh;

/**
 * Created by wzh on 2017/5/24.
 */
import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileCaptureFilter implements Filter{

    private String protDirPath;
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException {
//
// LoginUser loginUser = (LoginUser) ((HttpServletRequest)request).getSession().getAttribute(LoginConstant.USER);
// if (loginUser == null)
// throw new NoLoginException();
        System.out.print(request.getLocalAddr());
        String fileName = "/cache/userId_.html";
        File file = new File(filterConfig.getServletContext().getRealPath(fileName));
// 判断缓存文件是否存在或者是否重新设置了缓存内容
        if (!file.exists()) {// 如果缓存文件不存在
            fileName=protDirPath+fileName;
            FileCaptureResponseWrapper wrapper = new FileCaptureResponseWrapper((HttpServletResponse)response);
            chain.doFilter(request, wrapper);
//得到的html 页面结果字符串
//String html = responseWrapper.toString();
// 写成html 文件
            wrapper.writeFile(fileName);
// back to browser
            wrapper.writeResponse();

        }else{
// 转发至缓存文件
            request.getRequestDispatcher(fileName).forward(request, response);
        }
    }

    @Override
    public void destroy() {
// TODO Auto-generated method stub
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        this.filterConfig=arg0;
        protDirPath = arg0.getServletContext().getRealPath("/");
    }
}
