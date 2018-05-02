<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap-theme.css" />
<link rel="icon" href="<%=request.getContextPath() %>/images/page_photo.jpg" type="images/png" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/login.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<title>登录界面</title>
<style type="text/css">
	label.error{
		background: url("images/error.png") no-repeat 0px 0px;
		color: red;
		padding-left: 20px;
	}
</style>
<script type="text/javascript">
	function info(i) {
		if(i==1){
			alert("该账号已登录，请注意是否秘密泄露！");
		}else if (i==2) {
			alert("用户名或密码错误！");
		}else {
			
		}
	}
	$(function(){
		
		$("#loginForm").validate({
			/* debug:true, */
			rules:{
				userName:{
					required: true,
					email: false
				},
				password:{
					required:true
				}
			},
			messages:{
				userName:{
					required:"用户名不能为空",
					email:"请输入正确的邮箱格式！"
				},
				password:"密码不能为空！"
			}
		})
	})
</script>
</head>
<body onload="info(${requestScope.info})">
<div class="body_div">
	<div class="div_header">
		<h2 class="div_h2">Mr Cao的聊天室</h2>
	</div>
	<div class="div_content">
		<div class="div_login_left">
			<img alt="图片不存在" src="images/login_toubiao.jpg">
		</div>
		<div class="div_login_right">
			<form id="loginForm" action="sys/loginValid" method="post">
				<input type="text" size="16" class="form-control login_input" id="userName" 
				   name="userName" placeholder="用户名" />
				<input type="password" class="form-control login_input" id="password" 
				   name="password" placeholder="密码" />
				<div class="checkbox login_check">
					<label style="float:left;"><input type="checkbox">记住密码</label>
				<a class="login_a" href="javascript:void(0)">忘记密码</a>
				</div>
				<!-- <div class="select_div">
					<i class="text-left login_i">选择登录:</i>
				    <label class="radio-inline">
				        <input type="radio" name="optionsRadiosinline" id="optionsRadios3" value="option1" checked> 选项 1
				    </label>
				    <label class="radio-inline">
				        <input type="radio" name="optionsRadiosinline" id="optionsRadios4"  value="option2"> 选项 2
				    </label>
				</div> -->
				<div class="button_div text-center">
					<button type="submit" class="btn btn-info login_button">
						<i class="glyphicon glyphicon-user" style="color: white;"></i>&nbsp;立即登录
					</button>
				<!-- <a class="register_a text-center" href="javascript:void(0)">没有账号？立即注册</a> -->
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>