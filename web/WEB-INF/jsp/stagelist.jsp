<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/9
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <c:url value="/assign/listTraining/${trainingId}" var="suijifendui"/>
    <c:url value="/training/doAddStage" var="url_doadstage"/>
    <c:url value="/training/verifyUserJoin" var="url_verifyUserJoin"/>
    <c:url value="/training/modifyStage" var="url_modify"/>
    <c:url value="/rating/updateTraining/${trainingId}" var="url_update_rating"/>
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
                pageLength: 25,<!--初始化单页显示数-->
                "order": [[2, "desc"]]
            });
            $('#fendui').click(function () {
                location.href="${suijifendui}"
            });
            $('#savabutton').click(function () {
                $.post("${url_doadstage}", {
                    name: $('#name').val(),
                    startDate: $('#beginTime').val(),
                    endDate: $('#endTime').val(),
                    remark: $('#remark').val(),
                    countToRating: $('#true_false').prop('checked')
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
                    remark:$('#remark2').val(),
                    countToRating: $('#true_false2').prop('checked')
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
            $('#jieduan').addClass('active');
        });
        function updata(obj,id) {
            var tds=$(obj).parent().parent().find('td');
            $('#name2').val(tds.eq(0).text());
            $('#beginTime2').val(tds.eq(1).text());
            $('#endTime2').val(tds.eq(2).text());
            $('#remark2').val(tds.eq(3).text());
            $('#id2').val(id);
            var chec_true_false=tds.eq(7).text();
            if(chec_true_false=='true')
                $('#true_false2').prop("checked",true).change();
            else
                $('#true_false2').prop("checked",false).change();
        }
    </script>

</head>
<body>

<div class="container-fluid"  style="margin-right: 3%;margin-left: 3%">
    <jsp:include page="topBar.jsp" />
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">集训详情</li>
        </ol>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading" style="padding: 0px">
                <%@include file="training_topbar.jsp"%>
            </div>

            <div class="panel-body">
                <c:if test="${(!empty user) && (user.isAdmin())}">
                    <div class="row" style="padding-left: 20px">
                        <div class="pull-left">
                            <button class="btn btn-info btn-sm" id="addbutton" data-toggle="modal" data-target="#myModal" style="">添加阶段</button>
                        </div>
                    </div>
                    <hr style="margin:10px "/>
                </c:if>

                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th>阶段名称</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th hidden>备注</th>
                        <th hidden>添加时间</th>
                        <th>比赛</th>
                        <th>创建者</th>
                        <th hidden>计分阶段</th>
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
                            <td hidden>${stage.addTime}</td>
                            <td>${stageSizeMap.get(stage.id)}</td>
                            <td>${stageAddUserList.get(stage.addUid).username}</td>
                            <td hidden>${stage.countToRating}</td>
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
                    <div class="form-group">
                        <label>
                            <input id="true_false"
                                    type="checkbox" checked data-toggle="toggle" data-style="ios"
                                   data-onstyle="info" data-size="small"
                                   data-on="是" data-off="否">
                            计分阶段
                        </label>
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
                    <div class="form-group">
                        <label>
                            <input id="true_false2"
                                   type="checkbox" checked data-toggle="toggle" data-style="ios"
                                   data-onstyle="info" data-size="small"
                                   data-on="是" data-off="否">
                            计分阶段
                        </label>
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
