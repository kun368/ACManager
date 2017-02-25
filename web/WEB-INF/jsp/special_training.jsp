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
    <title>专题训练 - ACManager</title>
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
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>

    <c:url value="/cpt/add" var="url_cpt_add"/>
    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                "order": [[2, "desc"]]
            });
            $('#savabutton').click(function () {
                $(this).attr("disabled","disabled");
                $.post("${url_cpt_add}",{
                    name: $('#name').val(),
                    remark: $('#remark').val(),
                    csvTree: $('#csvTree').val()
                }, function(data){
                    alert(data);
                    location.reload();
                });
            });
        });
        function exportTable() {
            $("#mytable").table2excel({
                name: "doc1",
                filename: "专题训练"
            });
        }
    </script>
</head>
<body>

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li class="active">专题训练列表</li>
        </ol>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">专题训练列表</h3>
            </div>
            <div class="panel-body">
                    <div class="row" style="padding-left: 20px">
                        <div class="pull-left">
                            <button class="btn btn-info btn-sm" id="addbutton" data-toggle="modal"
                                    data-target="#myModal">添加专题</button>
                            <a class="btn btn-info btn-sm" href="<c:url value="/cpt/rule"/> "
                               target="_blank">添加规则</a>
                            <button class="btn btn-info btn-sm" onclick="exportTable()">导出表格</button>
                        </div>
                    </div>
                    <hr style="margin:10px "/>

                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th hidden>ID</th>
                        <th>名称</th>
                        <th>添加时间</th>
                        <th>题目数</th>
                        <th>创建者</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>

                    <c:forEach items="${cptList}" var="i">
                        <tr>
                            <td hidden>${i.id}</td>
                            <c:url value="/cpt/detail/${i.id}" var="url_cur_detail"/>
                            <td><a href="${url_cur_detail}">${i.name}</a></td>
                            <td>${i.addTime}</td>
                            <td>${i.node.allPids().size()}</td>
                            <td>${i.addUser.username}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加专题训练集</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post"><!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="名称" id="name" required>
                    </div>
                    <div class="form-group">
                        <textarea rows="5" class="form-control" placeholder="备注" id="remark"></textarea>
                    </div>
                    <div class="form-group">
                        <textarea rows="10" class="form-control" placeholder="CSV格式文件" id="csvTree"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="savabutton">保存</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
</body>
</html>
