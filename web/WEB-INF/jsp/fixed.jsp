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
    <title>队伍统计 - ACManager</title>
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

    <c:url value="/training/${trainingId}/fixedTeam/add_modify" var="url_add_modify"/>
    <c:url value="/training/${trainingId}/fixedTeam/delete" var="url_delete_fixedteam"/>
    <c:url value="/rating/updateTrainingTeam/${trainingId}" var="url_update_rating"/>

    <script>
        $(document).ready(function () {
            $('#mytable1').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                stateSave: true,<!--状态保存-->
                pageLength: 50,<!--初始化单页显示数-->
                orderClasses: false,<!--排序列不高亮显示-->
                dom: '<"top"if>rt<"bottom"lp>',
//                responsive: true,
                order:[[7,'desc']],
                columnDefs: [
                    { "type": "chinese-string", targets: 2},
                    {"orderable": false,targets:4},
                    {"orderable": false,targets:5},
                    {"orderable": false,targets:6},
                ]
            });
            $('#trueModel').click(function () {
               $.post("${url_add_modify}",{
                   id:$('#ID').val(),
                   name1:$('#E_teamname').val(),
                   name2:$('#C_teamname').val(),
                   vjname:$('#VJ_teamname').val(),
                   user1Id:$('#name1').val(),
                   user2Id:$('#name2').val(),
                   user3Id:$('#name3').val()
               },function (data) {
                   alert(data);
                   location.reload();
               });
            });
            $('#tianjia').click(function () {
                $('#myModalLabel').text('添加队伍');
                $('#ID').val(-1);
                $('#E_teamname').val('');
                $('#C_teamname').val('');
                $('#VJ_teamname').val('');
                $('select option[value="-1"]').prop("selected","selected");
            });
            $('#duiwu').addClass('active');
            $('#update_Rating').click(function () {
                $(this).attr("disabled","disabled");
                $.post("${url_update_rating}",{
                },function(data){
                    alert(data);
                    location.reload();
                });
            });
        });
        function updata (obj) {
            var ths=$(obj).parent().parent().children();
            $('#ID').val(ths.eq(0).text());
            $('#E_teamname').val(ths.eq(1).text());
            $('#C_teamname').val(ths.eq(2).text());
            $('#VJ_teamname').val(ths.eq(3).text());
            $('#myModalLabel').text('修改队伍信息');
            $("#name1 option").each(function (i,item) {
                var option_text=$(this).text();
                if(option_text==ths.eq(4).text()){
                    $(this).prop("selected","selected")
                }
            });
            $("#name2 option").each(function (i,item) {
                var option_text=$(this).text();
                if(option_text==ths.eq(5).text()){
                    $(this).prop("selected","selected")
                }
            });
            $("#name3 option").each(function (i,item) {
                var option_text=$(this).text();
                if(option_text==ths.eq(6).text()){
                    $(this).prop("selected","selected")
                }
            });
        }
        function delate(obj) {
            var id=$(obj).parent().parent().children().eq(0).text();
            if(confirm("确定删除吗?")) {
                $.post("${url_delete_fixedteam}",{
                    fixedTeamId:id
                },function (date) {
                    alert(date);
                    location.reload();
                })
            }
        }
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
                <ul class="nav nav-tabs" id="hehedadada">
                    <%@include file="training_topbar.jsp"%>
                </ul>
            </div>
            <div class="panel-body">
                <c:if test="${(!empty user) && (user.isAdmin())}">
                    <div class="row" style="padding-left: 20px">
                        <div class="pull-left">
                            <a class="btn btn-info btn-sm"  data-toggle="modal" data-target="#modifyModel" id="tianjia">添加队伍</a>
                            <button class="btn btn-info btn-sm" id="update_Rating">更新积分</button>
                        </div>
                    </div>
                    <hr style="margin:10px "/>
                </c:if>
                <table class="table table-condensed table-striped table-hover display" id="mytable1">
                    <thead class="tab-header-area">
                    <tr>
                        <th style="display: none">ID</th>
                        <th>英文队名</th>
                        <th>中文队名</th>
                        <th>VJ账号</th>
                        <th>队员1</th>
                        <th>队员2</th>
                        <th>队员3</th>
                        <th>Score</th>
                        <th>Miu</th>
                        <th>Sigma</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${fixedList}" var="team">
                        <tr>
                            <td style="display: none">${team.id}</td>
                            <td>${team.name1}</td>
                            <td>${team.name2}</td>
                            <td>${team.vjname}</td>
                            <td>${userInfoMap.get(team.user1Id).realName}</td>
                            <td>${userInfoMap.get(team.user2Id).realName}</td>
                            <td>${userInfoMap.get(team.user3Id).realName}</td>
                            <td>${ratingMap.get(team.vjname).myRating}</td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(team.vjname).mean}"
                                    maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(team.vjname).standardDeviation}"
                                                  maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                <a data-toggle="modal" data-target="#modifyModel" onclick="updata(this)">编辑</a>
                                <a id="delete" onclick="delate(this)">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<%--添加队伍模态框--%>
<div class="modal fade" id="modifyModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body">
                <form class="form" >
                        <input type="text" id="ID" value="-1"  hidden >
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="英文队名*" id="E_teamname" required>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="中文队名" id="C_teamname" >
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="VJ账号" id="VJ_teamname" >
                    </div>
                    <div class="form-group">
                        <%--<input type="text" class="form-control" placeholder="队员1姓名" id="name1" >--%>
                        <label for="name1">队员1:</label>
                        <select class="form-control" id="name1">

                                <option value="-1">---请选择---</option>
                                <c:forEach items="${userList}" var="curUser">
                                    <option value="${curUser.id}">${curUser.realName}</option>
                                </c:forEach>

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name1">队员2:</label>
                        <select class="form-control" id="name2" >
                            <option value="-1">---请选择---</option>
                            <c:forEach items="${userList}" var="curUser">
                                <option value="${curUser.id}">${curUser.realName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name1">队员3:</label>
                        <select class="form-control" id="name3" >
                            <option value="-1">---请选择---</option>
                            <c:forEach items="${userList}" var="curUser">
                                <option value="${curUser.id}">${curUser.realName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="trueModel">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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
