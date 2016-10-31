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

    <c:url value="/auth/modifyUserByAdmin" var="url_modify"/>
    <c:url value="/statistics/updatedb" var="url_updatedb"/>
    <c:url value="/rating/updateGlobal" var="url_update_rating"/>
    <script>
        $(document).ready(function () {
            $("#id").hide();
            var table = $('#mytable').DataTable({
                "order": [[8, "desc"]],
                "columnDefs": [
                    {"type": "chinese-string", targets: 1},
                    {"contentPadding": "", targets: 1}
                ]
            });
            $('#savabutton').click(function () {
                $.post("${url_modify}", {
                    id: $('#id').val(),
                    username: $('#username').val(),
                    realName: $('#realname').val(),
                    major: $('#major').val(),
                    cfname: $('#cfname').val(),
                    vjname: $('#vjname').val(),
                    bcname: $('#bcname').val(),
                    uvaId: $('#uvaId').val(),
                    type: $('#status option:selected').val()
                }, function (data) {
                    alert(data);
                    location.reload();
                })
            });
            $('#addbutton').click(function () {
                $(this).attr("disabled", "disabled");
                $.post("${url_updatedb}", {}, function (data) {
                    alert(data);
                    location.reload()
                });
            });
            $('#update_Rating').click(function () {
                $(this).attr("disabled", "disabled");
                $.post("${url_update_rating}", {}, function (data) {
                    alert(data);
                    location.reload();
                });
            })
        });
        function updata(obj) {
            var tds = $(obj).parent().parent().find('td');
            $('#id').val(tds.eq(0).text());
            $('#username').val(tds.eq(1).text());
            $('#uvaId').val(tds.eq(2).text());
            $('#cfname').val(tds.eq(3).text());
            $('#vjname').val(tds.eq(4).text());
            $('#bcname').val(tds.eq(5).text());
            $('#realname').val(tds.eq(6).text());
            $('#major').val(tds.eq(7).text());
            var form_text = $.trim(tds.eq(13).text());
            $("#status option").each(function (i, item) {
                var option_text = $(this).text();
                if (option_text == form_text) {
                    $(this).prop("selected", "selected")
                }
            })
        }
    </script>
</head>
<body>

