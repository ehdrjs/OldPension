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
		<div class="container">
			<div>
				<h5 style="font-weight: bold">
					<span>|&nbsp;</span>고객문의 게시판
				</h5>
			</div>
			<div class="row">
				<div class="col-md-12">
					<ul>
						<li style="display: inline">총${dataCount}글<span>|</span>페이지</li>
						<li style="float: right; list-style: none">
							<button type="button" class="btn btn-info btn-sm"
								onclick="javascript:location.href='<%=cp%>/qna/created.do';">글작성</button>
						</li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<ul class="list-group list-group-horizontal">
						<li class="list-group-item" style="width: 15%">번호</li>
						<li class="list-group-item" style="width: 40%">제목</li>
						<li class="list-group-item" style="width: 20%">작성ID</li>
						<li class="list-group-item" style="width: 15%">작성일</li>
						<li class="list-group-item" style="width: 10%">조회수</li>
					</ul>
				</div>
			</div>
			
				<c:forEach var="dto" items="${list}">
			<div class="row">
				<div class="col-md-12">
					<ul class="list-group list-group-horizontal">
						<li class="list-group-item" style="width: 15%">${dto.listNum}</li>
						<li class="list-group-item" style="width: 40%; text-align:left;">${dto.subject}</li>
						<li class="list-group-item" style="width: 20%">${dto.userId}</li>
						<li class="list-group-item" style="width: 15%">${dto.created}</li>
						<li class="list-group-item" style="width: 10%">${dto.count}</li>
					</ul>
				</div>
			</div>
			</c:forEach>
				
				<div class="">
					<nav aria-label="Page navigation example">
					  	<ul class="pagination" style="display:list-item">
					        <li class="page-item"><c:if test="${dataCount==0}">등록된 게시물이 없습니다.</c:if></li>
					        <li class="page-item"><c:if test="${dataCount!=0}">${paging}</c:if></li>
				 	 	</ul>
					</nav>
			</div>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-9">
					<form class="form-group" name="qnaSearchForm"
						action="<%=cp%>/qna/qna.do" method="post">
						<select class="form-control col-md-2" name="qnaSearchKey"
							style="float: left; margin-right: 5px" class="qnaSelectField">
							<option value="qnaSubject">제목</option>
							<option value="qnaContent">내용</option>
							<option value="qnaUserId">작성ID</option>
							<option value="qnaDate">등록일</option>
						</select> <input type="text" class="form-control col-md-4"
							style="float: left; margin-right: 5px" name="qnaSearchValue">
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px" onclick="qnaSearchList()">검색</button>
					</form>
				</div>
			</div>
		</div>

		<div class="footer">
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>