<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/7
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>账号密码找回 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
    <script src="//cdn.bootcss.com/js-sha1/0.3.0/sha1.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>

<body>

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>找回账号密码
                    <small>ACManager</small>
                </h1>
            </div>
            <h4>
                <strong>如果您忘记密码：</strong>由于密码已经加密，只能重新设定，请将您的新密码在
                <a href="http://tool.oschina.net/encrypt?type=2" target="_blank">此处</a>
                用SHA1加密后，联系管理员（QQ1004788567）修改。
            </h4>
            <div style="padding-top: 10px"></div>
            <h4>
                <strong>如果您忘记账号：</strong>请在下面的对应表中查找是否有您的信息，如果没有请重新注册。
            </h4>
            <div style="padding-top: 10px"></div>
            <c:forEach items="${userList}" var="useri">
                <p>${useri.username} &nbsp;&nbsp; - &nbsp;&nbsp; ${useri.realName} &nbsp;&nbsp; - &nbsp;&nbsp; ${useri.major}</p>
            </c:forEach>
        </div>
        </div>
        <div class="col-lg-2">
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>

</body>
</html>
