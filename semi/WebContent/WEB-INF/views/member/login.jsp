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
</head>
<body>

<form name = "loginForm" method = "post"  action = "<%=cp%>/member/login_ok.do">
<p>아이디:<input type = "text" name = "userId" value = "아이디를 입력하세요"></p>
<p>비밀번호: <input type = "password" name = "userPwd" value = "비밀번호를 입력하세요"></p>
<p><button>로그인</button></p>
</form>

<table>
	<tr align="center" height="40" >
	 	<td><span style="color: blue;">${message}</span></td>
	</tr>		  
</table>
</body>
</html>