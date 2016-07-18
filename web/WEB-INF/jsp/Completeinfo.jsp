<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/14
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="topBar.jsp"/>
<div class="container">
    <div class="row">

        <div class="page-header">
            <h2>&nbsp;&nbsp;比赛名称：${contest.name}</h2>
        </div>
    <div class="col-md-7">
        <h4 class="text-info text-right">请修改完善队员信息(填写真实姓名)，队员之间用英文逗号分隔</h4>
        <br/>
        <form class="form-horizontal" method="post" action="<c:url value="/contest/doAdd2"/> ">
            <c:forEach items="${contest.ranks}" var="team" varStatus="i">
                <div class="form-group">
                    <div class="col-md-3" style="text-align: right">
                    <span >${team.account}</span>
                    </div>
                    <div class="col-md-9">
                    <input type="text"  name="name_${i.index}" class="form-control col-lg-9" value="${team.teamName}">
                    </div>
                </div>
            </c:forEach>
            <input hidden name="contest" value="${contest}">
            <div class="form-group pull-right">
                <button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
            </div>
        </form>
    </div>
    </div>
</div>
</body>
</html>
