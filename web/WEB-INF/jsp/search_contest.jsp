<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/9
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>搜索比赛 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <script src="//cdn.bootcss.com/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/datatables/1.10.13/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css"
          href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
</head>
<body>

<div class="container-fluid" style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">搜索比赛</li>
        </ol>
    </div>

    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">搜索比赛</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <c:url value="/training/searchContest" var="url_search"/>
                        <form method="get" action="${url_search}">
                            <div class="input-group">
                                <input type="text" name="queryStr" class="form-control" value="${queryStr}">
                                <span class="input-group-btn">
                                <button class="btn btn-primary" type="submit">搜索</button>
                                </span>
                            </div>
                        </form>

                        <div style="margin-top: 20px"></div>

                        <c:if test="${(empty result) and (!empty queryStr)}">
                            <h3>没有找到关于"${queryStr}"的比赛:-)</h3>
                        </c:if>

                        <ul class="list-group">
                            <c:forEach items="${result}" var="i">
                                <li class="list-group-item">
                                    <c:url value="/contest/showScore/${i.id}" var="url_cur_contest"/>
                                    <h3><a target="_blank" href="${url_cur_contest}">${i.name}</a></h3>

                                    <h5>
                                        <span class="glyphicon glyphicon-time"></span>
                                        ${i.typeChs()}赛 &nbsp;&nbsp; ${i.startTimeStr}
                                    </h5>

                                    <span class="glyphicon glyphicon-stats"></span>
                                    <c:choose>
                                        <c:when test="${!empty i.sourceDetail}">
                                            <a href="${i.sourceUrl}" target="_blank">${i.sourceDetail}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${i.sourceUrl}"target="_blank">${i.source}</a>
                                        </c:otherwise>
                                    </c:choose>
                                    <h5></h5>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
</body>
</html>
