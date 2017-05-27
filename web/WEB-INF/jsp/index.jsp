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
    <script src="//cdn.bootcss.com/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
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
                <h5>2) 做题统计数据现支持：VJudge、UVa、HDU、POJ、Codeforces，填写账号后即可统计</h5>
                <h5>3) 零题数账号不会出现在做题统计和比赛统计中</h5>
                <h5>4) 请尽量使用 Firefox、Chrome、Edge 浏览器访问本站</h5>
                <h5>5) 系统外网地址为：http://cise.sdust.edu.cn/acmanager，欢迎有志青年提交 pull request 参与开发</h5>
                <h5>6) 由于各大OJ升级网站，导致本站爬虫失效，或者存在其他任何问题建议，请在及时下面留言或联系管理员（QQ 1004788567，1064406280）</h5>
                <h5>7) 由于多说评论即将停止服务, 评论服务更换为基于github issues 的gitment, 原有数据将被清空</h5>
            </div>
        </div>
        <div class="col-lg-1">
        </div>
    </div>


    <div class="row">
        <div class="col-lg-1">
        </div>
        <div class="col-lg-10">
            <!--
            评论区代码
            -->
            <div id="container"></div>
            <link rel="stylesheet" href="css/default.css">
            <script src="https://imsun.github.io/gitment/dist/gitment.browser.js"></script>
            <script>
                var gitment = new Gitment({
                    owner: 'wzhxyz',
                    repo: 'ACManager',
                    oauth: {
                        client_id: 'e0909750f96f4d66bc57',
                        client_secret: '851b698765405f541f0472a89034588ba30432b1',
                    },
                })
                gitment.render('container')
            </script>

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
