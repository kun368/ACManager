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

<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>导入比赛</h1>
            </div>
            <div>
                <form class="form-horizontal" method="post" action="<c:url value="/contest/importContest"/> ">
                    <div class="col-lg-10">
                        <div class="col-lg-6">
                            <div class="form-group">
                                <input type="text"   name="contestName" class="form-control" placeholder="比赛名称*" autofocus required>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="contestType">
                                    <option value="PERSONAL">个人赛</option>
                                    <option value="TEAM">组队赛</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="text"   name="stTime" class="form-control" placeholder="比赛开始时间(eg: 2007-12-03T10:15:30)" autofocus required>
                            </div>
                            <div class="form-group">
                                <input type="text"   name="edTime" class="form-control" placeholder="比赛结束时间(eg: 2007-12-03T10:15:30)" autofocus required>
                            </div>
                        </div>

                        <div class="form-group">
                            <textarea name="vjContest" class="form-control" rows="15" placeholder="VJudge榜单" required></textarea>
                        </div>
                        <div class="form-group">
                            <textarea name="myConfig" class="form-control" rows="8" placeholder="配置文件"></textarea>
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
</div>

<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
