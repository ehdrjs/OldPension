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
	<div class=qna_article>
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
			<div>
				<ul>
					<li>${dto.subject}</li>
					<li>${dto.date}</li>
					<li>${dto.count}</li>
				</ul>
				<ul>
					<li>${dto.userId}</li>
					<button>수정</button>
					<button>삭제</button>
				</ul>
				<ul>
					<li>${dto.content}</li>
				</ul>
				<ul>
					<li>이전글</li>
				</ul>
				<ul>
					<li>다음글</li>
				</ul>
				<ul>
					<button>답변</button>
					<button>돌아가기</button>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>