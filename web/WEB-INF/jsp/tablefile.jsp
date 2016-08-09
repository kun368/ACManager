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
    <title>队员统计 - ACManager</title>
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
    <script>
        $(document).ready(function () {
            $("#id").hide();
            $('#mytable').DataTable({
                "order": [[2, "asc"]]
            });
            $('#savabutton').click(function () {
                $.post("${url_modify}", {
                    id: $('#id').val(),
                    username: $('#username').val(),
                    realName: $('#realname').val(),
                    major: $('#major').val(),
                    cfname: $('#cfname').val(),
                    vjname: $('#vjname').val(),
                    uvaId:$('#uvaId').val(),
                    type:$('#status option:selected').val()
                }, function (data) {
                    alert(data);
                    location.reload();
                })
            });
        });
        function updata(obj) {
            var tds=$(obj).parent().parent().find('td');
            $('#id').val(tds.eq(0).text());
            $('#realname').val(tds.eq(1).text());
            $('#username').val(tds.eq(2).text());
            $('#major').val(tds.eq(3).text());
            $('#uvaId').val(tds.eq(4).text());
            $('#cfname').val(tds.eq(5).text());
            $('#vjname').val(tds.eq(7).text());
            $('#status').val(tds.eq(11).text());
        }
    </script>
</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp" />
    <div class="row" style="padding-bottom: 10px">
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">队内统计结果</h3>
            </div>
            <div class="panel-body">
                <table class="table table-condensed table-striped table-hover display" id="mytable">
                    <thead class="tab-header-area">
                    <tr>
                        <th hidden>ID</th>
                        <th>姓名</th>
                        <th hidden>用户名</th>
                        <th>班级</th>
                        <th>UVaId</th>
                        <th hidden>CfName</th>
                        <th>Cf Rating</th>
                        <th hidden>VJName</th>
                        <th>合计</th>
                        <c:forEach items="${booksName}" var="bookname">
                            <th>${bookname}</th>
                        </c:forEach>
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
                            <td>${curUser.realName}</td>
                            <td hidden>${curUser.username}</td>
                            <td>${curUser.major}</td>
                            <td>${curUser.uvaId}</td>
                            <td hidden>${curUser.cfname}</td>
                            <td>
                                <a href="http://codeforces.com/profile/${curUser.cfname}" target="_blank">
                                    ${cfInfoMap.get(curUser.cfname).rating}
                                </a>
                            </td>
                            <td hidden>${curUser.vjname}</td>
                            <td>${bookCnt.get(i.index).get(0) + bookCnt.get(i.index).get(1)}</td>
                            <c:forEach items="${bookCnt.get(i.index)}" var="j">
                                <td>${j}</td>
                            </c:forEach>
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
                                    <a data-toggle="modal" data-target="#myModal" class="btn btn-sm btn-info"  onclick="updata(this);">编辑</a>&nbsp;
                                    <c:url value="/auth/dealApplyInACM/${curUser.id}/1" var="url_y"/>
                                    <c:url value="/auth/dealApplyInACM/${curUser.id}/0" var="url_n"/>
                                    <c:if test="${curType eq Verifying}">
                                        <a href="${url_y}">Y</a>&nbsp;
                                        <a href="${url_n}">N</a>
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
    <c:if test="${(!empty user) and (user.isAdmin())}">
        <div class="row">
            <div class="pull-left">
                <button class="btn btn-info" id="addbutton">更新数据&nbsp;(LastUpdate: ${lastUpdate})</button>
            </div>
        </div>
    </c:if>

</div>

<c:url value="/uva/updatedb" var="url_updatedb"/>
<script>
    $(document).ready(function () {
        $('#addbutton').click(function () {
            $.post("${url_updatedb}", {

                    },function (data) {
                        alert(data);
                        location.reload()
                    }
            );
        })
    });
</script>

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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改用户信息</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post">
                    <!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" class="form-control"  value="" placeholder="id" id="id" disabled>
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
                        CF用户名:<input type="text" class="form-control" id="cfname" >
                    </div>
                    <div class="form-group">
                        VJ用户名:<input type="text" class="form-control" id="vjname" >
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
