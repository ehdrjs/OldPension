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
			<li><a href="<%=cp%>/schedule/calendar.do">일정</a></li>
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
	 	<c:if test="${empty sessionScope.member}">
	 
		<a href="<%=cp%>/member/login.do" style="color: black;">로그인 | </a> 
		<a href="<%=cp %>/member/member.do" style="color: black;">회원가입</a>
		
		</c:if>
		
		<c:if test="${not empty sessionScope.member}">
			<span style="color:blue;">${sessionScope.member.userName}</span>님
                    &nbsp;|&nbsp;
                    <a href="<%=cp%>/member/logout.do">로그아웃</a>
		
		</c:if>
	 
		
	</div>

