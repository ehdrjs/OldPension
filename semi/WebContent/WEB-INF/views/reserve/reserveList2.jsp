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
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
</head>
<body>
<div class="qna">
		<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>
		<div class="header">
			<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		</div>
		<div class="container" style="height:720px;">
			<div>
				<h5 style="font-weight: bold">
					<span>|&nbsp;</span>예약조회
				</h5>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<ul class="list-group list-group-horizontal">
						<li class="list-group-item" style="width: 25%">예약번호</li>
						<li class="list-group-item" style="width: 25%">예약자</li>
						<li class="list-group-item" style="width: 25%">결제금액</li>
						<li class="list-group-item" style="width: 25%">예약날짜</li>
					</ul>
				</div>
			</div>
			
				<div class="row">
					<div class="col-md-12">
						<c:forEach var="dto" items="${list}">
						<ul class="list-group list-group-horizontal">
							<li class="list-group-item" style="width: 25%"><a href="<%=cp %>/reserve/reserve_detailMem.do?${query}">${dto.reserveNum}</a></li>
							<li class="list-group-item" style="width: 25%; text-align:left;">${dto.reserveName}</li>
							<li class="list-group-item" style="width: 25%">${dto.price}원</li>
							<li class="list-group-item" style="width: 25%">${dto.reserveDate}</li>
						</ul>
						</c:forEach>
					</div>
				</div>
			
		</div>
		<div class="footer">
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>