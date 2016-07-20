<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/14
  Time: 17:14
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
<div class="container-fluid">
    <div class="col-lg-2">
    </div>
    <div class="col-lg-8">
        <div class="page-header">
            <h1>导入<small>Vjudge比赛</small></h1>
        </div>

        <div>
            <form class="form-horizontal" method="post" action="<c:url value="/contest/doAdd1"/> ">
                <div class="col-lg-10">
                    <div class="col-lg-6">
                        <div class="form-group">
                            <input type="text" id="inputcontestname"  name="contestName" class="form-control" placeholder="ContestName*" autofocus required>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="contestType">
                                <option value="PERSONAL">个人赛</option>
                                <option value="TEAM">组队赛</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="contestStage">
                                <c:forEach items="${groups}" var="group">
                                    <option value="${group.id}">${group.id}.${group.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <textarea id="Contest"  name="vjContest" class="form-control" rows="15" required></textarea>
                    </div>
                    <div class="form-group pull-right">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">下一步</button>
                    </div>

                </div>
            </form>
        </div>
        </div>
    <div class="co-lg-2">

    </div>
</div>
</body>
</html>
