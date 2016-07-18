<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <c:url value="/assign/doAssign" var="url_assign"/>
    <script>
        $(function () {
            $('#fulltrue').click(function () {
                $("[name='ckb']:checkbox").prop("checked",true).change();
            });
            $('#fullfalse').click(function () {
                $("[name='ckb']:checkbox").prop("checked",false).change();
            });
            $('#submit').click(function () {
                var id_array=new Array();
                $("[name='ckb']:checkbox").each(function () {
                    if($(this).prop("checked")==true)
                    id_array.push($(this).attr('id'));//向数组中添加元素
                });
                var id_list=id_array.join('_');
                if(id_array.length<=3) {

                    alert("请选择多于三个队员！");
                    return false
                }
                $('#test').attr("value",id_list);
            });
        })
    </script>
</head>
<body>

<jsp:include page="topBar.jsp"/>

<div class="container-fluid">

    <div class="col-lg-2">
    </div>
    <div class="col-lg-8">
        <div class="page-header">
            <h1>队员分组 <small>（随机）</small></h1>
        </div>
        <form class="form-horizontal" method="post" action="<c:url value="/assign/doAssign"/>">
                    <input id="test" name="text" type="text" value="" hidden/>
                    <div class="form-group">
                        <c:forEach items="${userList}" var="user">
                            <div name="box" class="checkbox col-lg-4" style="padding-bottom: 20px">
                                <label>
                                    <input type="checkbox" name="ckb" id="${user.id}"
                                           data-toggle="toggle" data-on="<i class='glyphicon glyphicon-ok'></i>"
                                           data-off="<i class='glyphicon glyphicon-remove'></i>"/>
                                           ${user.realName}(${user.username})
                                </label>
                            </div>
                        </c:forEach>
                    </div>
            <div class="form-group">
            <div class="form-horizontal pull-right">
            <input type="button" id="fulltrue" value="全选" class="btn btn-default"/>
            <input type="button" id="fullfalse" value="全不选" class="btn btn-default"/>
            <input type="submit" id="submit" value="提交" class="btn btn-primary"/>
            </div>
            </div>
        </form>
    </div>
    <div class="col-lg-2"></div>
</div>
</body>
</html>
