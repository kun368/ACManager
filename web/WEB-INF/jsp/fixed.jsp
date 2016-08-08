<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/14
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>固定分队 - ACManager</title>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <c:url value="/assign/canAssign" var="url_canAssign"/>
    <c:url value="/assign/listTraining/${trainingId}" var="url_list"/>
    <script>
        $(document).ready(function () {
            function updata(id) {
                $('#teamname').val();
                $('#name1').val();
                $('#name2').val();
                $('#name3').val();
            }

            $('#trueModel').click(function () {
                $.ajax({
                    url: "",
                    type: "post",
                    data: {
                        teamname: $('#teamname').val(),
                        name1: $('#name1').val(),
                        name2: $('#name2').val(),
                        name3: $('#name3').val()
                    },
                    success: function (data) {
                        alert("修改成功");
                    },
                    error: function () {
                        alert("添加失败");
                    }
                });
            });
        });
    </script>
</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li><a href="<c:url value="/training/detail/${trainingId}"/> ">当前集训</a></li>
            <li class="active">固定分队</li>
        </ol>
    </div>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="row">

                <div class="page-header">
                    <h3>&nbsp;&nbsp;固定分队</h3>
                </div>
                <div class="col-md-10">
                    <br/>
                    <div class="col-md-12 form-group">
                        <ul class="list-group">
                            <c:forEach items="${fixedList}" var="curTeam" varStatus="i">
                                <li class="list-group-item">
                                    ${curTeam.name1}
                                    <h4 class="list-group-item-heading">
                                        <span class="badge">${curTeam.name2}</span>
                                    </h4>
                                    <div style="padding-top: 15px"></div>
                                    <p class="list-group-item-text">
                                        <--!for 循环队员名称-->
                                        <c:forEach items="${curTeam.uids}" var="curUid">
                                            ${userMap.get(curUid)}&nbsp;
                                        </c:forEach>
                                        <div class="pull-right">
                                            <button class="btn btn-sm" id="modify" data-toggle="modal"
                                                    data-target="#modifyModel" onclick="updata(id)">修改
                                            </button>
                                            <button class="btn btn-sm" id="delate">删除</button>
                                        </div>
                                    </p>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-lg-3">
                    </div>

                </div>
            </div>
            <div class="row">
                <div class="pull-right">
                    <button target="_blank" class="btn btn-primary" data-toggle="modal" data-target="#modifyModel">添加
                    </button>
                </div>
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
<!-- Modal for 修改，添加-->
<div class="modal fade" id="modifyModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <form class="form-control">
                    <div class="form-group">
                        队伍名称：<input type="text" class="form-control" placeholder="队伍名称" id="teamname" required>
                    </div>
                    <div class="form-group">
                        队员1：<input type="text" class="form-control" placeholder="队员1姓名" id="name1" required>
                    </div>
                    <div class="form-group">
                        队员2：<input type="text" class="form-control" placeholder="队员2姓名" id="name2">
                    </div>
                    <div class="form-group">
                        队员3：<input type="text" class="form-control" placeholder="队员3姓名" id="name3">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="trueModel">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal for 删除-->
<div class="modal fade" id="modifyModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>

            </div>
            <div class="modal-body">
                <h4>您确定删除吗?</h4>
            </div>
            <div class="modal-footer">
                <a class="btn btn-primary" href="">确定</a> <--!删除路径-->
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
