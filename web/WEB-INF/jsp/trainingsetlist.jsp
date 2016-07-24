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

    <c:url value="/training/doAddTraining" var="url_doAddTraining"/>
    <c:url value="/training/doApplyJoinTraining" var="url_applyjoin"/>
    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching: true,
                dom: '<"top"if>rt<"bottom"lp>',
                "order": [[3, "desc"]]
            });
            $('#savabutton').click(function () {
                $.post("${url_doAddTraining}", {
                    name: $('#name').val(),
                    startDate: $('#beginTime').val(),
                    endDate: $('#endTime').val(),
                    remark: $('#remark').val()
                }, function (data) {
                    alert(data);
                    location.reload();
                })
            });
        });
        function applyJoin(trainingId) {
            $.post("${url_applyjoin}",{
                userId: '${user.id}',
                trainingId: trainingId
            },function () {
                alert("申请提交成功，请耐心等待审核");
                location.reload();
            });
        }

    </script>

</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li class="active">集训列表</li>
        </ol>
    </div>

    <div class="row" style="padding-bottom: 20px">
        <div class="pull-right">
            <button class="btn btn-info" id="addbutton" data-toggle="modal" data-target="#myModal">添加集训</button>
        </div>
    </div>

    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">所有集训</h3>
            </div>
            <div class="panel-body">
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th>集训名称</th>
                        <th>开始日期</th>
                        <th>停止日期</th>
                        <th>添加时间</th>
                        <th>添加者</th>
                        <th>操作</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${allList}" var="training">
                        <tr>
                            <td><a href="<c:url value="/training/detail/${training.id}"/> ">${training.name}</a></td>
                            <td>${training.startDate}</td>
                            <td>${training.endDate}</td>
                            <td>${training.addTime}</td>
                            <td>${trainingAddUserList.get(training.addUid).username}</td>
                            <td>
                                <a href="">编辑属性</a>
                            </td>
                            <td>
                                <c:if test="${!empty ujointMap.get(training.id)}">
                                    <span>${ujointMap.get(training.id).status.name()}</span>
                                    <c:set value="Reject" var="reject"/>
                                    <c:if test="${ujointMap.get(training.id).status.name() eq reject}">
                                        <span><a href="javascript:void(0);" onclick="applyJoin(${training.id})">(重新申请)</a></span>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty ujointMap.get(training.id)}">
                                    <a href="javascript:void(0);" onclick="applyJoin(${training.id})">申请加入</a>
                                </c:if>
                            </td>
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
                <h4 class="modal-title" id="myModalLabel">添加集训</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post"><!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="名称" id="name" required>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="开始时间" id="beginTime" required>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="截止时间" id="endTime" required>
                    </div>
                    <div class="form-group">
                        <textarea rows="5" class="form-control" placeholder="备注" id="remark"></textarea>
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
