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
	function sendOk(){
		var f = document.qnaForm;
		var sv = f.subject.value;
		if(!sv) {
			alert("제목을 입력하세요");
			f.subject.focus();
			return;
		}
		
		sv= f.content.value;
		if(!sv) {
			alert("내용을 입력하세요.");
			f.content.focus();
			return;
		}
		
		if(!sv) {
			alert("비밀번호를 입력하세요.");
			f.pwd.focus();
			return;
		}
		
		f.action="<%=cp%>/qna/${mode}_ok.do";

		f.submit();
	}
</script>
</head>
<body>
	<div class=qna_created>
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
				<form name="qnaForm" method="post">
					<table>
						<tr>
							<td>제목</td>
							<td><input type="text" name="subject" value="${dto.subject}"></td>
						</tr>
						<tr>
							<td>작성자</td>
							<td>${sessionScope.member.userName}</td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea name="content" rows="12">${dto.content}</textarea></td>
						</tr>
						<tr>
							<td><input type="password" name="pwd" value="${dto.pwd}">
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>
								<button type="button" onclick="sendOk();">등록하기</button>
								<button type="button"
									onclick="javascript:location.href='<%=cp%>/qna/qna.do';">등록취소</button>
							</td>
						</tr>
					</table>
				</form>
			</div>

		</div>
		<div class="footer">
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>