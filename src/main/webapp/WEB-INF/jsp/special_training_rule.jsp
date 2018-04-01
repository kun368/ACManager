<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/9
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>专题训练添加规则 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <script src="//cdn.bootcss.com/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/datatables/1.10.13/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>

</head>
<body>

<div class="container-fluid"  style="margin-right: 0.7%;margin-left: 0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/cpt/list"/> ">专题训练列表</a></li>
            <li class="active">查看规则</li>
        </ol>
    </div>


    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">专题训练 规则</h3>
            </div>
            <div class="panel-body">
                <pre>
专题训练目录树结构有两种添加方式

    1. 添加时使用CSV格式，规则如下：

        不含表头，4列，英文逗号分隔无空格，分别为：
        * id：当前节点id；根节点必为0，且出现在第0行；保证id唯一
        * pid：父节点id；根节点的父节点为0；父节点需出现在子节点之前
        * name：当前节点名，规则见下方；
        * type：类型；大写；题目节点为LEAF，目录节点为LIST

        如果添加专题时，本栏留空，则自动创建根节点。

        Example:
        0,0,SDKD训练题,LIST
        1,0,动态规划,LIST
        2,1,树形DP,LIST
        3,2,10086@HDU,LEAF
        4,2,10010@UVA,LEAF
        5,1,插头DP,LIST
        6,5,10000@POJ,LEAF
        7,0,数据结构,LIST"

    2. 可视化修改格式树，规则如下：

        * 只能有一个根目录，代表当前专题名字
        * 非目录节点不能添加题目


    节点名规则：

        目录名不能包含“@”
        题目名格式必须为：题目标号@OJ名称
        其中OJ名称必须为下列之一，注意大小写：

            POJ,
            ZOJ,
            UVALive,
            SGU,
            URAL,
            HUST,
            SPOJ,
            HDU,
            HYSBZ,
            UVA,
            CodeForces,
            Aizu,
            LightOJ,
            UESTC,
            NBUT,
            FZU,
            CSU,
            SCU,
            ACdream,
            CodeChef,
            Gym,
            OpenJudge,
            HihoCoder,
            UESTC_old,
            OpenJ_Bailian
            Null

                </pre>
            </div>
        </div>
    </div>

</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加专题训练集</h4>
            </div>
            <div class="modal-body">
                <form class="form" action="" method="post"><!--填写提交地址-->
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="名称" id="name" required>
                    </div>
                    <div class="form-group">
                        <textarea rows="5" class="form-control" placeholder="备注" id="remark"></textarea>
                    </div>
                    <div class="form-group">
                        <textarea rows="20" class="form-control" placeholder="
    CSV格式树信息，规则如下：

    * 不含表头，4列，英文逗号分隔无空格，分别为：
    * id：当前节点id；根节点必为0，且出现在第0行；保证id唯一
    * pid：父节点id；根节点的父节点为0；父节点需出现在子节点之前
    * name：当前节点名字，如章节名；若为叶节点（题目）则为题目编号（int）
    * type：类型；大写；叶节点（题目）为LEAF，其余为LIST

    Example:
    0,0,UVA,LIST
    1,0,入门经典,LIST
    2,1,第一章,LIST
    3,2,10086,LEAF
    4,2,10010,LEAF
    5,1,第二章,LIST
    6,5,10000,LEAF
    7,0,训练指南,LIST" id="csvTree"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="savabutton">保存</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
</body>
</html>
