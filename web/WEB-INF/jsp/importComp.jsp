<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/14
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>导入比赛 - ACManager</title>
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
    <script>
        $(document).ready(function () {
            $('#startTime').datetimepicker({

                format:'Y-m-d'+' '+'H:i:s',
                formatDate:'Y-m-d',
                formatTime:'H:i',
                timepicker:true,
                //showSecond: true,
                dayOfWeekStart : 1,
                lang:'en',
                step:1
                // stepSecond: 1,
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'

            });
            $('#startTime').change(function () {
                str=$('#startTime').val();
                re=new RegExp(" ","i");
                var newstart=str.replace(re,"T");
                $('#startTime').val(newstart);
            })
            $('#endTime').datetimepicker({

                format:'Y-m-d'+' '+'H:i:s',
                formatDate:'Y-m-d',
                formatTime:'H:i',
                timepicker:true,
                //showSecond: true,
                dayOfWeekStart : 1,
                lang:'en',
                step:1
                // stepSecond: 1,
                // disabledDates:['1986-01-08 ','1986-01-09','1986-01-10'],
                //startDate:	'1986-01-05'

            });
            $('#endTime').change(function () {
                str=$('#endTime').val();
                re=new RegExp(" ","i");
                var newstart=str.replace(re,"T");
                $('#endTime').val(newstart);
            })
        })
    </script>
</head>
<body>

<div class="container">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>导入比赛</h1>
            </div>
            <div>
                <form class="form-horizontal" method="post" action="<c:url value="/contest/importContest"/> ">
                    <div class="col-lg-10">
                        <div class="col-lg-6">
                            <div class="form-group">
                                <input type="text"   name="contestName" class="form-control" placeholder="比赛名称*" autofocus required>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="contestType">
                                    <option value="PERSONAL">个人赛</option>
                                    <option value="TEAM">组队赛</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="text"  id="startTime" name="stTime" class="form-control" placeholder="比赛开始时间(eg: 2007-12-03T10:15:30)" autofocus required>
                            </div>
                            <div class="form-group">
                                <input type="text"  id="endTime"  name="edTime" class="form-control" placeholder="比赛结束时间(eg: 2007-12-03T10:15:30)" autofocus required>
                            </div>
                        </div>

                        <div class="form-group">
                            <textarea name="vjContest" class="form-control" rows="15" placeholder="VJudge榜单" required></textarea>
                        </div>
                        <div class="form-group">
                            <textarea name="myConfig" class="form-control" rows="8" placeholder="配置文件"></textarea>
                        </div>

                        <div class="form-group pull-right">
                            <button class="btn btn-lg btn-primary btn-block" type="submit">下一步</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="co-lg-2">
        </div>
    </div>
</div>

<c:if test="${!empty tip}">
    <script>
        alert('${tip}');
    </script>
</c:if>
<jsp:include page="footerInfo.jsp"/>
</body>
</html>
