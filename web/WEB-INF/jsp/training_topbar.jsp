<%--
  Created by IntelliJ IDEA.
  User: kun
  Date: 2016/8/21
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav nav-tabs">
    <li id="jieduan"><a href="<c:url value="/training/detail/${trainingId}"/>" style="padding: 6px;margin-left: 10px;margin-top: 5px">
        阶段列表
    </a>
    </li>
    <li id="duiyuan"><a href="<c:url value="/training/statistic/${trainingId}"/>" style="padding: 6px;margin-left: 5px;margin-top: 5px">
        队员统计
    </a></li>
    <li id="duiwu"><a href="<c:url value="/training/fixedTeam/${trainingId}"/>" style="padding: 6px;margin-left: 5px;margin-top: 5px">
        队伍统计
    </a></li>
    <c:if test="${user.isAdmin()}">
        <li id="shenhe"><a href="<c:url value="/training/trainingUser/${trainingId}"/> " style="padding: 6px;margin-left: 5px;margin-top: 5px">
            审核队员
        </a></li>
    </c:if>
    <li id="suiji"><a href="<c:url value="/assign/lastAssign/${trainingId}"/> " style="padding: 6px;margin-left: 5px;margin-top: 5px">
        随机分队
    </a></li>
</ul>