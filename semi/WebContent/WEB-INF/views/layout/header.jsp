<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>

	<div class="logo">
		<p>
			<a href="<%=cp%>/index.jsp"> <span><img src="<%=cp %>/img/rogo.png"
					style="width: 65px; margin: -15px;"></span>
			</a>
		</p>
	</div>
	<div class="gnb">
		<ul>
			<li><a href="#">공지사항</a></li>
			<li><a href="#">방소개</a></li>
			<li><a href="<%=cp%>/special/s_calendar.do">스페셜</a></li>
			<li><a href="#">예약</a>
				<ul class="list1">
					<li><a href="<%=cp%>/reserve/reserve.jsp">실시간예약</a></li>
					<li><a href="#">예약확인/취소</a></li>
				</ul>
			</li>
			<li><a href="#">고객문의</a></li>
		</ul>
	</div>
	<div class="loginInfo">
		<a href="<%=cp%>/WEB-INF/login/login.jsp" style="color: black;">로그인 | </a> 
		<a href="#" style="color: black;">회원가입</a>
	</div>

