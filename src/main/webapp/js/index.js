	function getStorage(){
		var session = window.sessionStorage;
		return session;
	}
	
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