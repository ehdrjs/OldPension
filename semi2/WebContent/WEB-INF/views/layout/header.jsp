<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예제</title>

<link rel="stylesheet" href="<%=cp %>/resource/layout.css" type="text/css">

</head>
		<div class="logo">
			<p>
				<a href="#"> <span><img src="<%=cp %>/img/rogo.png"
						style="width: 65px; margin: -15px;"></span>
				</a>
			</p>
		</div>
		<div class="gnb">
			<ul>
				<li><a href="#">공지사항</a></li>
				<li><a href="#">방소개</a></li>
				<li><a href="#">일정</a></li>
				<li><a href="#">예약</a>
					<ul class="list1">
						<li style="clear: both;"><a href="#">실시간예약</a></li>
						<li style="clear: both;"><a href="#">예약확인/취소</a></li>
					</ul>
				</li>
				<li><a href="#">고객문의</a></li>
			</ul>
		</div>
		<div class="loginInfo">
			<a href="#" style="color: black;">로그인</a> 
			<a href="#" style="color: black;">회원가입</a>
		</div>

</html>