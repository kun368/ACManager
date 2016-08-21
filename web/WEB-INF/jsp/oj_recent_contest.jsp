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
    <title>近期比赛 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                "order": [[2, "asc"]]
            });
        });
    </script>

</head>

<body>


<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-1">
        </div>
        <div class="col-lg-10">
            <div class="page-header">
                <h1>各大OJ近期比赛汇总
                </h1>
                <p>数据来源：<a href="http://contests.acmicpc.info/contests.json" target="_blank">http://contests.acmicpc.info/contests.json</a></p>
            </div>
            <table class="table table-condensed table-striped table-hover display" id="mytable">
                <thead class="tab-header-area">
                <tr>
                    <th>OJ</th>
                    <th>Name</th>
                    <th>Start Time</th>
                    <th>Week</th>
                    <th>Access</th>
                </tr>
                </thead>
                <tfoot>

                </tfoot>

                <tbody>
                <c:forEach items="${list}" var="contest" varStatus="i">
                    <tr>
                        <td>${contest.oj}</td>
                        <td><a href="${contest.link}" target="_blank">${contest.name}</a></td>
                        <td>${contest.start_time}</td>
                        <td>${contest.week}</td>
                        <td>${contest.access}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-lg-1">
        </div>
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
</body>
</html>
