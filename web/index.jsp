<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/7
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script>
        $(function () {
            $('#sign').click(function () {
                $.post('auth/login',
                        {username: $('#inputUsername').val(),
                        password: $('#inputPassword').val()
                        },function (data) {
                            if(data=="true"){
                            location.href="rg.jsp";
                            }
                            else{
                                alert("用户名或密码错误！");
                            }
                        });
            });

        });
    </script>

</head>

<body>

<div class="container">

    <form class="form-horizontal" method="post" action="">
        <h2 class="form-signin-heading">Please sign in</h2>
        <div class="form-group">
            <input type="text" id="inputUsername" class="form-control" placeholder="UserName" required autofocus >
        </div>
        <div class="form-group">
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        </div>
        <div class="form-group">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
            </div>
        </div>
        <span id="msg"></span>
        <div class="form-group">
            <button class="btn btn-lg btn-primary btn-block"  id="sign" type="submit" >Sign in</button>
        </div>
    </form>

</div> <!-- /container -->



</body></html>
