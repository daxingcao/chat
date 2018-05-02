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
	function getStorage(){
		var session = window.sessionStorage;
		return session;
	}
	
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
	
	function addMessage(data){
		var content = JSON.parse(data);
		if(username == content.sender){
			var chatContain = $("iframe[name='"+content.receiver+"']").contents().find("#scrollUL");
			if ($('#chatRoom').tabs('exists', content.receiver)){
				var html = appendHTML(content);
				chatContain.append(html);
			}else{
				saveData(content.receiver,data);
			}
		}else{
			var chatContain = $("iframe[name='"+content.sender+"']").contents().find("#scrollUL");
			if ($('#chatRoom').tabs('exists', content.sender)){
				var html = appendHTML(content);
				chatContain.append(html);
			}else{
				saveData(content.sender,data);
			}
		}
		chatContain[0].scrollTop = chatContain[0].scrollHeight;
	}
	
	function appendHTML(data){
		var html = "";
		if(data.isSelf){
			html += "<li id='li-right' class='kuang'>"+data.message+"</li>";
		}else {
			html += "<li id='li-left' class='kuang'>"+data.message+"</li>"
		}
		return html;
	}
	
	function saveData(key,data){
		var st = getStorage();
		var list = null;
		if(st.getItem(key) != null){
			list = JSON.parse(st.getItem(key));
			list.push(data);
		}else {
			list = new Array(data);
		}
		st.setItem(key,JSON.stringify(list));
	}
	
	function sendMessage(data){
		if(socket.readyState == 1){
			socket.send(JSON.stringify(data));
		}
	}
	
	$("#chatRoom").tabs({
		pill: true,
		justified: true
	})
	
	function addTab(userName, url){
		if ($('#chatRoom').tabs('exists', userName)){
			$('#chatRoom').tabs('select', userName);
		} else {
			var content = '<iframe name="'+userName+'" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#chatRoom').tabs('add',{
				title:userName,
				content:content,
				closable:true
			});
		}
	}
</script>
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
						<%-- <h5><a id="http://www.youku.com/" onclick="addTab('zhangsan','<%=request.getContextPath()%>/chatView')" href="javascript:void(0)">zhangsan</a></h5>
						<h5><a id="http://www.iqiyi.com/" onclick="addTab('lisi','<%=request.getContextPath()%>/chatView')" href="javascript:void(0)">lisi</a></h5> --%>
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