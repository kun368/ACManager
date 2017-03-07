<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/5 0005
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>做题统计 - ACManager</title>
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
    <link rel="stylesheet" href="//cdn.bootcss.com/datatables/1.10.13/css/jquery.dataTables.min.css">
    <script>
        function exportTable() {
            $("#mytable").table2excel({
                name: "doc1",
                filename: "做题统计"
            });
        }
    </script>
</head>
<body>

<div class="container-fluid" style="margin-right: 0.5%;margin-left: 0.5%">
    <jsp:include page="topBar.jsp"/>
    <div class="row" style="padding-bottom: 20px">
    </div>

    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title" id="panel_titile"></h3>
            </div>
            <div class="panel-body" id="table-div">

                <div class="row" style="padding-left: 20px">
                    <div class="pull-left">
                        <c:if test="${(!empty user) and (user.isAdmin())}">
                        </c:if>
                        <button class="btn btn-info btn-sm" onclick="exportTable()">导出表格</button>
                    </div>
                </div>

                <hr style="margin:10px "/>

                <table class="table table-condensed table-striped table-hover display"
                       id="mytable">
                    <thead class="tab-header-area">
                    <tr id="table_head">
                        <th>姓名</th>
                        <th>班级</th>
                        <th>总计</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>

                    </tbody>
                </table>
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

<c:url value="/api/cpt/statistic/${treeId}/${nodeId}" var="url_statistic"/>
<c:url value="/api/cpt/seeNode/${treeId}/${nodeId}" var="url_seeNode"/>

<script>
    var headid = [];
    $(document).ready(function () {
        $.ajax({
            url: "${url_seeNode}",
            type: "get",
            dataType: "json",
            success: function (data) {
                $('#panel_titile').text(data.name);
                headid[0] = data.id;
                for (var i = 0; i < data.sons.length; i++) {
                    $('#table_head').append("<th>" + data.sons[i].name + "</th>");
                    headid[i + 1] = data.sons[i].id;
                }
                var table = $('#mytable').DataTable({
                    ajax: "${url_statistic}",
                    columns: hehe()
                });
            }
        });

    });
    function hehe() {
        var res = [
            {data: "userReal"},
            {data: "userMajor"}
        ];
        for (var i = 0; i < headid.length; i++) {
            var name = "acCount" + headid[i];
            res.push({data: name});
        }
        return res;
    }
</script>
</body>
</html>
