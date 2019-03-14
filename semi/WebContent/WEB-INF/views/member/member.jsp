<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#signInId").change(function(){
		
		var f = document.signId;
		
		var query="id="+$("#signInId").val();
		var url="<%=cp%>/member/test.do";
		  
		  $.ajax({
			  type:"post"
			  ,url:url
			  ,data:query
			  ,dataType:"json"
			  ,success:function(data) {
				  var state = data.state;
				  var out = "";
				  if(state=="Impossible") {
					  out += "아이디가 <span style='color: red;'>중복 </span> 됩니다";
				  }else{
					  out += "아이디가 <span style='color: blue;'>사용 가능 </span> 합니다.";  
				  }		  
				  
				  $("#idOverLayout").html(out);
			
			  }
		      ,error:function(e) {
		    	  console.log(e.responseText);
		      }
		  });
	});
	
});

function sendOk(){
	var f = document.signIn;
	var str;
	
	str = f.userId.value;
	if(!str){
		alert("아이디를 입력하세요");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9]{2,4}$/ig.test(str)){
		alert("아이디는 영문자로 시작해서 3~5글자 여야합니다.")
		f.userId.focus();
		return;
	}
	
	str = f.userPwd.value;
	if(!str){
		alert("비밀번호를 입력하세요.");
		f.userPwd.focus();
		return;
	}
	if(str != f.userPwdCheck.value){
		alert("비밀번호가 일치하지 않습니다.");
		f.userPwdCheck.focus();
		return;
	}
	
	str = f.tel.value;
	str = str.replace(/\-/gi,"");
	if(!str){
		alert("전화번호를 입력해주세요");
		f.tel.focus();
		return;
	}
	if(str.length != 10 && str.length != 11){
		alert("번호 크기를 맞춰주세요.");
		f.tel.focus();
		return;
	}
	
	str = f.email.value;
	if(!str){
		alert("이메일을 입력해주세요");
		f.email.focus();
		return;
	}
	
	if(!isValidEmail(str)){
		alert("이메일형식이 올바르지 않습니다.");
		f.email.focus();
		return;
	}
	f.action  = "<%=cp%>/member/member_ok.do";
	f.submit();
	
}

function isValidEmail(data){
    var format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}


</script>
<style type="text/css">
.nBtn{
	border: 1px solid orange;
	border-radius: 2px;
	background: #FFF3DA;
	color : #FF7A12;
	width: 100px;
	height: 40px;
	cursor: pointer
}
</style>
</head>
<body>
<div style="margin : 30px auto 0;width: 450px; height : 400px; border : 1px solid black; border-radius: 5px" align="center">
<form name = "signIn" method = "post">
<h2 style ="font-weight: bold">회원가입</h2>
<div style="width:400px; text-align: left" >
	<p>아이디 :<input id = "signInId" type = "text" name = "userId"> </p>
	<div id ="idOverLayout">아아디는 영문자로 시작하는 3~5글자 이어야합니다.</div> 
	<p>비밀번호 :<input type = "password" name = "userPwd"> </p>
	<p>비밀번호 :<input type = "password" name = "userPwdCheck"> </p>
	<p>이름 :<input type = "text" name = "userName"> </p>
	<p>전화번호 : <input type = "text" name = "tel"> </p>
	<p>이메일<input type = "text" name = "email"> </p>
</div>
<button class = "nBtn" type = "button" onclick = "sendOk();">${mode=="created"?"회원가입":"수정완료" }</button>
</form>
</div>
</body>
</html>