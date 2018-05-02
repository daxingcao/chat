<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天界面</title>
<link href="${pageContext.request.contextPath}/css/adv.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/reconnecting-websocket.min.js"></script>
<!-- <script type="text/javascript" src="js/chat.js"></script> -->
<link rel="stylesheet" href="css/chat.css" media="screen" type="text/css" />
</head>
<body>
<div id="convo" class="chat-div" data-from="Sonu Joshi">  
	<ul id="scrollUL" class="chat-thread">
		<li id='li-right' class='kuang'>hello!</li>
		<li id='li-left' class='kuang'>Hi!</li>
	</ul>
	<div class="credits">
		<textarea id="txtContent" cols="40" rows="1" class="txt"></textarea></td><td class="pl">
		<input id="Button1" type="button" onclick="sendContent()" value="发送" class="btn"/>
	</div>
</div>
<div class="input-id">
	<input id="userId" type="hidden" value="${sessionScope.user.userId }" />
	<input id="userName" type="hidden" value="${sessionScope.user.userName }" />
</div>
</body>
</html>