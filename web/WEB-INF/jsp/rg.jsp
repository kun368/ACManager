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
            location.href = "${url_index}";<!--填写提交地址-->
        }
        $(function () {

            $('#inputUsername').blur(function () {
                var user = $('#inputUsername').val();
                var patten = /^[a-zA-Z]\w{2,15}$/ig;

                if ((user != ""))

                    if (!patten.test(user)) {
                        $('#checkname').html("输入用户名不合法,首字母应为字母，长度在3-16之间");
                    }
                    else {
                        $('#inputUsername').blur(function () {
                            $.post('${url_valid}',
                                    {
                                        name: $('#inputUsername').val()
                                    }, function (data) {
                                        if (data == "true") {
                                        }
                                        else {
                                            $('#checkname').html("用户名已经注册");
                                        }
                                    });
                        });
                    }
            });
            $('#inputUsername').click(function () {
                var ck = $('#checkname');
                ck.html('');
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
            $('#inputRepass,#inputPassword').click(function () {
                var ck = $('#accheck');
                ck.html('');
            });
            $('#rgform').submit(function () {
               var cname=$('#checkname').html();
                var ps=$('#inputPassword').var();
                var rc=$('#inputRepass').var();
                if(cname!=""||(ps!=rc)){return false;}
            });

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
            <h1>欢迎注册
                <small>ACManager</small>
            </h1>
        </div>
        <form class="form-horizontal" method="post" action="<c:url value="/auth/dorg"/> " id="rgform">
            <div class="col-lg-6">
                <div class="form-group">
                    <input type="text" name="username" id="inputUsername" class="form-control" placeholder="用户名*"
                           required autofocus>
                </div>
                <p id="checkname"></p>
                <div class="form-group">
                    <input type="password" name="password" id="inputPassword" class="form-control"
                           placeholder="密码*" required>
                </div>
                <div class="form-group">
                    <input type="password" id="inputRepass" class="form-control" placeholder="重复密码*" required>
                </div>
                <p id="accheck"></p>

                <div class="form-group">
                    <input type="text" name="major" id="class" class="form-control" placeholder="班级">
                </div>

                <div class="form-group">
                    <input type="text" name="uvaid" id="inpu" class="form-control" placeholder="UVAID">
                </div>
                <!--<p>UVA UserName:</p>-->
                <div class="form-group">
                    <input type="text" name="cfname" id="input" class="form-control" placeholder="CF 用户名">
                </div>
                <div class="form-group">
                    <input type="text" name="realName" id="inputRealName" class="form-control" placeholder="真实姓名">
                </div>
                    <div class="form-horizontal pull-right">
                        <button class="btn btn-primary " type="submit">注册</button>
                        <button class="btn btn-primary " type="button" onclick="Goto()">取消</button>
                    </div>
            </div>
        </form>
    </div>
    <div class="col-lg-2">
    </div>
</div> <!-- /container -->


<%--<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->--%>
<%--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>--%>
<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>
</body>
</html>

