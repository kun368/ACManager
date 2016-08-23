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
    <title>集训队员 - ACManager</title>
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
    <c:url value="/assign/listTraining/${trainingId}" var="suijifendui"/>
    <c:url value="/training/doAddStage" var="url_doadstage"/>
    <c:url value="/training/verifyUserJoin" var="url_verifyUserJoin"/>

    <script>
        var Userid;
        var trainingId;
        function userinfo(p,tid) {
            Userid=p;
            trainingId=tid;
            console.log(Userid);
        }
        $(document).ready(function () {
            $('#mytable').DataTable({
                "order": [[3, "desc"]]
            });
            $('#mytable1').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                dom: '<"top"if>rt<"bottom"lp>',
            });
            $('#fendui').click(function () {
                location.href="${suijifendui}"
            });
            $('#savabutton').click(function () {
                $.post("${url_doadstage}", {
                    name: $('#name').val(),
                    startDate: $('#beginTime').val(),
                    endDate: $('#endTime').val(),
                    remark: $('#remark').val()
                }, function (data) {
                    alert(data);
                    location.reload();
                })
            });

            $('#verify_true').click(function () {
                $.post("${url_verifyUserJoin}",{
                    userId:Userid,
                    trainingId:trainingId,
                    op: "true"
                },function (date) {
                    alert(date);
                    location.reload();
                });
            });

            $('#verify_false').click(function () {
                $.post("${url_verifyUserJoin}",{
                    userId: Userid,
                    trainingId:trainingId,
                    op: "false"
                },function (date) {
                    alert(date);
                    location.reload();
                });
            });
            $('#shenhe').addClass('active');
        });
    </script>

</head>
<body>

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp" />
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">${info.name}</li>
        </ol>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading" style="padding: 0px">
                <%@include file="training_topbar.jsp"%>
            </div>
            <div class="panel-body">
                <div style="padding-bottom: 10px">
                    示例：
                    <button class="btn btn-sm btn-success" >通过审核</button>
                    <button class="btn btn-sm btn-warning">等待审核</button>
                    <button class="btn btn-sm btn-danger">拒绝加入</button>
                </div>

                <ul class="list-group">
                    <c:set value="Success" var="success"/>
                    <c:set value="Pending" var="pending"/>
                    <c:set value="Reject" var="reject"/>

                    <c:forEach items="${ujoinT}" var="user">
                        <c:if test="${user.value eq success}">
                            <li class="list-group-item list-group-item-success">
                                    ${user.key.username}(${user.key.realName}，${user.key.major})
                            </li>
                        </c:if>
                        <c:if test="${user.value eq pending}">
                            <a class="list-group-item list-group-item-warning" href="#memberModel" data-toggle="modal" id="pendingli"
                               onclick="userinfo(${user.key.id}, ${info.id})">
                                    ${user.key.username}(${user.key.realName}，${user.key.major})
                            </a>
                        </c:if>
                        <c:if test="${user.value eq reject}">
                            <li class="list-group-item list-group-item-danger">
                                    ${user.key.username}(${user.key.realName}，${user.key.major})
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--审核状态模态框-->
<div class="modal fade" id="memberModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title">资格审核</h4>
            </div>
            <div class="modal-body ">
                <p>是否通过此人加入集训：${info.name}？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="verify_true">通过</button>
                <button type="button" class="btn btn-primary" id="verify_false">拒绝</button>
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
</body>
</html>
