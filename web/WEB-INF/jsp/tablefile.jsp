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
                dom: '<"top"if>rt<"bottom"lp>'
            });
        });
    </script>

</head>
<body>
<jsp:include page="topBar.jsp" />
<div class="container">
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
                <%--<c:forEach items="${cptsName}" var="cptname">--%>
                    <%--<th class="text-overflow">${cptname}</th>--%>
                <%--</c:forEach>--%>
            </tr>
            </thead>
        <tfoot>

        </tfoot>

        <tbody>
            <c:forEach begin="0" end="${users.size()-1}" step="1" var="i">
                <tr>
                    <td>${users.get(i).realName}</td>
                    <td>${users.get(i).username}</td>
                    <td>${users.get(i).major}</td>
                    <td>${users.get(i).uvaId}</td>
                    <td>${bookCnt.get(i).get(0) + bookCnt.get(i).get(1)}</td>
                    <c:forEach begin="0" end="${booksName.size()-1}" var="j">
                        <td>${bookCnt.get(i).get(j)}</td>
                    </c:forEach>
                    <%--<c:forEach begin="0" end="${cptsName.size()-1}" var="j">--%>
                        <%--<td>${cptCnt.get(i).get(j)}</td>--%>
                    <%--</c:forEach>--%>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
