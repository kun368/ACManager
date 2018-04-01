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
    <script src="//cdn.bootcss.com/js-sha1/0.3.0/sha1.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <c:url value="/" var="url_index"/>
    <c:url value="/auth/validUsername" var="url_valid"/>
    <script type="text/javascript">
        function check() {
            var pre = $('#OldPassword').val();
            var pa = $('#inputPassword').val();
            var rep = $('#inputRepass').val();
            if(sha1(pre) != "${user.password}") {
                alert("原密码错误！");
                return false;
            }
            if(rep != pa) {
                alert("表单填写错误");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>


<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>

    <div style="padding-top: 20px"></div>

    <div class="row" style="margin-right: 12%;margin-left: 12%">
        <h2>${user.username} 的个人中心</h2>
        <hr/>
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
                    申请入队需满足集训队入队条件，并保证信息按照格式填写正确无误，申请后联系管理员进行审核。<br/>
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

        <div style="padding-top: 10px"></div>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">个人导航</h3>
            </div>
            <div class="panel-body">
                <c:url value="/userac/${user.username}/list" var="aclist_url"/>
                <a href="${aclist_url}" target="_blank" class="btn btn-default btn-sm">AC汇总</a>
                <a href="${user.blogUrl}" target="_blank" class="btn btn-default btn-sm">博客</a>
                <a href="http://codeforces.com/profile/${user.cfname}" target="_blank" class="btn btn-default btn-sm">Codeforces</a>
                <a href="http://bestcoder.hdu.edu.cn/rating.php?user=${user.bcname}" target="_blank" class="btn btn-default btn-sm">BestCoder</a>
                <a href="http://acm.hdu.edu.cn/userstatus.php?user=${user.hduName}" target="_blank" class="btn btn-default btn-sm">HDU</a>
                <a href="http://poj.org/userstatus?user_id=${user.pojName}" target="_blank" class="btn btn-default btn-sm">POJ</a>
                <a href="https://vjudge.net/user/${user.vjname}" target="_blank" class="btn btn-default btn-sm">VJudge</a>
            </div>
        </div>

        <div style="padding-top: 10px"></div>

        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">修改信息</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" id="myform" method="post" action="<c:url value="/auth/doModify"/> "
                      onsubmit="return mysubmit(true)">
                    <div class="col-lg-12">
                        <input value="${user.id}" name="id" hidden>
                        <div class="form-group">
                            用户名：<input type="text" name="username" id="inputUserName" class="form-control" value="${user.username}" readonly>
                        </div>

                        <div class="form-group">
                            UVAID:<input type="text" name="uvaId" id="inputUVA" class="form-control" value="${user.uvaId}"
                                         placeholder="6位数 (登陆UVaOJ -> My Accounts -> Online Judge ID)">
                        </div>
                        <!--<p>UVA UserName:</p>-->
                        <div class="form-group">
                            Codeforces用户名:<input type="text" name="cfname" id="inputCF" class="form-control" value="${user.cfname}">
                        </div>
                        <div class="form-group">
                            BestCoder用户名:<input type="text" name="bcname" id="inputBC" class="form-control" value="${user.bcname}">
                        </div>
                        <div class="form-group">
                            VJudge用户名:<input type="text" name="vjname" id="inputVJ" class="form-control" value="${user.vjname}">
                        </div>
                        <div class="form-group">
                            HDOJ用户名:<input type="text" name="hduName" id="inputHDU" class="form-control" value="${user.hduName}">
                        </div>
                        <div class="form-group">
                            POJ用户名:<input type="text" name="pojName" id="inputPOJ" class="form-control" value="${user.pojName}">
                        </div>
                        <div class="form-group">
                            真实姓名:<input type="text" name="realName" id="inputRealName" class="form-control"
                                        value="${user.realName}">
                        </div>
                        <div class="form-group">
                            班级:<input type="text" name="major" id="inputMajor" class="form-control disabled"
                                      value="${user.major}" placeholder="格式示例：软件工程14-3">
                        </div>
                        <div class="form-group">
                            博客地址:<input type="text" name="blogUrl" class="form-control disabled"
                                        value="${user.blogUrl}" placeholder="格式示例：http://blog.csdn.net/hahaha">
                        </div>
                        <div class="form-horizontal pull-right form-group">
                            <button class="btn btn-primary" type="submit">修 改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div style="padding-top: 10px"></div>

        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">修改密码</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" method="post"
                      action="<c:url value="/auth/doModifyUserPW"/> "
                      onsubmit="return check();">
                    <div class="col-lg-12">
                        <input value="${user.id}" name="id" hidden>
                        <div class="form-group">
                            旧密码:<input type="password" name="oldpassword" id="OldPassword" class="form-control"
                                       placeholder="Oldpassword*" required>
                        </div>
                        <div class="form-group">
                            新密码:<input type="password" name="password" id="inputPassword" class="form-control"
                                       placeholder="Newpassword*" required>
                        </div>
                        <div class="form-group">
                            重复新密码:<input type="password" id="inputRepass" class="form-control" placeholder="Repeat*" required>
                        </div>
                        <p id="accheck"></p>
                        <div class="form-horizontal pull-right form-group">
                            <button class="btn btn-danger" type="submit">修 改</button>
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
