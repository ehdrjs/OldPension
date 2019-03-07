<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>올드펜션</title>
<link rel="stylesheet" href="<%=cp %>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/style.css" type="text/css">
</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="reserve_body">
	<div>
		<ul>
			<li>객실</li>
			<select id="room">
				<option value="room1">방1</option>
				<option value="room2">방2</option>
				<option value="room3">방3</option>
			</select>
		</ul>
		<ul>
			<li>입실날짜</li>
			<li>퇴실날짜</li>
		</ul>
		<ul>
			<li>인원</li>
			<li>성인</li>
			<select id="adult">
				<option value="1">1명</option>
				<option value="2">2명</option>
				<option value="3">3명</option>
				<option value="4">4명</option>
				<option value="5">5명</option>
				<option value="6">6명</option>
			</select>
			<li>아동</li>
			<select id="kids">
				<option value="1">1명</option>
				<option value="2">2명</option>
			</select>
			
		</ul>
	</div>
	
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>