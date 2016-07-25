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
    <c:url value="/assign/canAssign" var="url_canAssign"/>
    <c:url value="/assign/listTraining/${trainingId}" var="url_list"/>
</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li><a href="<c:url value="/training/detail/${trainingId}"/> ">当前集训</a></li>
            <li class="active">当前分队</li>
        </ol>
    </div>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="row">

                <div class="page-header">
                    <h3>&nbsp;&nbsp;即将进行的随机组队赛组队情况，请设置自己队的账号</h3>
                </div>
                <div class="col-md-10">
                    <br/>

                    <div class="col-md-12 form-group">
                        <ul class="list-group">
                            <c:forEach items="${teamList}" var="team" varStatus="i">
                                <li class="list-group-item">
                                    <h4 class="list-group-item-heading">
                                        <span class="badge">${i.index+1}</span>
                                        <c:forEach items="${team}" var="member">
                                            ${userInfo[member].realName}&nbsp;&nbsp;
                                        </c:forEach>
                                    </h4>
                                    <div style="padding-top: 15px"></div>
                                    <p class="list-group-item-text">
                                        <form class="form-inline" method="post" action="<c:url value="/assign/setAssignAccount"/> ">
                                            <input name="assignId" value="${assign.id}" hidden>
                                            <input name="pos" value="${i.index}" hidden>
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputtext3">使用的VJ账号</label>
                                                <c:if test="${i.index >= assign.accountList.size()}">
                                                    <input name="account" value="" type="text" class="form-control" id="exampleInputtext3" placeholder="使用的VJ账号">
                                                </c:if>
                                                <c:if test="${i.index < assign.accountList.size()}">
                                                    <input name="account" value="${assign.accountList.get(i.index)}" type="text" class="form-control" id="exampleInputtext3" placeholder="使用的VJ账号">
                                                </c:if>
                                            </div>
                                            <c:if test="${team.contains(user.id)}">
                                                <button type="submit" class="btn btn-default">确认</button>
                                            </c:if>
                                            <c:if test="${!team.contains(user.id)}">
                                                <button type="submit" class="btn btn-default" disabled>确认</button>
                                            </c:if>
                                        </form>
                                    </p>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-lg-3">
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="pull-right">
                    <c:url value="/assign/exportAssign/${assign.id}" var="url_export"/>
                    <a href="${url_export}" target="_blank" class="btn btn-primary">导出数据</a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footerInfo.jsp"/>
<c:if test="${!empty tip}">
    <script>
        alert("${tip}");
    </script>
</c:if>
</body>
</html>
