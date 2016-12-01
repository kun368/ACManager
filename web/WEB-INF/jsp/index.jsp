<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/8
  Time: 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>主页 - ACManager</title>
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

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>

    <div style="padding-top: 26px"></div>

    <div class="row">
        <div class="col-lg-1">
        </div>
        <div class="col-lg-10">
            <div class="jumbotron">
                <h2><strong>欢迎访问 SDUST ACM 集训队管理系统！</strong></h2>
                <p></p>
                <h5>1) 系统更新频率：</strong>
                    近期比赛:1小时 &nbsp;
                    做题统计:6小时 &nbsp;
                    CF/BC Rating:12小时 &nbsp;
                    全局比赛Rating:1天
                </h5>
                <h5>2) 做题统计数据现支持：Vjudge、UVa、HDU、POJ和Codeforces，填写账号后即可统计</h5>
                <h5>3) 请使用 Chrome, Firefox, IE (>=IE9) 或其他浏览器访问本站</h5>
                <h5>4) 存在任何问题，请在下面留言或<a href="mailto:kun368@126.com">联系管理员</A></h5>
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
<c:if test="${!empty tip}">
    <script>
        alert("${tip}");
    </script>
</c:if>
</body>
</html>
