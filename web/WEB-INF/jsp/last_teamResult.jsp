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
    <title>即将进行的比赛分队 - ACManager</title>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <c:url value="/assign/canAssign" var="url_canAssign"/>
    <c:url value="/assign/listTraining/${trainingId}" var="url_list"/>
    <script>
        $(function () {
            $('#suiji').addClass('active');
        });
    </script>
</head>
<body>

<div class="container-fluid" style="margin-right: 0.5%;margin-left: 0.5%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">${info.name}</li>
        </ol>
    </div>



    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading" style="padding: 0px">
                <ul class="nav nav-tabs" id="hehedadada">
                    <%@include file="training_topbar.jsp" %>
                </ul>
            </div>
            <div class="panel-body">
                <c:if test="${(!empty user) && (user.isAdmin())}">
                    <div class="row" style="padding-left: 20px">
                        <div class="pull-left">
                            <a href="<c:url value="/assign/listTraining/${trainingId}"/>" class="btn btn-info btn-sm"
                               id="fendui">随机分队</a>
                        </div>
                    </div>
                    <hr style="margin:10px "/>
                </c:if>
                <div class="form-group">
                    <c:choose>
                        <c:when test="${!empty teamList}">
                            <h3>即将进行的随机组队赛组队情况，请设置自己队的账号</h3>
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
                                        <form class="form-inline" method="post"
                                              action="<c:url value="/assign/setAssignAccount"/> ">
                                            <input name="trainingId" value="${trainingId}" hidden>
                                            <input name="assignId" value="${assign.id}" hidden>
                                            <input name="pos" value="${i.index}" hidden>
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputtext3">使用的VJ账号</label>
                                                <c:if test="${i.index >= assign.accountList.size()}">
                                                    <input name="account" value="" type="text" class="form-control"
                                                           id="exampleInputtext3" placeholder="使用的VJ账号">
                                                </c:if>
                                                <c:if test="${i.index < assign.accountList.size()}">
                                                    <input name="account" value="${assign.accountList.get(i.index)}"
                                                           type="text" class="form-control" id="exampleInputtext3"
                                                           placeholder="使用的VJ账号">
                                                </c:if>
                                            </div>
                                            <c:if test="${team.contains(user.id) or user.isAdmin()}">
                                                <button type="submit" class="btn btn-default">确认</button>
                                            </c:if>
                                        </form>
                                        </p>
                                    </li>
                                </c:forEach>
                            </ul>
                            <div class="row">
                                <div class="pull-left" style="padding-left: 20px">
                                    <c:url value="/assign/exportAssign/${assign.id}" var="url_export"/>
                                    <a href="${url_export}" target="_blank" class="btn btn-primary">导出数据</a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h3>暂时没有随机分队数据...</h3>
                        </c:otherwise>
                    </c:choose>
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
