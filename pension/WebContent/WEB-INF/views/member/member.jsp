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
<script type="text/javascript">
function sendOk(){
	var f = document.singIn;
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
		f.userPwd.value();
		return;
	}
	if(str != f.userPwdCheck.value){
		alert("비밀번호가 일치하지 않습니다.");
		f.userPwdCheck.value();
		return;
	}
	
	str = f.tel.value;
	str = str.replace(/-/gi,"");
	if(!str){
		alert("전화번호를 입력해주세요");
		f.tel.focus();
		return;
	}
	if(str.length != 10 || str.length != 11){
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
	
	
	f.submit();
	
}

function isValidEmail(data){
    var format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}


</script>

</head>
<body>
<form name = "signIn" method = "post" action = "<%=cp%>/member/member_ok.do">
<p>${title }</p>
<p>아이디 :<input type = "text" name = "userId"> </p>
<p>비밀번호 :<input type = "password" name = "userPwd"> </p>
<p>비밀번호 :<input type = "password" name = "userPwdCheck"> </p>
<p>이름 :<input type = "text" name = "userName"> </p>
<p>전화번호 : <input type = "text" name = "tel"> </p>
<p>이메일<input type = "text" name = "email"> </p>
<p><input type = "radio" name = "userRole" value = "admin">관리자</p>
<p><input type = "radio" name = "userRole" value = "user">사용자</p>
<button onclick = "sendOk();">${mode=="created"?"회원가입":"수정완료" }</button>

</form>
</body>
</html>