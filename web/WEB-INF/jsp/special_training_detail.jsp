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
    <title>专题训练详情 - ACManager</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！
-->
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="//cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.js"></script>
    <script src="//cdn.bootcss.com/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.bootcss.com/zTree.v3/3.5.28/js/jquery.ztree.all.min.js"></script>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/datatables/1.10.13/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css"
          href="//cdn.bootcss.com/jquery-datetimepicker/2.5.4/jquery.datetimepicker.css"/>
    <link href="//cdn.bootcss.com/zTree.v3/3.5.28/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet">
</head>
<body>

<div class="container-fluid" style="margin-right: 0.7%;margin-left:
0.7%">
    <jsp:include page="topBar.jsp"/>
    <div class="row">
        <ol class="breadcrumb">
            <li>您所在的位置：</li>
            <li><a href="<c:url value="/cpt/list"/> ">专题训练列表
            </a></li>
            <li class="active">${res.name}</li>
        </ol>
    </div>

    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">专题训练：${res.name}</h3>
            </div>
            <div class="panel-body">
                添加时间：${res.addTime}
                <br/><br/>
                <a class="btn btn-default btn-sm" href="<c:url value="/cpt/rule"/> "
                   target="_blank">查看规则</a>
                <button class="btn btn-default btn-sm" id="add_dir">添加目录</button>
                <button class="btn btn-default btn-sm" id="add_problem">添加题目</button>
                <br/>
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footerInfo.jsp"/>
<c:url value="/api/cpt/${res.id}/ztreestr" var="url_ztree"/>
<c:url value="/api/cpt/${res.id}/delete/" var="url_delete"/>
<c:url value="/api/cpt/${res.id}/rename/" var="url_rename"/>
<c:url value="/api/cpt/${res.id}/addNode" var="url_addson"/>

<c:url value="/cpt/statistic/${res.id}/" var="url_statistic"/>
<c:url value="/cpt/pidsInfo/${res.id}/" var="url_pidsinfo"/>

<script>
    var zTreeObj;
    function zTreeBeforeRemove(treeId, treeNode) {
        return confirm("确定删除" + treeNode.name + "节点吗?");
    }
    function zTreeOnRemove(event, treeId, treeNode) {
        $.ajax({
            url: "${url_delete}" + treeNode.id,
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.ok == "false") {
                    alert(data.status);
                    location.reload();
                }
            }
        });
    }

    function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
        if (treeNode.isParent == false) {
            if (newName.match(/^(\w+)@(\w+)$/) != null) { //fuhe
                return true;
            } else {
                alert("叶子节点命名规范为：题目编号@OJ名。eg：10086@UVA");
                var nodes = zTreeObj.getSelectedNodes();
                zTreeObj.editName(nodes);
                return false;
            }
        }
        else {
            if (newName.match(/^.*@.*$/) == null) { //bufuhe
                return true;
            } else {
                alert("父节点节点命名规范为：不能包含“@”。");
                var nodes = zTreeObj.getSelectedNodes();
                zTreeObj.editName(nodes);
                return false;
            }
        }
    }
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
        $.ajax({
            url: "${url_rename}" + treeNode.id + "/" + treeNode.name,
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.ok == "false") {
                    alert(data.status);
                    location.reload();
                }
            }
        });


    }
    function setRemoveBtn(treeId, treeNode) {
        return !treeNode.id == 0;
    }
    function findmaxID(data) {
        console.log(data);
        var max = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].id > max)
                max = data[i].id;
        }
        return max;
    }
    $(document).ready(function () {

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                },
                keep: {
                    leaf: true,
                    parent: true
                }

            },
            edit: {
                enable: true,
                showRemoveBtn: setRemoveBtn,
                removeTitle: "删除节点",
                showRenameBtn: true,
                renameTitle: "编辑节点名称"
            },
            callback: {
                beforeRemove: zTreeBeforeRemove,
                onRemove: zTreeOnRemove,
                beforeRename: zTreeBeforeRename,
                onRename: zTreeOnRename
            },
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
            }
        };
        $.ajax({
            url: "${url_ztree}",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var zNodes = data;
                zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                zTreeObj.expandAll(true);
            }
        });

        function addHoverDom(treeId, treeNode) {
            var aObj = $("#" + treeNode.tId + "_a");
            // 做题统计
            if ($("#diyBtn_"+treeNode.id).length>0)
                return;
            var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' ></span>"
                + "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
                + "' title='" + treeNode.name + "' onfocus='this.blur();'>做题统计</button>";
            aObj.append(editStr);
            var btn = $("#diyBtn_"+treeNode.id);
            if (btn)
                btn.bind("click", function() {
                    var url = "${url_statistic}" + treeNode.id;
                    window.open(url);
                });

            // 题目信息
            if ($("#diyBtn2_"+treeNode.id).length>0)
                return;
            editStr = "<span id='diyBtn2_space_" +treeNode.id+ "' ></span>"
                + "<button type='button' class='diyBtn2' id='diyBtn2_" + treeNode.id
                + "' title='" + treeNode.name + "' onfocus='this.blur();'>题目信息</button>";
            aObj.append(editStr);
            btn = $("#diyBtn2_"+treeNode.id);
            if (btn)
                btn.bind("click", function() {
                    var url = "${url_pidsinfo}" + treeNode.id;
                    window.open(url);
                });
        };

        function removeHoverDom(treeId, treeNode) {
            $("#diyBtn_"+treeNode.id).unbind().remove();
            $("#diyBtn_space_" +treeNode.id).unbind().remove();

            $("#diyBtn2_"+treeNode.id).unbind().remove();
            $("#diyBtn2_space_" +treeNode.id).unbind().remove();
        };

        function addSon(isParent) {
            var node = zTreeObj.getSelectedNodes();
            if (node.length == 0) {
                alert("请先选择一个节点");
            }
            else if (node[0].isParent) {
                var troobj = zTreeObj.transformToArray(zTreeObj.getNodes());
                var new_ID = findmaxID(troobj) + 1;
                var newnode = {
                    id: new_ID,
                    pId: node[0].id,
                    isParent: isParent,
                    name: isParent ? "new" + new_ID : new_ID + "@Null"
                };
                $.ajax({
                    url: "${url_addson}",
                    type: "POST",
                    data: newnode,
                    dataType: "json",
                    success: function (data) {
                        if (data.ok == "false") {
                            alert(data.status);
                            location.reload();
                        } else {
                            zTreeObj.addNodes(node[0], newnode);
                        }
                    }
                });
            }
            else if (!node[0].isParent) {
                alert("叶子节点不能添加子节点！");
            }
        }

        $("#add_problem").click(function () {
            addSon(false)
        });
        $("#add_dir").click(function () {
            addSon(true)
        });
    });
</script>
</body>
</html>
