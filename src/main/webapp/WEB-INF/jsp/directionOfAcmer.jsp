<%--
  Created by IntelliJ IDEA.
  User: wzh
  Date: 2017/5/23
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>历届队员去向</title>
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
    <div class="row">
        <div class="col-lg-1">
        </div>
        <div class="col-lg-10">
            <div class="page-header">
                <h1>历届队员去向</h1>
            </div>
        </div>
        <div class="col-lg-1">
        </div>
    </div>


    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <table class="table table-condensed table-striped table-hover display" id="mytable">
                <thead class="tab-header-area">
                <tr>
                    <th width = "20%">专业</th>
                    <th width = "10%">姓名</th>
                    <th width = "60%">工作去向</th>
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
        <div class="col-lg-2">
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