<div class="container-fluid" style="margin-right: 0.5%;margin-left: 0.5%">
    <jsp:include page="topBar.jsp"/>
    <div class="row" style="padding-bottom: 10px">
    </div>
    <c:if test="${(!empty user) and (user.isAdmin())}">
        <div class="row">
            <div class="pull-right">
                <button class="btn btn-info" id="addbutton">更新数据</button>
                <button class="btn btn-info" id="update_Rating">更新Rating</button>
            </div>
        </div>
    </c:if>
    <div class="row" style="padding-bottom: 10px">
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">队员比赛统计结果</h3>
            </div>
            <div class="panel-body" id="table-div">
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th hidden>ID</th>
                        <th hidden>用户名</th>
                        <th hidden>UVaId</th>
                        <th hidden>CfName</th>
                        <th hidden>VJName</th>
                        <th hidden>BCName</th>
                        <th>姓名</th>
                        <th>班级</th>
                        <th>Score</th>
                        <th>Base</th>
                        <th>Rating</th>
                        <th>Param</th>
                        <th>Match</th>
                        <th>状态</th>
                        <c:if test="${(!empty user) and (user.isAdmin())}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:set value="New" var="New"/>
                    <c:set value="Admin" var="Admin"/>
                    <c:set value="Verifying" var="Verifying"/>
                    <c:set value="Reject" var="Reject"/>
                    <c:set value="Acmer" var="Acmer"/>
                    <c:set value="Expeled" var="Expeled"/>
                    <c:set value="Retired" var="Retired"/>
                    <c:set value="Quit" var="Quit"/>
                    <c:set value="Coach" var="Coach"/>
                    <c:forEach items="${users}" var="curUser" varStatus="i">
                        <tr>
                            <td hidden>${curUser.id}</td>
                            <td hidden>${curUser.username}</td>
                            <td hidden>${curUser.uvaId}</td>
                            <td hidden>${curUser.cfname}</td>
                            <td hidden>${curUser.vjname}</td>
                            <td hidden>${curUser.bcname}</td>
                            <c:choose>
                                <c:when test="${!empty curUser.blogUrl}">
                                    <td><a href="${curUser.blogUrl}" target="_blank">${curUser.realName}</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td>${curUser.realName}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${curUser.major}</td>
                            <td>${ratingMap.get(curUser.realName).calcRating(playDuration.get(curUser.realName))}</td>
                            <td>
                                <fmt:formatNumber value="${playDuration.get(curUser.realName)/60}"
                                                  maxFractionDigits="0"
                                                  minFractionDigits="0"
                                                  groupingUsed="false"/>
                            </td>
                            <td>
                                    ${ratingMap.get(curUser.realName).myRating}
                            </td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(curUser.realName).mean}"
                                                  maxFractionDigits="2"
                                                  minFractionDigits="2"/>
                                (<fmt:formatNumber value="${ratingMap.get(curUser.realName).standardDeviation}"
                                                   maxFractionDigits="2"
                                                   minFractionDigits="2"/>)
                            </td>
                            <td>
                                <fmt:formatNumber value="${playcntMap.get(curUser.realName)}"
                                                  maxFractionDigits="0"
                                                  minFractionDigits="0"
                                                  groupingUsed="false"/>
                            </td>

                            <c:set value="${curUser.type.name()}" var="curType"/>
                            <td>
                                <c:choose>
                                    <c:when test="${curType eq New}">
                                        用户
                                    </c:when>
                                    <c:when test="${curType eq Verifying}">
                                        申请
                                    </c:when>
                                    <c:when test="${curType eq Reject}">
                                        拒绝
                                    </c:when>
                                    <c:when test="${curType eq Acmer}">
                                        队员
                                    </c:when>
                                    <c:when test="${curType eq Expeled}">
                                        除名
                                    </c:when>
                                    <c:when test="${curType eq Retired}">
                                        退役
                                    </c:when>
                                    <c:when test="${curType eq Quit}">
                                        退出
                                    </c:when>
                                    <c:when test="${curType eq Coach}">
                                        教练
                                    </c:when>
                                    <c:otherwise>
                                        未知
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <c:if test="${(!empty user) and (user.isAdmin())}">
                                <td>
                                    <a data-toggle="modal" data-target="#myModal" onclick="updata(this);">编辑</a>&nbsp;
                                    <c:url value="/auth/dealApplyInACM/${curUser.id}/1" var="url_y"/>
                                    <c:url value="/auth/dealApplyInACM/${curUser.id}/0" var="url_n"/>
                                    <c:if test="${curType eq Verifying}">
                                        <a href="${url_y}" target="_blank">Y</a>&nbsp;
                                        <a href="${url_n}" target="_blank">N</a>
                                    </c:if>
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


<jsp:include page="footerInfo.jsp"/>
<c:if test="${!empty tip}">
    <script>
        alert("${tip}");
    </script>
</c:if>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改用户信息</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post">
                    <!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" class="form-control" value="" placeholder="id" id="id" disabled>
                    </div>
                    <div class="form-group">
                        用户名:<input type="text" class="form-control" id="username" readonly>
                    </div>
                    <div class="form-group">
                        真实姓名:<input type="text" class="form-control" id="realname" required>
                    </div>
                    <div class="form-group">
                        专业:<input type="text" class="form-control" id="major" required>
                    </div>
                    <div class="form-group">
                        Codeforces 用户名:<input type="text" class="form-control" id="cfname">
                    </div>
                    <div class="form-group">
                        Virtual Judge 用户名:<input type="text" class="form-control" id="vjname">
                    </div>
                    <div class="form-group">
                        BestCoder 用户名:<input type="text" class="form-control" id="bcname">
                    </div>
                    <div class="form-group">
                        UVaId:<input class="form-control" id="uvaId" required>
                    </div>
                    <div class="form-group">
                        状态:<select class="form-control" id="status" required>
                        <option value="Retired">退役</option>
                        <option value="Expeled">除名</option>
                        <option value="Acmer">队员</option>
                        <option value="Reject">拒绝</option>
                        <option value="Verifying">申请</option>
                        <option value="New">用户</option>
                        <option value="Quit">退出</option>
                        <option value="Coach">教练</option>
                        <option value="Admin">管理员</option>
                    </select>
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

</body>
</html>
