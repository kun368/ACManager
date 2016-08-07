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
    <title>阶段列表 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
    <c:url value="/assign/listTraining/${trainingId}" var="suijifendui"/>
    <c:url value="/training/doAddStage" var="url_doadstage"/>
    <c:url value="/training/verifyUserJoin" var="url_verifyUserJoin"/>
    <c:url value="/training/modifyStage" var="url_modify"/>
  <!-- <style type="text/css">
       .table-striped tbody tr:nth-child(odd) td {
           background-color: MediumSpringGreen;
       }
   </style>
   -->
    <script>
        var Userid;
        function userinfo(p) {
            Userid=p;
            console.log(Userid);
        }
        $(document).ready(function () {
            $('#mytable').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                stateSave: true,<!--状态保存-->
                pageLength: 25,<!--初始化单页显示数-->
                orderClasses: false,<!--排序列不高亮显示-->
                dom: '<"top"if>rt<"bottom"lp>',
                "order": [[4, "desc"]]
            });
            $('#mytable1').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                stateSave: true,<!--状态保存-->
                pageLength: 25,<!--初始化单页显示数-->
                orderClasses: false,<!--排序列不高亮显示-->
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

            $('#verify_true').click(function () {
                $.post("${url_verifyUserJoin}",{
                    userId:Userid,
                    op: "true"
                },function (date) {
                    alert(date);
                    location.reload();
                });
            });

            $('#verify_false').click(function () {
                $.post("${url_verifyUserJoin}",{
                    userId: Userid,
                    op: "false"
                },function (date) {
                    alert(date);
                    location.reload();
                });
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
    <jsp:include page="topBar.jsp" />
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">阶段列表</li>
        </ol>
    </div>



    <div class="row" style="padding-bottom: 20px">
        <div class="pull-right">
            <a href="<c:url value="/assign/lastAssign"/>" class="btn btn-primary" id="lastfendui">分队详情</a>
            <c:if test="${(!empty user) && (user.isAdmin())}">
                <button class="btn btn-info" id="fendui">随机分队</button>
                <button class="btn btn-info" id="addbutton" data-toggle="modal" data-target="#myModal">添加阶段</button>
            </c:if>
            </div>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">${info.name}&nbsp;&nbsp;详情</h3>
            </div>
            <div class="panel-body">
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th>阶段名称</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th hidden>备注</th>
                        <th>添加时间</th>
                        <th>比赛数</th>
                        <th>创建者</th>
                        <c:if test="${(!empty user) && (user.isAdmin())}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${stageList}" var="stage">
                        <tr>
                            <td><a href="<c:url value="/training/stage/${stage.id}"/> ">${stage.name}</a></td>
                            <td>${stage.startDate}</td>
                            <td>${stage.endDate}</td>
                            <td hidden>${stage.remark}</td>
                            <td>${stage.addTime}</td>
                            <td>${stageSizeMap.get(stage.id)}</td>
                            <td>${stageAddUserList.get(stage.addUid).username}</td>
                            <c:if test="${(!empty user) && (user.isAdmin())}">
                                <td>
                                    <a id="modifybutton" data-toggle="modal" data-target="#myModal2"
                                       onclick="updata(this,${stage.id})">编辑</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>



    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">${info.name}&nbsp;&nbsp;队员</h3>
            </div>
            <div class="panel-body">


                <c:set value="Success" var="success"/>
                <c:set value="Pending" var="pending"/>
                <c:set value="Reject" var="reject"/>
                <table class="table table-condensed table-striped table-hover display" id="mytable1">
                    <thead class="tab-header-area">
                    <tr>
                        <th>姓名</th>
                        <th>班级</th>
                        <th>状态</th>
                        <th>Rating</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${ujoinT}" var="user">
                        <tr>
                            <td>${user.key.realName}</td>
                            <td>${user.key.major}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.value eq success}">
                                        成功加入
                                    </c:when>
                                    <c:when test="${user.value eq pending}">
                                        申请中
                                    </c:when>
                                    <c:when test="${user.value eq reject}">
                                        被拒绝
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>...</td>
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
                <h4 class="modal-title" id="myModalLabel">添加阶段</h4>
            </div>
            <div class="modal-body ">
                <form class="form"><!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" id="name" class="form-control" placeholder="名称" required>
                    </div>
                    <div class="form-group">
                        <input type="text" id="beginTime" class="form-control" placeholder="开始时间" required>
                    </div>
                    <div class="form-group">
                        <input type="text" id="endTime" class="form-control" placeholder="截止时间" required>
                    </div>
                    <div class="form-group">
                        <textarea rows="5" id="remark" class="form-control" placeholder="备注"></textarea>
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
            <div class="modal-body ">
                <form class="form"><!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" id="name2" class="form-control" placeholder="名称" required>
                    </div>
                    <div class="form-group">
                        <input type="text" id="beginTime2" class="form-control" placeholder="开始时间" required>
                    </div>
                    <div class="form-group">
                        <input type="text" id="endTime2" class="form-control" placeholder="截止时间" required>
                    </div>
                    <div class="form-group">
                        <textarea rows="5" id="remark2" class="form-control" placeholder="备注"></textarea>
                    </div>
                    <div class="form-group" hidden>
                        <input type="text" id="id2" class="form-control" placeholder="id" hidden>
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
<c:if test="${!empty tip}">
    <script>
        alert("${tip}");
    </script>
</c:if>
</body>
</html>
