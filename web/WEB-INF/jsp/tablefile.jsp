<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/9
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
//                ajax:{
//                    url:'mujava',
//                    type:'Post'
//                },
//                "columns" : [
//                    {"data" : "id"},
//                    {"data" : "firstName"},
//                    {"data" : "lastName"}
//                ]
//
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
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Age</th>
                <th>Start date</th>
                <th>Salary</th>
            </tr>
            </thead>
        <tfoot>

        </tfoot>

        <tbody>
            <tr>
                <td>fjfsaf</td>
                <td>fjfsaf</td>
                <td>fjfsaf</td>
                <td>fjfsaf</td>
                <td>fjfsaf</td>
                <td>fjfsaf</td>
            </tr>
            <tr>
                <td>afjfsaf</td>
                <td>afjfsaf</td>
                <td>afjfsaf</td>
                <td>afjfsaf</td>
                <td>afjfsdaf</td>
                <td>afjfsaf</td>
            </tr>
        </tbody>
    </table>
</div>

</body>
</html>
