<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">


    <c:url value="/training/getstatge" var="getstage_url"/>

</head>
<body>
<jsp:include page="topBar.jsp"/>
    <div class="container-fluid">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-8">
            <div class="page-header">
                <h1>添加比赛</h1>
            </div>
            <form class="form-horizontal" method="post">

                        <div class="col-lg-6">
                            <div class="form-group">
                                <input type="text" id="inputcontestname"  name="contestName" class="form-control" placeholder="ContestName*" autofocus required>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="contestType">
                                    <option value="PERSONAL">个人赛</option>
                                    <option value="TEAM">组队赛</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="contestStage" id="selectone">
                                    <c:forEach items="${allList}" var="group">
                                        <option value="${group.id}">${group.id}.${group.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control" id="selecttwo" >
                                </select>
                            </div>
                        </div>
            </form>

        </div>
</div>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
    $(function () {
        $('#selectone').change(function () {
            $('#entity').addClass('disable');
            $.post("${getstage_url}",{
                        id: $('#selectone option:selected').val()
                    },function (data) {
                        var str="";
                        $('#selecttwo').html('');
                        data=eval(data);
                        for(var i=0;i<data.length;i++){
                            str+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                        }
                        $('#selecttwo').html(str);
                    }
            );
        });
        $('#selectone').ajaxError(function (event,xhr,options,exc) {
            console.log(event);
            console.log(xhr);
            console.log(options);
            console.log(exc);
        })

    })
</script>
<script type="text/javascript">
    $(function () {
        $('#datetimepicker4').datetimepicker();
    });
</script>
</body>
</html>
