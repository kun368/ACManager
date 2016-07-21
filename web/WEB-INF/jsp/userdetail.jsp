<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/7
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <c:url value="/" var="url_index"/>
    <c:url value="/auth/validUsername" var="url_valid"/>
    <script type="text/javascript">
        //传数据库
        function Goto() {
            self.location = '${url_index}';
        }
        $(function () {

            $('#OldPassword').blur(function () {
                var oldpassword = $(this).val();

                if ( oldpassword != ${user.password})

                        $('#checkOldPassword').html("旧密码错误");
            });
            $('#inputRepass').blur(function () {
                var pa = $('#inputPassword').val();
                var ck = $('#accheck');
                var rep = $('#inputRepass').val();
                if (rep != '' || pa != '')
                    if (rep === pa) {
                    }
                    else {
                        ck.html('两次密码输不入一致');
                    }
            });
            $('#OldPassword').click(function () {
                var ck = $('#checkOldPassword');
                ck.html('');
            });
            $('#inputRepass,#inputPassword').click(function () {
                var ck = $('#accheck');
                ck.html('');
            });

        });
    </script>
    <script>

        function mySubmit(flag) {
            return flag;
        }
        $("#myform").submit(function () {
            var pa = $('#inputPassword').val();
            var rep = $('#inputRepass').val();
            if (OldPassword != ${user.password} || rep != pa) {
                alert("表单填写错误");
                return mySubmit(false);
            } else {
                return mySubmit(true);
            }
        });
    </script>
</head>

<body>

<jsp:include page="topBar.jsp"/>
<div class="container-fluid">
    <div class="col-lg-2">
    </div>
    <div class="col-lg-8">
        <div class="page-header">
            <h1>个人详情
                <small>修改</small>
            </h1>
        </div>
        <form class="form-horizontal" id="myform" method="post" action="<c:url value="/auth/doModify"/> "
              onsubmit="return mysubmit(true)">
            <div class="col-lg-6">
                <input value="${user.id}" name="id" hidden>
                <div class="form-group">
                    用户名：<input type="text" name="username" id="inputUserName" class="form-control" value="${user.username}" readonly>
                </div>
                <div class="form-group">
                    旧密码:<input type="password" name="oldpassword" id="OldPassword" class="form-control"
                               placeholder="Oldpassword*" required>
                </div>
                <p id="checkOldPassword"></p>
                <div class="form-group">
                    新密码:<input type="password" name="password" id="inputPassword" class="form-control"
                               placeholder="Newpassword*" required>
                </div>
                <div class="form-group">
                    重复密码:<input type="password" id="inputRepass" class="form-control" placeholder="Repeat*" required>
                </div>
                <p id="accheck"></p>

                <div class="form-group">
                    UVA ID:<input type="text" name="uvaId" id="inputUVA" class="form-control" value="${user.uvaId}">
                </div>
                <!--<p>UVA UserName:</p>-->
                <div class="form-group">
                    CF用户名:<input type="text" name="cfname" id="inputCF" class="form-control" value="${user.cfname}">
                </div>
                <div class="form-group">
                    真实姓名:<input type="text" name="realName" id="inputRealName" class="form-control"
                                value="${user.realName}">
                </div>
                <div class="form-group">
                    班级:<input type="text" name="major" id="inputMajor" class="form-control disabled"
                              value="${user.major}">
                </div>
                <div class="form-horizontal pull-right form-group">
                    <button class="btn btn-primary " type="submit">修 改</button>
                    <button class="btn btn-primary" type="button" onclick="Goto()">取 消</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-lg-2">

    </div>
</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>

<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>
</body>
</html>

