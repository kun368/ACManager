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
    <title>个人中心 - ACManager</title>
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
        $("#myform1").submit(function () {
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


<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row" style="padding-top: 15px; padding-bottom: 15px">
    </div>

    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">进队审核状态</h3>
            </div>
            <div class="panel-body">
                <c:set value="${user.type.name()}" var="curStatus"/>
                <c:set value="New" var="New"/>
                <c:set value="Admin" var="Admin"/>
                <c:set value="Verifying" var="Verifying"/>
                <c:set value="Reject" var="Reject"/>
                <c:set value="Acmer" var="Acmer"/>
                <div style="padding-bottom: 10px">
                    您的当前状态：${curStatus} <br/>
                    申请入队需保证信息按照格式填写正确无误，班级样例：软件14-3<br/>
                    入队后不能修改个人信息
                </div>
                <c:url value="/auth/applyInACM/${user.id}" var="url_applyin"/>
                <c:if test="${curStatus eq New}">
                    <a class="btn btn-primary" href="${url_applyin}">申请进队</a>
                </c:if>
                <c:if test="${curStatus eq Reject}">
                    <a class="btn btn-primary" href="${url_applyin}">重新申请进队</a>
                </c:if>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">信息修改</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" id="myform" method="post" action="<c:url value="/auth/doModify"/> "
                      onsubmit="return mysubmit(true)">
                    <div class="col-lg-6">
                        <input value="${user.id}" name="id" hidden>
                        <div class="form-group">
                            用户名：<input type="text" name="username" id="inputUserName" class="form-control" value="${user.username}" readonly>
                        </div>

                        <div class="form-group">
                            UVA ID:<input type="text" name="uvaId" id="inputUVA" class="form-control" value="${user.uvaId}">
                        </div>
                        <!--<p>UVA UserName:</p>-->
                        <div class="form-group">
                            CF用户名:<input type="text" name="cfname" id="inputCF" class="form-control" value="${user.cfname}">
                        </div>
                        <div class="form-group">
                            BC用户名:<input type="text" name="bcname" id="inputBC" class="form-control" value="${user.bcname}">
                        </div>
                        <div class="form-group">
                            VJ用户名:<input type="text" name="vjname" id="inputVJ" class="form-control" value="${user.vjname}">
                        </div>
                        <div class="form-group">
                            HDUOJ账号:<input type="text" name="hduName" id="inputHDU" class="form-control" value="${user.hduName}">
                        </div>
                        <div class="form-group">
                            POJ账号:<input type="text" name="pojName" id="inputPOJ" class="form-control" value="${user.pojName}">
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
        </div>
        <br/>
        <br/>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"> 密码修改</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" id="myform1" method="post" action="<c:url value="/auth/doModifyUserPW"/> "
                      onsubmit="return mysubmit(true)">
                    <div class="col-lg-6">
                        <input value="${user.id}" name="id" hidden>
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
                        <div class="form-horizontal pull-right form-group">
                            <button class="btn btn-primary " type="submit">修 改</button>
                            <button class="btn btn-primary" type="button" onclick="Goto()">取 消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> <!-- /container -->


<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>

