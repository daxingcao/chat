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
</head>
<script type="text/javascript">
var username = null;
var socket = null;
$(function() {
	username = $("#username").val();
	//新建WebSocket对象，最后的/websocket对应服务器端的@ServerEndpoint("/websocket")
	socket = new ReconnectingWebSocket('ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/sendMessage/'+username); 
	socket.onopen = function(){
		alert("Connection success!")
	}
	// 处理服务器端发送的数据
	socket.onmessage = function(event) {
		addMessage(event.data);
	};
	socket.onclose = function(){
		alert("Connection is broken!")
	}
})
</script>
<script src="<%=request.getContextPath() %>/js/index.js"></script>
<body>
<input id="username" type="hidden" value="${sessionScope.user.username }">
<div id="content" style="width: 100%;height: 600px;min-width: 1000px;">
	
	<!-- 左侧好友栏显示 -->
	<div class="left-div">
		<div class="left-list-div" id="containor">
			<div class='panel panel-default left-list-default'>
				<div class="left-list-hand">
					<div style="height: 100px;">
						<div style="width: 100px;height: 100px;margin: auto;">
							<c:choose>
								<c:when test="${not empty user.headFileId }">
									<img alt="" src="<%=request.getContextPath() %>/main/loadImage.do?fileId=${user.headFileId}" style="margin: 10px;width: 80px;height: 80px;border-radius: 40px;" />
								</c:when>
								<c:otherwise>
									<img alt="" src="<%=request.getContextPath() %>/images/default_head.png" style="margin: 10px;width: 80px;height: 80px;border-radius: 40px;" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div style="height: 30px;">
						<div style="height: 30px;text-align: center;">
							<a href="<%=request.getContextPath()%>/main/edit"><h4>${sessionScope.user.username }</h4></a>
						</div>
					</div>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="left-list-body">
						<c:forEach items="${requestScope.friendList }" var="user">
							<h5><a id="${user.username }" onclick="addTab('${user.username }','<%=request.getContextPath()%>/chatView')" href="javascript:void(0)">
								<c:choose>
									<c:when test="${not empty user.headFileId }">
										<img alt="" src="<%=request.getContextPath() %>/loadImage.do?fileId=${user.headFileId}" /><b>${user.username }</b>
									</c:when>
									<c:otherwise>
										<img alt="" src="<%=request.getContextPath() %>/images/default_head.png" /><b>${user.username }</b>
									</c:otherwise>
								</c:choose>
							</a></h5>
						</c:forEach>
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