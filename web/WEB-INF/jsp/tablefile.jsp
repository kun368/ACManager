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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">


    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                dom: '<"top"if>rt<"bottom"lp>',
                "order": [[3, "asc"]]
            });
        });

    </script>
</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp" />
    <div class="row" style="padding-bottom: 10px">
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">队内UVa统计结果</h3>
            </div>
            <div class="panel-body">
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th>姓名</th>
                        <th>用户名</th>
                        <th>班级</th>
                        <th>UVaId</th>
                        <th>合计</th>
                        <c:forEach items="${booksName}" var="bookname">
                            <th>${bookname}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${users}" var="curUser" varStatus="i">
                        <tr>
                            <td>${curUser.realName}</td>
                            <td>${curUser.username}</td>
                            <td>${curUser.major}</td>
                            <td>${curUser.uvaId}</td>
                            <td>${bookCnt.get(i.index).get(0) + bookCnt.get(i.index).get(1)}</td>
                            <c:forEach items="${bookCnt.get(i.index)}" var="j">
                                <td>${j}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="pull-left">
            <button class="btn btn-info" id="addbutton">更新数据&nbsp;(LastUpdate: ${lastUpdate})</button>
        </div>
    </div>
</div>

<c:url value="/uva/updatedb" var="url_updatedb"/>
<script>
    $(document).ready(function () {
        $('#addbutton').click(function () {
            $.post("${url_updatedb}", {

                    },function (data) {
                        alert(data);
                        location.reload()
                    }
            );
        })
    });
</script>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
