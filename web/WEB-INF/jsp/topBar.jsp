<%--
  Created by IntelliJ IDEA.
  User: kun
  Date: 2016/7/14
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<link  rel="stylesheet" href="https://cdn.datatables.net/responsive/2.1.0/css/responsive.dataTables.min.css">--%>
<%--<script src="https://cdn.datatables.net/responsive/2.1.0/js/dataTables.responsive.min.js"></script>--%>
<%--<script src="//cdn.datatables.net/plug-ins/1.10.12/sorting/chinese-string.js"></script>--%>
<script>
    jQuery.extend( jQuery.fn.dataTableExt.oSort, {
        "chinese-string-asc" : function (s1, s2) {
            return s1.localeCompare(s2);
        },
        "chinese-string-desc" : function (s1, s2) {
            return s2.localeCompare(s1);
        }
    } );
</script>
<script>
    $.extend( $.fn.dataTable.defaults, {
        lengthChange: true,
        ordering: true,
        processing: true,
        searching:true,
        stateSave: true,<!--状态保存-->
        stateDuration:-1,
        pageLength: 50,<!--初始化单页显示数-->
        orderClasses: false,<!--排序列不高亮显示-->
        order:[],
        dom: '<"top"if>rt<"bottom"lp>',
        responsive: true
    } );
</script>
<c:url value="/" var="url_index"/>
<c:url value="/uva/showTable" var="url_uvaTable"/>
<c:url value="/auth/login" var="url_login"/>
<c:url value="/auth/rg" var="url_rg"/>
<c:url value="/training/list" var="url_traininglist"/>
<style>
    body {
        font-family:"Microsoft Yahei",微软雅黑,Consolas,Arial,sans-serif;
    }
</style>
<div class="row">
    <nav class="nav navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="navbar-brand"> ACManager</div>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li> <a href="${url_index}">首页</a> </li>
                    <li> <a href="${url_uvaTable}">队员统计</a> </li>
                    <li> <a href="${url_traininglist}">集训管理</a> </li>
                    <li> <a href="<c:url value="/oj/recentContest"/> ">近期比赛</a> </li>
                </ul>
                <c:if test="${empty user}">
                    <div class="navbar-form navbar-right">
                        <a class="navbar-link" href="${url_login}">登录</a>
                        <a class="navbar-link" href="${url_rg}">注册</a>
                    </div>
                </c:if>
                <c:if test="${!empty user}">
                    <div class="navbar-form navbar-right">
                        <a class="navbar-link" href="<c:url value="/auth/my"/> ">${user.username}</a>
                        <a class="navbar-link" href="<c:url value="/auth/dologout"/> ">退出</a>
                    </div>
                </c:if>

            </div>
        </div>
    </nav>
</div>
