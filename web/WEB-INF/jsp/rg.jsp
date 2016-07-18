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

            $('input#inputUsername').blur(function () {
                var user = $('input#inputUsername').val();
                var patten = /^[a-zA-Z]\w{3,15}$/ig;

                if ((user != ""))

                    if (!patten.test(user)) {
                        $('#checkname').html("输入用户名不合法");
                    }
                    else {
                        $('#inputUsername').blur(function () {
                            $.post('${url_valid}',
                                    {
                                        name: $('#inputUsername').val()
                                    }, function (data) {
                                        if (data == "true") {
                                            alert("用户名可用！");
                                        }
                                        else {
                                            alert("用户名不可用 ！");
                                        }
                                    });
                        });
                    }
            });
            $('#inputUsername').click(function () {
                var ck = $('p#checkname');
                ck.html('');
            });
            $('input#inputRepass').blur(function () {
                var pa = $('input#inputPassword').val();
                var ck = $('p#accheck');
                var rep = $('input#inputRepass').val();
                if(rep!=''||pa!='')
                if (rep === pa ) {
                }
                else {
                    ck.html('两次密码输不入一致');
                }
            });
            $('#inputRepass,#inputPassword').click(function () {
                var ck = $('p#accheck');
                ck.html('');
            });

        });
    </script>
</head>

<body>

<div class="container-fluid">

    <form class="form-horizontal" method="post" action="<c:url value="/auth/dorg"/> ">
        <h2 class="form-signin-heading">Register</h2>
        <div class="col-lg-6">
            <div class="form-group">
                <input type="text" name="username" id="inputUsername" class="form-control" placeholder="UserName*" required autofocus>
            </div>
            <p id="checkname"></p>
            <div class="form-group">
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password*" required>
            </div>
            <div class="form-group">
                <input type="password" id="inputRepass" class="form-control" placeholder="Repeat*" required>
            </div>
            <span id="accheck" class=""/>

            <div class="form-group">
                <input type="text" name="uvaid" id="inputUVA" class="form-control" placeholder="UVAID">
            </div>
            <!--<p>UVA UserName:</p>-->
            <div class="form-group">
                <input type="text" name="cfname" id="inputCF" class="form-control" placeholder="CF NikeName">
            </div>
            <div class="form-group">
                <input type="text" name="realName" id="inputRealName" class="form-control" placeholder="RealName">
            </div>
            <div class="col-lg-5">
                <div class="form-group">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </div>
            </div>
            <div class="col-lg-5 pull-right">
                <div class="form-group">
                    <button class="btn btn-lg btn-primary btn-block" type="button" onclick="Goto()"> 取 消</button>
                </div>
            </div>
        </div>
    </form>

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

