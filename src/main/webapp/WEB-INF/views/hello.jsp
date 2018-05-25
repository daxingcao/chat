<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/themes/default/easyui.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/adv.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/themes/icon.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/index-left.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath() %>/js/reconnecting-websocket.min.js"></script>
<script src="<%=request.getContextPath() %>/js/index.js"></script>
</head>
<body>
<input id="username" type="hidden" value="${sessionScope.user.username }">
<div id="content" style="width: 100%;height: 600px;min-width: 1000px;">
	
	<!-- 左侧好友栏显示 -->
	<div class="left-div">
		<div class="left-list-div" id="containor">
			<div class='panel panel-default left-list-default'>
				<div class="left-list-hand">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapseOne">
							<i class="glyphicon glyphicon-plus-sign"></i>&nbsp;视频
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="left-list-body">
						<c:forEach items="${requestScope.friendList }" var="user">
							<h5><a id="${user.username }" onclick="addTab('${user.username }','<%=request.getContextPath()%>/chatView')" href="javascript:void(0)">${user.username }</a></h5>
						</c:forEach>
						<!-- <h5><a id="http://www.youku.com/" onclick="addTab('zhangsan','/chatView')" href="javascript:void(0)">zhangsan</a></h5>
						<h5><a id="http://www.iqiyi.com/" onclick="addTab('lisi','/chatView')" href="javascript:void(0)">lisi</a></h5> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="chatRoom" class="easyui-tabs chat_room" style="height: 100%;"></div>
</div>
<script type="text/javascript">
	var height = window.innerHeight;
	$("#content").css({
		"height":height+"px"
	});
</script>
</body> 
</html>