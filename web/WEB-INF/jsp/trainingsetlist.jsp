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
<div class="container-fluid">
    <ol class="breadcrumb">
        <li>您所在的位置：</li>
        <li class="active">集训列表</li>
    </ol>

    <table class="table table-condensed table-striped table-hover display" id="mytable">
        <thead class="tab-header-area">
        <tr>
            <th>id</th>
            <th>集训名称</th>
            <th>开始时间</th>
            <th>停止时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tfoot>

        </tfoot>

        <tbody>
        <c:forEach items="${allList}" var="training">
            <tr>
                <td>${training.id}</td>
                <td><a href="<c:url value="/training/detail/${training.id}"/> ">${training.name}</a></td>
                <td>${training.startDate}</td>
                <td>${training.endDate}</td>
                <td><a href="" >申请加入</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pull-left">
    <button class="btn btn-defult" id="addbutton" data-toggle="modal" data-target="#myModal">添加集训</button>
    </div>
</div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">添加集训</h4>
                    </div>
                    <div class="modal-body ">
                        <form class="form">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="名称" required>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="开始时间" required>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="截止时间" required>
                            </div>
                            <div class="form-group">
                                <textarea rows="5" class="form-control" placeholder="备注"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary">保存</button>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>
