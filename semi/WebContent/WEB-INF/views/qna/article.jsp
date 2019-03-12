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
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="qna_article">
		<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>
		<div class="header">
			<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		</div>
		<div class="container" style="max-width: 700px">
			<div>
				<h5 style="font-weight: bold">
					<span>|&nbsp;</span>고객문의 게시글
				</h5>
			</div>
			<div class="a_menu">
				<div class="a_menuA">
					<ul>
						<li style="float: left; width: 60%;">${dto.subject}</li>
						<li style="float: left; width: 20%;">작성일:${dto.date}</li>
						<li style="float: left; width: 20%;">조회수:${dto.count}</li>
					</ul>
					<div>
						<ul>
							<li>작성자:${dto.userId}</li>
						</ul>
						<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='first'}">    
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px; clean:both;" onclick="javascript:location.href='<%=cp%>/qna/update.do?qnaNum=${dto.num}&${query}';">수정</button>
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px;" onclick="">삭제</button>
                         </c:if>
					</div>
					<ul>
						<li>${dto.content}</li>
					</ul>
					<ul>
						<li>이전글: 
						  <c:if test="${not empty preReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${preReadDto.num}&${query}">${preReadDto.subject}</a>
                  </c:if>
						</li>
					</ul>
					<ul>
						<li>다음글:
						  <c:if test="${not empty nextReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${nextReadDto.num}&${query}">${nextReadDto.subject}</a>
                  </c:if></li>
					</ul>
					<ul>
						<button>답변</button>
						<button>돌아가기</button>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>