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
<title>로그인</title>

<style type="text/css">

.loginForm {
	margin: 0px 0px 0px 20px;
}
</style>

</head>
<body>
<div class="loginForm">
<div style="float: left; width:500px;">
<form name = "loginForm" method = "post"  action = "<%=cp%>/member/login_ok.do">
<p>아이디 : <input type = "text" name = "userId" value = "아이디를 입력하세요"></p>
<p>비밀번호 : <input type = "password" name = "userPwd" value = "비밀번호를 입력하세요"></p>
<p>
	<button>로그인</button>
</p>

</form>
<table>
	<tr align="center" height="40" >
	 	<td><span style="color: blue;">${message}</span></td>
	</tr>		  
</table>
</div>
<div style="float: left;">
<form name = "nonMemloginForm" method = "post"  action = "<%=cp%>/member/nonMemLogin_ok.do">
<p>예약번호 : <input type = "text" name = "reserveNum" value = "예약번호를 입력하세요"></p>
<p>비밀번호 : <input type = "password" name = "userPwd2" value = "비밀번호를 입력하세요"></p>
<p>
	<button>비회원 예약조회</button>
</p>

</form>
<table>
	<tr align="center" height="40" >
	 	<td><span style="color: blue;">${message2}</span></td>
	</tr>		  
</table>
</div>
</div>
</body>
</html>