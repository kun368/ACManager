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
    <title>登陆 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>

<body>



<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>欢迎登陆
                    <small>ACManager</small>
                </h1>
            </div>

            <form class="form-horizontal" method="post" action="<c:url value="/auth/dologin"/> ">
                <div class="col-lg-6">
                    <div class="form-group">
                        <input type="text" name="username" id="inputUsername" class="form-control" placeholder="UserName"
                               required autofocus>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="inputPassword" class="form-control"
                               placeholder="Password"
                               required>
                    </div>
                    <c:if test="${!empty tip}">
                        <span id="msg">${tip}</span>
                    </c:if>
                    <div class="form-horizontal pull-right">
                        <button class="btn btn-primary" id="sign" type="submit">登陆</button>
                        <button class="btn btn-primary" id="reset" type="reset">重置</button>
                    </div>
                </div>
            </form>
        </div>
        </div>
        <div class="col-lg-2">
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
</body>
</html>
