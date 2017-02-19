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
    <title>比赛统计 - ACManager</title>
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
        $(document).ready(function () {
            $('#mytable').DataTable({
                columnDefs: [
                    //给第一列指定宽度为表格整个宽度的20%
                    { "width": "30px", "targets": 2 },
                { "type": "chinese-string", targets: 2}
                ],
                "order": [[3, "desc"]]
            });
        });
    </script>

    <script>
        function exportTable() {
            $("#mytable").table2excel({
                name: "doc1",
                filename: "比赛统计"
            });
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
            <li><a href="<c:url value="/training/detail/${contest.stage.training.id}"/> ">阶段列表</a></li>
            <li><a href="<c:url value="/training/stage/${contest.stage.id}"/> ">比赛列表</a></li>
            <li class="active">${contest.name}</li>
        </ol>
    </div>



    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">比赛统计</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-left: 20px">
                    <div class="pull-left">
                        <button class="btn btn-info btn-sm" onclick="exportTable()">导出表格</button>
                    </div>
                </div>
                <hr style="margin:10px "/>

                <p>比赛时间：${contest.startTimeStr} —— ${contest.endTimeStr}</p>
                <p>比赛类型：${contest.typeChs()}</p>
                <p>
                    比赛来源：
                    <c:choose>
                        <c:when test="${empty contest.sourceUrl}">
                                ${contest.sourceDetail}
                        </c:when>
                        <c:otherwise>
                            <a href="${contest.sourceUrl}" target="_blank">
                                ${contest.sourceDetail}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </p>
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                            <th>排名</th>
                            <th>比赛账号</th>
                            <th>成员</th>
                            <th>分数</th>
                            <th>罚时</th>
                            <c:set value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" var="ti"/>
                            <c:forEach begin="1" end="${contest.pbCnt}" var="i">
                                <th><center>${ti.charAt(i-1)}</center></th>
                            </c:forEach>
                        <%--<th>操作</th>--%>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                        <c:forEach items="${ranks}" var="team" varStatus="i">
                            <c:if test="${!contest.realContest or team.localTeam}">
                                <tr>
                                    <td><center><strong>${myrank[i.index]}</strong></center></td>
                                    <td>${team.account}</td>
                                    <td>${team.memberToString()}</td>
                                    <td>
                                        <strong>
                                            <c:if test="${sum[i.index] != 0}">
                                                <fmt:formatNumber type="number" value="${sum[i.index]}" maxFractionDigits="2" minFractionDigits="2"/>
                                            </c:if>
                                        </strong>
                                    </td>
                                    <td>
                                        <center>
                                                ${team.solvedCount}
                                            <br/>
                                            <small>
                                                    ${team.calcSumPenaltyStr()}
                                            </small>
                                        </center>

                                    </td>

                                    <c:forEach begin="1" end="${contest.pbCnt}" var="j">
                                        <td>
                                            <center>
                                                    <%--没有wa太多扣除--%>
                                                <c:if test="${!waClear[i.index][j-1]}">
                                                    <c:if test="${pre[j-1][i.index] != 0}">
                                                        <fmt:formatNumber type="number" value="${pre[j-1][i.index]}" maxFractionDigits="2" minFractionDigits="2"/>
                                                    </c:if>
                                                </c:if>
                                                <br/>
                                                <small>
                                                        ${team.pbStatus.get(j-1).toHString()}
                                                </small>
                                            </center>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="footerInfo.jsp"/>
</body>
</html>
