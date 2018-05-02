<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>聊天室窗口</title>
<link href="<%=request.getContextPath() %>/css/adv.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/css/bootstrap-theme.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/css/font-awesome.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/reconnecting-websocket.min.js"></script>
<%-- <script src="<%=request.getContextPath() %>/js/chat.js"></script> --%>
<script type="text/javascript">
	$(function(){
		var receiver = window.name;
		var st = parent.getStorage();
		if(st.getItem(receiver) != null){
			var list = JSON.parse(st.getItem(receiver));
			var html = "";
			$(list).each(function(index,item){
				var obj = JSON.parse(item);
				html += parent.appendHTML(obj);
			});
			$("#scrollUL").append(html);
			$("#scrollUL")[0].scrollTop = $("#scrollUL")[0].scrollHeight;
			st.removeItem(receiver);
		}
	})
	//点击发送按钮
	function sendContent(){
		var content = $("#txtContent").val();
		if(content == '' || content == null){
			alert("输入内容为空");
			$("#txtContent").focus();
			return;
		}
		var receiver = window.name;
		var content = $("#txtContent").val();
		var data = {"receiver":receiver,"message":content};
		parent.sendMessage(data);
		$("#txtContent").val(null);
	}
	function scroll(){
		var ul = document.getElementById('scrollUL');
		ul.scrollTop = ul.scrollHeight;
	}
</script>
<link rel="stylesheet" href="css/chat.css" media="screen" type="text/css" />
</head>
<body style="margin: 0px;">
<div id="convo" class="chat-div" data-from="Sonu Joshi">
 <!-- onmouseover="showScroll()" onmouseout="hiddenScroll()" -->
	<ul id="scrollUL" class="chat-thread">
		<!-- <li id='li-right' class='kuang'>hello!</li>
		<li id='li-left' class='kuang'>Hi!</li>
		<li id='li-right' class='kuang'>hello!</li>
		<li id='li-left' class='kuang'>Hi!</li>
		<li id='li-right' class='kuang'>hello!</li>
		<li id='li-left' class='kuang'>Hi!</li>
		<li id='li-right' class='kuang'>hello!</li>
		<li id='li-left' class='kuang'>Hi!</li> -->
	</ul>
	<div class="credits">
		<div id="tools" class="tool_div">
			<p style="font-size: 20px;padding-left: 10px;">
				<i class="icon-github-alt" style="margin: 0px 10px;"></i><i class="icon-cut" style="margin: 0px 10px;"></i>
			</p>
		</div>
		<textarea id="txtContent" class="txt"></textarea>
		<input id="username" type="hidden" value="${sessionScope.user.username }" >
		<input id="send" type="button" onclick="sendContent()" value="发送" class="btn btn-info"/>
		<i style="float: right;padding: 10px 20px;">按Ctrl+enter换行</i>
	</div>
</div>
<script type="text/javascript">
	var height = window.innerHeight-24;
	$("#convo").css({
		"height":height+"px"
	});
	//鼠标移到聊天框显示下拉条
	function showScroll(){
		$("#scrollUL").css({
			"overflow-y":"scroll"
		})
	}
	//鼠标移出聊天框隐藏下拉条
	function hiddenScroll(){
		$("#scrollUL").css({
			"overflow-y":"hidden"
		})
	}
	//键盘按键控制
	document.onkeydown = function(e){
		e = e || event;
		//enter触发事件
		if(e.keyCode == 13 && !e.ctrlKey){
			sendContent();
			e.cancelBubble=true;
			e.preventDefault();
			e.stopPropagation();
		}
		if(e.keyCode == 13 && e.ctrlKey){
			var e = $("#txtContent");
			e.val(e.val()+'\n');
		}
	}
</script>
<!-- <a href="javascript:void(0)" onclick="skip()">跳转</a> -->
</body>
</html>