<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/7
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>登陆 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>

<body>



<div class="container-fluid"  style="margin-right: 3%;margin-left: 3%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>欢迎登陆
                    <small>ACManager</small>
                </h1>
            </div>

            <form class="form-horizontal" method="post" action="<c:url value="/auth/dologin"/> ">
                <div class="col-lg-6">
                    <div class="form-group">
                        <input type="text" name="username" id="inputUsername" class="form-control" placeholder="UserName"
                               required autofocus>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" id="inputPassword" class="form-control"
                               placeholder="Password"
                               required>
                    </div>
                    <c:if test="${!empty tip}">
                        <span id="msg">${tip}</span>
                    </c:if>

                    <div id="embed-captcha"></div>
                    <p id="wait" class="show alert alert-info" role="alert">正在加载验证码......</p>
                    <div style="padding-bottom: 15px"></div>
                    <p id="notice" class="hide alert alert-danger" role="alert">请先拖动验证码到相应位置</p>

                    <div style="padding-bottom: 20px">
                    </div>

                    <div class="form-horizontal pull-right">
                        <button class="btn btn-primary" id="embed-submit" type="submit">登陆</button>
                        <button class="btn btn-primary" id="reset" type="reset">重置</button>
                    </div>
                </div>
            </form>
        </div>
        </div>
        <div class="col-lg-2">
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
<c:url value="/auth/startGeetest" var="url_stGeetest"/>

<script>

    var handlerEmbed = function (captchaObj) {
        $("#embed-submit").click(function (e) {
            var validate = captchaObj.getValidate();
            if (!validate) {
                $("#notice")[0].className = "show alert alert-danger";
                setTimeout(function () {
                    $("#notice")[0].className = "hide alert alert-danger";
                }, 2000);
                e.preventDefault();
            }
        });

        // 将验证码加到id为captcha的元素里
        captchaObj.appendTo("#embed-captcha");

        captchaObj.onReady(function () {
            $("#wait")[0].className = "hide";
        });

        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
    };
    $.ajax({
        // 获取id，challenge，success（是否启用failback）
        url: "${url_stGeetest}",
        type: "get",
        dataType: "json",
        success: function (data) {

            // 使用initGeetest接口
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                product: "embed", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
                offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
            }, handlerEmbed);
        }
    });

</script>

</body>
</html>
