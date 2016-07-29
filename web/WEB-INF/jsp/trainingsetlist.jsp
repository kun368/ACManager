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
    <title>集训列表 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>

    <c:url value="/training/doAddTraining" var="url_doAddTraining"/>
    <c:url value="/training/doApplyJoinTraining" var="url_applyjoin"/>
    <c:url value="/training/modifyTraining" var="url_modify"/>
    <script>
        $(document).ready(function () {
            $('#mytable').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching: true,
                dom: '<"top"if>rt<"bottom"lp>',
                "order": [[4, "desc"]]
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
            $('#savabutton2').click(function () {
                $.post("${url_modify}", {
                    id:$('#id2').val(),
                    name: $('#name2').val(),
                    beginTime: $('#beginTime2').val(),
                    endTime: $('#endTime2').val(),
                    remark:$('#remark2').val()
                }, function (data) {
                    alert(data);
                    location.reload();
                })
            });
            $('#beginTime').datetimepicker({
                format:'Y-m-d',
                formatDate:'Y-m-d',
                timepicker:false,
                dayOfWeekStart : 1,
                lang:'en',
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'
            });
            $('#beginTime2').datetimepicker({
                format:'Y-m-d',
                formatDate:'Y-m-d',
                timepicker:false,
                dayOfWeekStart : 1,
                lang:'en',
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'
            });
            $('#endTime').datetimepicker({
                format:'Y-m-d',
                formatDate:'Y-m-d',
                timepicker:false,
                dayOfWeekStart : 1,
                lang:'en',
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'
            });
            $('#endTime2').datetimepicker({
                format:'Y-m-d',
                formatDate:'Y-m-d',
                timepicker:false,
                dayOfWeekStart : 1,
                lang:'en',
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'
            });
        });
        function applyJoin(trainingId) {
            $.post("${url_applyjoin}",{
                userId: '${user.id}',
                trainingId: trainingId
            },function () {
                alert("已收到您的申请...");
                location.reload();
            });
        }
        function updata(obj,id) {
            var tds=$(obj).parent().parent().find('td');
            $('#name2').val(tds.eq(0).text());
            $('#beginTime2').val(tds.eq(1).text());
            $('#endTime2').val(tds.eq(2).text());
            $('#remark2').val(tds.eq(3).text());
            $('#id2').val(id);
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
                                <a  id="modifybutton" data-toggle="modal" data-target="#myModal2" onclick="updata(this,${training.id})">编辑属性</a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${(!empty user) && (user.isAdmin())}">
                                        <a href="<c:url value="/training/trainingUser/${training.id}"/>">审核队员</a>
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                                </c:choose>
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
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modifyModalLabel">编辑属性</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post"><!--填写提交地址-->
                    <div class="form-group">
                        集训名称：<input type="text" class="form-control" placeholder="名称" id="name2" required>
                    </div>
                    <div class="form-group">
                        开始日期:<input type="text" class="form-control" placeholder="开始时间" id="beginTime2" required>
                    </div>
                    <div class="form-group">
                        截止时间:<input type="text" class="form-control" placeholder="截止时间" id="endTime2" required>
                    </div>
                    <div class="form-group">
                        备注：<textarea rows="5" class="form-control" placeholder="备注" id="remark2"></textarea>
                    </div>
                    <div class="form-group" hidden>
                        集训id：<input type="text" class="form-control" placeholder="id" id="id2" hidden>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="savabutton2">保存</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
