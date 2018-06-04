<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.Jcrop.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.Jcrop.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-theme.css" />
</head>
<style>
.cut_img{
	margin:auto;
}
.head_image{
	width: 100px;
	height: 100px;
	border-radius: 50px;
}
.chat_info_update{
	width: 500px;
	margin: 100px auto;
    border-radius: 10px;
    background: white;
    -webkit-box-shadow: #666 0px 0px 10px;
   	-moz-box-shadow: #666 0px 0px 10px;
   	box-shadow: #666 0px 0px 10px;  
}
.chat_info_update form{
	margin-top: 20px;
}
.chat_info_update_head{
	display: block;
	text-align: center;
	background: darksalmon;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}
.chat_info_update_head img{
	width: 100px;
	height: 100px;
	border-radius: 50px;
	margin: 20px 0px;
}
.chat_info_update_button{
	height: 80px;
	padding-top: 10px;
}
</style>
<body style="background: whitesmoke">
<form id="submitImg" method="post" enctype="multipart/form-data">
	<input class="inputImage hidden" type="file" accept="image/*" name="myFile" />
	<input type="hidden" name="x" id="x" />
	<input type="hidden" name="y" id="y" />
	<input type="hidden" name="width" id="width" />
	<input type="hidden" name="height" id="height" />
</form>
<div id="edit_info" class="chat_info_update">
	<a id="changeImg" class="chat_info_update_head">
		<c:choose>
			<c:when test="${not empty user.headFileId }">
				<img id="showImg" alt="" src="<%=request.getContextPath() %>/main/loadImage.do?fileId=${user.headFileId}" />
			</c:when>
			<c:otherwise>
				<img id="showImg" src="<%=request.getContextPath() %>/images/tubiao.png">
			</c:otherwise>
		</c:choose>
	</a>
	<form id="basisInfo" class="form-horizontal">
		<div class="form-group">
			<label for="username" class="col-sm-4 control-label">用户名:</label>
			<div class="col-sm-6">
				<input type="text" disabled class="form-control" id="username" 
					   placeholder="请输入密码">
			</div>
		</div>
		<div class="form-group">
			<label for="nickname" class="col-sm-4 control-label">昵&nbsp;&nbsp;称:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="nickname" 
					   placeholder="请输入密码">
			</div>
		</div>
		<div class="form-group">
			<label for="nickname" class="col-sm-4 control-label">邮&nbsp;&nbsp;箱:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="nickname" 
					   placeholder="请输入密码">
			</div>
		</div>
		<div class="form-group">
			<label for="nickname" class="col-sm-4 control-label">电&nbsp;&nbsp;话:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="nickname" 
					   placeholder="请输入密码">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label">性&nbsp;&nbsp;别:</label>
			<div class="col-sm-6">
				<div class="radio-inline">
					<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked> 男
				</div>
				<div class="radio-inline">
					<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">女
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-4 control-label">个人签名:</label>
			<div class="col-sm-6">
				<textarea class="form-control" style="resize: none;" rows="3"></textarea>
			</div>
		</div>
	</form>
	<div class="chat_info_update_button">
		<label for="name" class="col-sm-4">
			<button type="button" class="btn btn-default" style="float: right;">返回</button>
		</label>
		<label for="name" class="col-sm-6">
			<button type="button" class="btn btn-success" style="float: right;">确认</button>
		</label>
	</div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" data-keyboard='false' aria-hidden="true">
	<div class="modal-content" style="margin:100px auto" />
		<div id="showImage" style="overflow: none;margin: 50px auto;text-align: center;">
			<img id="element_id" src="">
		</div>
		<div class="modal-footer">
			<button id="close" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button id="submit" disabled type="button" class="btn btn-primary">提交更改</button>
		</div>
	</div>
</div>
</body>
<script>
//截图控件初始化
var jcropApi;
$("#element_id").Jcrop({
	allowResize:true,
	allowSelect:true,
	minSelect:[40,40],
	onRelease:cancelWarn,
	addClass:"cut_img",
	aspectRatio:1,
	onSelect:getParams
},function(){
	jcropApi = this;
});
//头像点击事件关联文件输入框
$("#changeImg").click(function(){
	$(".inputImage").trigger("click");
});
//上传剪切的图片
$("#submit").click(function(){
	var form = new FormData(document.getElementById("submitImg"));
	//ajax请求
	$.ajax({
		url:"<%=request.getContextPath() %>/main/uploadHeadPortrait.do",
		type:"post",
		data:form,
		processData:false,
		contentType:false,
		success:function(data){
			console.log(data);
			$("#myModal").modal('hide');
			if(data.success){
				$("#showImg").attr("src","<%=request.getContextPath() %>/main/loadImage.do?fileId="+data.fileId);
			}else {
				$("#showImg").attr("src","<%=request.getContextPath()%>/"+data.path);
			}
		}
	})
});

function cancelWarn() {
	console.log("选框太小了");
};

//选框选定时,进行赋值操作
function getParams(c) {
	console.log(c);
	$("#x").val(c.x);
	$("#y").val(c.y);
	$("#width").val(c.w);
	$("#height").val(c.h);
	var val = $("#width").val();
	if(val != null && val != 'NaN' && val > 0){
		$("#submit").removeAttr("disabled");
	}
};

//文件选择框内容改变触发事件
$(".inputImage").change(function(){
	$("#submit").attr("disabled","disabled");
	var dom = $(this);
	var reader = new FileReader();
	reader.readAsDataURL(dom[0].files[0]);
	reader.onload = function(e){
		var img = new Image();
		var path = e.target.result;
		img.src = path;
		img.onload = function(argument){
			if(this.width < 400){
				this.width = 400;
			}
			$(".modal-content").css({width:(this.width + 40)});
		};
		jcropApi.setImage(path);
		$("#myModal").modal('show');
	}
});
//对上传的文件重命名
$('#myModal').on('hidden.bs.modal', function () {
	$(".inputImage").val('');
})
</script>
</html>