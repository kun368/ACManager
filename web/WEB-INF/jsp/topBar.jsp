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
    /*
     *  jQuery table2excel - v1.0.2
     *  jQuery plugin to export an .xls file in browser from an HTML table
     *  https://github.com/rainabba/jquery-table2excel
     *
     *  Made by rainabba
     *  Under MIT License
     */
    !function(a,b,c,d){function e(b,c){this.element=b,this.settings=a.extend({},h,c),this._defaults=h,this._name=g,this.init()}function f(a){return(a.filename?a.filename:"table2excel")+(a.fileext?a.fileext:".xlsx")}var g="table2excel",h={exclude:".noExl",name:"Table2Excel"};e.prototype={init:function(){var b=this,c='<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';b.template={head:'<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">'+c+"<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets>",sheet:{head:"<x:ExcelWorksheet><x:Name>",tail:"</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>"},mid:"</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>",table:{head:"<table>",tail:"</table>"},foot:"</body></html>"},b.tableRows=[],a(b.element).each(function(c,d){var e="";a(d).find("tr").not(b.settings.exclude).each(function(b,c){e+="<tr>"+a(c).html()+"</tr>"}),b.tableRows.push(e)}),b.tableToExcel(b.tableRows,b.settings.name,b.settings.sheetName)},tableToExcel:function(d,e,g){var h,i,j,k=this,l="";if(k.uri="data:application/vnd.ms-excel;base64,",k.base64=function(a){return b.btoa(unescape(encodeURIComponent(a)))},k.format=function(a,b){return a.replace(/{(\w+)}/g,function(a,c){return b[c]})},g="undefined"==typeof g?"Sheet":g,k.ctx={worksheet:e||"Worksheet",table:d,sheetName:g},l=k.template.head,a.isArray(d))for(h in d)l+=k.template.sheet.head+g+h+k.template.sheet.tail;if(l+=k.template.mid,a.isArray(d))for(h in d)l+=k.template.table.head+"{table"+h+"}"+k.template.table.tail;l+=k.template.foot;for(h in d)k.ctx["table"+h]=d[h];if(delete k.ctx.table,"undefined"!=typeof msie&&msie>0||navigator.userAgent.match(/Trident.*rv\:11\./))if("undefined"!=typeof Blob){for(var h in d){var r=eval("/{table["+h+"]}/");l=l.replace(r, d[h]);}l=[l];var m=new Blob(l,{type:"text/html"});b.navigator.msSaveBlob(m,f(k.settings))}else txtArea1.document.open("text/html","replace"),txtArea1.document.write(k.format(l,k.ctx)),txtArea1.document.close(),txtArea1.focus(),sa=txtArea1.document.execCommand("SaveAs",!0,f(k.settings));else i=k.uri+k.base64(k.format(l,k.ctx)),j=c.createElement("a"),j.download=f(k.settings),j.href=i,c.body.appendChild(j),j.click(),c.body.removeChild(j);return!0}},a.fn[g]=function(b){var c=this;return c.each(function(){a.data(c,"plugin_"+g)||a.data(c,"plugin_"+g,new e(this,b))}),c}}(jQuery,window,document);
</script>

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
        lengthMenu: [[10, 20, 50, 100, -1], [10, 20, 50, 100, "全部"]],
        pageLength: 50,<!--初始化单页显示数-->
        orderClasses: false,<!--排序列不高亮显示-->
        order:[],
        dom: '<"top"if>rt<"bottom"lp>',
        responsive: true
    } );
</script>
<c:url value="/" var="url_index"/>
<c:url value="/statistics/showTable" var="url_uvaTable"/>
<c:url value="/userac/showTable" var="url_table_userac"/>
<c:url value="/auth/login" var="url_login"/>
<c:url value="/auth/rg" var="url_rg"/>
<c:url value="/training/list" var="url_traininglist"/>
<c:url value="/cpt/list" var="url_cpt_list"/>
<style>
    body {
        font-family:"Microsoft Yahei",微软雅黑,Arial,Consolas,sans-serif;
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
                    <li> <a href="${url_table_userac}">做题统计</a> </li>
                    <li> <a href="${url_uvaTable}">比赛统计</a> </li>
                    <li> <a href="${url_cpt_list}">专题训练</a></li>
                    <li> <a href="${url_traininglist}">集训管理</a> </li>
                    <li> <a href="<c:url value="/acmer/infos"/> ">队员去向</a> </li>
                    <li> <a href="<c:url value="/oj/recentContest"/> ">近期比赛</a> </li>
                    <li> <a href="https://www.eoapi.cn/#/share/login?shareCode=ZE1ddL" target="_blank">API</a></li>
                </ul>
                <c:if test="${empty user}">
                    <div class="navbar-form navbar-right">
                        <a class="navbar-link" href="${url_login}">登录</a>
                        <a class="navbar-link" href="${url_rg}">注册</a>
                    </div>
                </c:if>
                <c:if test="${!empty user}">
                    <div class="navbar-form navbar-right">
                        <c:choose>
                            <c:when test="${user.isAdmin()}">
                                <%--管理员至尊红名显示--%>
                                <a class="navbar-link" href="<c:url value="/auth/my"/> "
                                   style="color: red; font-weight: bold">
                                    ${user.username}
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="navbar-link" href="<c:url value="/auth/my"/> ">${user.username}</a>
                            </c:otherwise>
                        </c:choose>
                        <a class="navbar-link" href="<c:url value="/auth/dologout"/> ">退出</a>
                    </div>
                </c:if>

            </div>
        </div>
    </nav>
</div>
