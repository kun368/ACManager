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
    <title>集训统计 - ACManager</title>
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
    <c:url value="/rating/updateTraining/${trainingId}" var="url_update_rating"/>

    <script>
        $(document).ready(function () {
            $('#mytable1').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                stateSave: true,<!--状态保存-->
                pageLength: 25,<!--初始化单页显示数-->
                orderClasses: false,<!--排序列不高亮显示-->
                dom: '<"top"if>rt<"bottom"lp>',
                responsive: true,
                order:[[2,'desc']],
                columnDefs: [
                    { "type": "chinese-string", targets: 0}
                ]
            });
            $('#update_Rating').click(function () {
                $(this).attr("disabled","disabled");
                $.post("${url_update_rating}",{
                },function(data){
                    alert(data);
                    location.reload();
                });
            });
            $('#duiyuan').addClass('active');
        });

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
                            <button class="btn btn-info btn-sm" id="update_Rating">更新Rating</button>
                        </div>
                    </div>
                    <hr style="margin:10px "/>
                </c:if>
                <table class="table table-condensed table-striped table-hover display" id="mytable1">
                    <thead class="tab-header-area">
                    <tr>
                        <th>姓名</th>
                        <th>班级</th>
                        <th>Score</th>
                        <th>Miu</th>
                        <th>Sigma</th>
                        <th>场次</th>
                        <th>平均Rank</th>
                        <th>参赛分</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${ujoinT}" var="user">
                        <tr>
                            <td>${user.realName}</td>
                            <td>${user.major}</td>
                            <td>${ratingMap.get(user.realName).myRating}</td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(user.realName).mean}"
                                                  maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(user.realName).standardDeviation}"
                                                  maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>${playcntMap.get(user.realName)}</td>
                            <td>
                                <fmt:formatNumber value="${averageRankMap.get(user.realName)}"
                                                  maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${durationMap.get(user.realName) / 15.0}"
                                                  maxFractionDigits="0" minFractionDigits="0"/>
                            </td>
                        </tr>
                    </c:forEach>
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
</body>
</html>
