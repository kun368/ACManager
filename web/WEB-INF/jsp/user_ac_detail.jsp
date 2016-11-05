<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/11
  Time: 7:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        .oj {
            color: green;
            font-weight: bold;
            text-align: center;
        }

        .th_title {
            text-align: center;
        }
    </style>
    <title>${curUser.realName} 做题统计 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.1.0/css/responsive.dataTables.min.css">
    <script src="https://cdn.datatables.net/responsive/2.1.0/js/dataTables.responsive.min.js"></script>
    <script src="//cdn.datatables.net/plug-ins/1.10.12/sorting/chinese-string.js"></script>
    <c:url value="/api/userac/${username}/list" var="apiList"/>
    <c:url value="/api/userac/url/" var="urlapi"/>
    <script>
        $(function () {
            $.ajax({
                url: "${apiList}",
                data: {},
                dataType: "json",
                type: "GET",
                success: function (data) {
                    var ojs = data.ojs;
                    var max_number = 0;
                    $.each(data.ac, function (name, value) {
                        var t = value;
                        var number = t.length;
                        max_number += number;
                        var dataName = "<div  class='col-lg-3 oj'><div>" +
                                name + "&nbsp;(" + number +
                                ")</div></div>";
                        var dataForm = "<div  class='col-lg-9' id='" + name + "'>" + "<table class='table table-condensed display'>";
                        t = t.sort(function (a, b) {
                            return a - b;
                        });
                        var td_numb = 15;
                        for (var j = 0; j < t.length; j++) {
                            var modv = j % td_numb;
                            if (modv == 0) dataForm += "<tr>";
                            dataForm += "<td style='width:75px'><a class='click_url' href='javascript:;'" +
                                    "onmouseover='onmouseover_url(this)' >" + t[j] + "</a></td>";
                            if (j == t.length - 1 && modv != td_numb - 1) {
                                for (var x = modv; x < td_numb; x++)
                                    dataForm += "<td>&nbsp;</td>";
                                dataForm += "</tr>";
                            }
                            else if (modv == td_numb - 1) dataForm += "</tr>";
                        }
                        dataForm += "</table></div>";
                        $('#t_body').append("<div>" + dataName + dataForm + "</div>");
                    });
                    $('#t_body').children().each(function () {
                        var ht = $(this).children().eq(1).outerHeight();
                        $(this).children().eq(0).outerHeight(ht);
                        $(this).children().eq(0).children().css("line-height", ht + "px");
                    });
                    $('#solve_number').html("(" + max_number + ")");


                }
            });
        });
        function onmouseover_url(obj) {
            var this_obj = $(obj);
            var name = this_obj.parent().parent().parent().parent().parent().eq(0).attr("id");
            var numb = this_obj.text();
            var url = this_obj.attr("href");
            if (url == "javascript:;")
                $.ajax({
                    url: "${urlapi}" + name + "/" + numb,
                    data: {},
                    dataType: "text",
                    type: "GET",
                    success: function (data) {
                        this_obj.attr("target", "_blank");
                        this_obj.attr("href", data);
                    },
                    error: function (data) {
                        this_obj.attr("href", "#");
                    }
                });
        }

    </script>
</head>
<body>

<div class="container-fluid" style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div style="padding-top: 20px"></div>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">${curUser.realName} 做题统计</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped display"
                       id="mytable" role="grid"
                       style="table-layout:fixed;">
                    <thead>
                    <tr role="row">
                        <div class="col-lg-3"><h4 class="th_title">OJ名称</h4></div>
                        <div class="col-lg-9"><h4 class="th_title">解决题目&nbsp;<span id="solve_number"></span></h4></div>
                    </tr>
                    </thead>

                    <tbody id="t_body">
                    </tbody>
                </table>
            </div>

        </div>

    </div>
</div>
</body>
</html>
