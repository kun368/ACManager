<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/8
  Time: 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>
<body>

    <div class="container">
        <jsp:include page="topBar.jsp"/>
        <div class="row">
            <div class="col-lg-1">
            </div>
            <div class="col-lg-10">
                <div class="page-header">
                    <h1>欢迎访问山东科技大学ACM集训队管理系统</h1>
                </div>
            </div>
            <div class="col-lg-1">
            </div>
        </div>


        <div class="row">
            <div class="col-lg-1">
            </div>
            <div class="col-lg-10">
                <!-- 多说评论框 start -->
                <div class="ds-thread" data-thread-key="1" data-title="Index" data-url="<%=request.getRequestURL()%>"></div>
                <!-- 多说评论框 end -->
                <!-- 多说公共JS代码 start (一个网页只需插入一次) -->
                <script type="text/javascript">
                    var duoshuoQuery = {short_name:"acmanager"};
                    (function() {
                        var ds = document.createElement('script');
                        ds.type = 'text/javascript';ds.async = true;
                        ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
                        ds.charset = 'UTF-8';
                        (document.getElementsByTagName('head')[0]
                        || document.getElementsByTagName('body')[0]).appendChild(ds);
                    })();
                </script>
                <!-- 多说公共JS代码 end -->
            </div>
            <div class="col-lg-1">
            </div>
        </div>
    </div> <!-- /container -->
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
