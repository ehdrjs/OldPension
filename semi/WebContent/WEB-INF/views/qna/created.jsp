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
			f.subject.focus();
			return;
		}
		
		sv= f.content.value;
		if(!sv) {
			f.content.focus();
			return;	
		}
		
		sv= f.pwd.value;
		if(!(/\d{4}/).test(sv)) {
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
					<span>|&nbsp;</span>고객문의 글작성
				</h5>
			</div>
			<div class=c_menu>
				<div class=c_menuA>
					<form name="qnaForm" method="post">
						<ul>
							<li class="title"
								style="float: left; width: 10%; line-height: 40px;">제목</li>
							<li style="float: left; width: 90%"><input type="text"
								name="subject" placeholder="제목을 입력하세요" value="${dto.subject}"></li>
						</ul>
						<ul style="line-height: 40px;">
							<li class="title" style="float: left; width: 10%;">작성ID</li>
							<li
								style="float: left; width: 20%; font-size: 18px; padding: 0px 0px 0px 10px">${sessionScope.member.userId}</li>
							<li class="title"
								style="float: left; text-align: right; width: 40%; padding: 0px 20px 0px 0px">글
								비밀번호</li>
							<li><input
								style="float: left; width: 30%; height: 35px; margin: 3px 0px"
								type="password" name="pwd" placeholder="비밀번호(숫자4자리)"
								value="${dto.pwd}"></li>
						</ul>
						<ul>
							<li class="title" style="clear: both">내용</li>
							<li><textarea name="content" rows="12"
									placeholder="내용을 입력하세요">${dto.content}</textarea></li>
						</ul>
						<div class="row text-center" style="width: 100%">
							<div style="width: 30%; float: none; margin: 0 auto">
								<button type="button" class="btn btn-info btn-sm"
									style="margin-top: 3px;"
									onclick="javascript:location.href='<%=cp%>/qna/qna.do';">등록취소</button>
								<button type="button" class="btn btn-info btn-sm"
									style="margin-top: 3px; float: center;" onclick="sendOk();">등록하기</button>
							</div>
						</div>
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