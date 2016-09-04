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
    <title>分队结果 - ACManager</title>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <c:url value="/assign/canAssign" var="url_canAssign"/>
    <c:url value="/assign/listTraining/${trainingId}" var="url_list"/>
    <script>
        $(function () {
            $('#make_sure').click(function ()
            {
                $.post("${url_canAssign}",
                        {},
                         function (data) {
                            $('#make_sure').addClass('disabled');
                        });
            });
            $('#make_sure').ajaxError(function (event,xhr,options,exc) {
                    console.log(event);
                    console.log(xhr);
                    console.log(options);
                    console.log(exc);
            })
            $('#again').click(function () {
                location.href="${url_list}"
            })
        })

    </script>
</head>
<body>

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li><a href="<c:url value="/training/detail/${trainingId}"/> ">当前集训</a></li>
            <li><a href="<c:url value="/assign/listTraining/${trainingId}"/> ">随机分队</a></li>
            <li class="active">分队结果</li>
        </ol>
    </div>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="row">

                <div class="page-header">
                    <h3>&nbsp;&nbsp;分组情况：</h3>
                </div>
                <div class="col-lg-10">
                    <br/>
                    <div class="col-lg-12 form-group">
                        <ul class="list-group">
                            <c:forEach items="${teamList}" var="team" varStatus="i">
                                <li class="list-group-item">
                                    <span class="badge">${team.size()}</span>
                                    <c:forEach items="${team}" var="member">
                                        ${userInfo[member].realName}&nbsp;&nbsp;
                                    </c:forEach>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-lg-3"></div>

                    <div class="form-group form-horizontal pull-right">
                        <button class="btn btn-primary " type="button" id="again">重新分组</button>
                        <button class="btn btn-primary " type="button" id="make_sure">确认分组</button>
                    </div>
                </div>
            </div>
            <div class="col-lg-2">
            </div>
        </div>
    </div>
</div>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
