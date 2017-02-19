<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/9
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>集训队员统计 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="http://d3js.org/d3.v3.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <c:url value="/rating/updateTraining/${trainingId}" var="url_update_rating"/>

    <style type="text/css">
        #container{
            background: #dddddd;
            width: 500px;
            height: 250px;
        }
        path{
            fill:none;
            stroke: #4682B4;
            stroke-width: 2;
        }
        .domain,.tick line{
            stroke:gray;
            stroke-width: 1;
        }
    </style>
    <script>
        $(document).ready(function () {
            $('#mytable1').DataTable({
                lengthChange: true,
                ordering: true,
                processing: true,
                searching:true,
                stateSave: true,<!--状态保存-->
                pageLength: 25,<!--初始化单页显示数-->
                orderClasses: false,<!--排序列不高亮显示-->
                dom: '<"top"if>rt<"bottom"lp>',
                order:[[3,'desc']],
                columnDefs: [
                    { "type": "chinese-string", targets: 0}
                ]
            });
            $('#update_Rating').click(function () {
                $(this).attr("disabled","disabled");
                $.post("${url_update_rating}",{
                },function(data){
                    alert(data);
                    location.reload();
                });
            });
            $('#duiyuan').addClass('active');
        });
        function updata(obj,id) {
            var tds=$(obj).parent().parent().find('td');
            var username=tds.eq(2).text();
            $.get("/ACManager/api/rating/training/"+id+"/username/"+username,{},function (data) {
                $("#my_container").html("")
                $("#my_container2").html("")
                json_data=$.parseJSON(data)
                var rate=new Array()
                var rank=new Array()
                $.each(json_data['result'],function (i,item) {
                    rate[i]=item.myRating
                    rank[i]=item.playRankSum/(i+1)
                })
                var width=500,
                        height=250,
                        margin={left:50,top:30,right:20,bottom:20},
                        g_width=width-margin.left-margin.right,
                        g_height=height-margin.top-margin.bottom;
                var svg = d3.select("#my_container").append("svg").attr("width",width).attr("height",height)
                var g = d3.select("svg").append("g").attr("transform","translate("+margin.left+","+margin.top+")")
                var scale_x = d3.scale.linear().domain([0,rate.length-1]).range([0,g_width])
                var scale_y = d3.scale.linear().domain([d3.min(rate),d3.max(rate)]).range([g_height,0])
                var line_generator = d3.svg.line()
                        .x(function (d,i) {return scale_x(i);})
                        .y(function (d) {return scale_y(d);})
                        .interpolate("cardinal")
                g.append("path").attr("d",line_generator(rate))

                g.selectAll("circle").data(rate).enter()
                        .append("svg:circle")
                        .attr("r", 4)
                        .attr("cx", function(d,i) { return scale_x(i)})
                        .attr("cy", function(d) { return scale_y(d)})

                var x_axis=d3.svg.axis().scale(scale_x),
                        y_axis=d3.svg.axis().scale(scale_y).orient("left")

                g.append("g").call(x_axis).attr("transform","translate(0,"+g_height+")")

                g.append("g")
                        .call(y_axis)
                        .append("text")
                        .text("Score")
                        .attr("transform","rotate(-90)")
                        .attr("text-anchor","end")
                        .attr("dy","1em")

                var svg2 = d3.select("#my_container2").append("svg").attr("width",width).attr("height",height)
                var g2 = svg2.append("g").attr("transform","translate("+margin.left+","+margin.top+")")
                var scale_x2 = d3.scale.linear().domain([0,rank.length-1]).range([0,g_width])
                var scale_y2 = d3.scale.linear().domain([d3.min(rank),d3.max(rank)]).range([g_height,0])
                var line_generator2 = d3.svg.line()
                        .x(function (d,i) {return scale_x2(i);})
                        .y(function (d) {return scale_y2(d);})
                        .interpolate("cardinal")
                g2.append("path").attr("d",line_generator2(rank))

                var x_axis2=d3.svg.axis().scale(scale_x2),
                        y_axis2=d3.svg.axis().scale(scale_y2).orient("left")

                g2.append("g").call(x_axis2).attr("transform","translate(0,"+g_height+")")

                g2.append("g")
                        .call(y_axis2)
                        .append("text")
                        .text("Rank")
                        .attr("transform","rotate(-90)")
                        .attr("text-anchor","end")
                        .attr("dy","1em")

                g2.selectAll("circle").data(rank).enter()
                        .append("svg:circle")
                        .attr("r", 4)
                        .attr("cx", function(d,i) { return scale_x2(i)})
                        .attr("cy", function(d) { return scale_y2(d)})
            })
        }
    </script>
    <script>
        function exportTable() {
            $("#mytable1").table2excel({
                name: "doc1",
                filename: "队员统计"
            });
        }
    </script>
</head>
<body>
<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp" />
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/training/list"/> ">集训列表</a></li>
            <li class="active">${info.name}</li>
        </ol>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading" style="padding: 0px">
                <%@include file="training_topbar.jsp"%>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-left: 20px">
                    <div class="pull-left">
                        <c:if test="${(!empty user) && (user.isAdmin())}">
                            <button class="btn btn-info btn-sm" id="update_Rating">更新积分</button>
                        </c:if>
                        <button class="btn btn-info btn-sm" onclick="exportTable()">导出表格</button>
                    </div>
                </div>
                <hr style="margin:10px "/>

                <table class="table table-condensed table-striped table-hover display" id="mytable1">
                    <thead class="tab-header-area">
                    <tr>
                        <th>姓名</th>
                        <th>班级</th>
                        <th hidden>username</th>
                        <th>Score</th>
                        <th>Base</th>
                        <th>Rating</th>
                        <th>Param</th>
                        <th>Match</th>
                    </tr>
                    </thead>
                    <tfoot>

                    </tfoot>

                    <tbody>
                    <c:forEach items="${ujoinT}" var="curUser">
                        <tr>
                            <td><a  id="pictrue_btn" data-toggle="modal" data-target="#myModal"
                                    onclick="updata(this,${trainingId})">${curUser.realName}</a></td>
                            <td>${curUser.major}</td>
                            <td hidden>${curUser.username}</td>
                            <td>${ratingMap.get(curUser.realName).calcRating(playDuration.get(curUser.realName))}</td>
                            <td>
                                <fmt:formatNumber value="${playDuration.get(curUser.realName)/60}"
                                                  maxFractionDigits="0"
                                                  minFractionDigits="0"
                                                  groupingUsed="false"/>
                            </td>
                            <td>
                                    ${ratingMap.get(curUser.realName).myRating}
                            </td>
                            <td>
                                <fmt:formatNumber value="${ratingMap.get(curUser.realName).mean}"
                                                  maxFractionDigits="2"
                                                  minFractionDigits="2"/>
                                (<fmt:formatNumber value="${ratingMap.get(curUser.realName).standardDeviation}"
                                                   maxFractionDigits="2"
                                                   minFractionDigits="2"/>)
                            </td>
                            <td>
                                <fmt:formatNumber value="${playcntMap.get(curUser.realName)}"
                                                  maxFractionDigits="0"
                                                  minFractionDigits="0"
                                                  groupingUsed="false"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>

            </div>
            <div class="modal-body">
                <h4 class="modal-title" id="myModalLabel">队员成绩曲线</h4>
                <div id="my_container">
                </div>
                <h4 class="modal-title" id="myModalLabel2">队员平均Rank曲线</h4>
                <div id="my_container2">
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>
<jsp:include page="footerInfo.jsp"/>
<c:if test="${!empty tip}">
    <script>
        alert("${tip}");
    </script>
</c:if>
</body>
</html>
