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
    <title>用户注册 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://static.geetest.com/static/tools/gt.js"></script>
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

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
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
                        <input type="text" name="major" id="class" class="form-control" placeholder="班级*(格式示例：软件工程14-3)" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="realName" id="inputRealName" class="form-control" placeholder="真实姓名*" required>
                    </div>

                    <div class="form-group">
                        <div id="embed-captcha"></div>
                        <p id="wait" class="show alert alert-info" role="alert">正在加载验证码......</p>
                        <div style="padding-bottom: 15px"></div>
                        <p id="notice" class="hide alert alert-danger" role="alert">请先拖动验证码到相应位置</p>

                        <div style="padding-bottom: 20px">
                        </div>
                    </div>

                    <div class="form-horizontal pull-right">
                        <button class="btn btn-primary" id="embed-submit" type="submit">注册</button>
                        <button class="btn btn-primary" type="button" onclick="Goto()">取消</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-lg-2">
        </div>
    </div>
</div> <!-- /container -->


<%--<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->--%>
<%--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>--%>
<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>

<c:url value="/auth/startGeetest" var="url_stGeetest"/>
<script>

    var handlerEmbed = function (captchaObj) {
        $("#embed-submit").click(function (e) {
            var validate = captchaObj.getValidate();
            if (!validate) {
                $("#notice")[0].className = "show";
                setTimeout(function () {
                    $("#notice")[0].className = "hide";
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


<jsp:include page="footerInfo.jsp"/>

</body>
</html>

