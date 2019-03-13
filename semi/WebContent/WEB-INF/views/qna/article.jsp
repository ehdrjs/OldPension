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
function deleteQna(num) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/qna/delete.do?qnaNum="+num+"&${query}";
		location.href=url;
	}
}
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
					<ul style="display: inline-block; width: 100%; margin:5px;">
						<li style="float: left;width: 408px;text-align:center;font-size:18px;"><c:if test="${dto.depth!=0}">[Re]</c:if>
						${dto.subject}</li>
						<li style="float: left; width: 170px; text-align:right; line-height:25px;">작성일 ${dto.date}</li>
						<li style="float: left;text-align: center;width: 100px; text-align:center">조회 ${dto.count}</li>
					</ul>
					<div>
						<ul style="display: inline-block; width: 100%;">
							<li style="clear:both;line-height: 40px;float:left;width: 580px; padding:0px 0px 0px 5px;">작성자&nbsp;${dto.userId}</li>
						<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='first'}">    
						<li style="float: left; width:49px">
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px; clean:both;" onclick="javascript:location.href='<%=cp%>/qna/update.do?qnaNum=${dto.num}&${query}';">수정</button>
						</li>
						<li style="float: left; width:49px">
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px;" onclick="deleteQna('${dto.num}');">삭제</button>
                         </li>
                         </c:if>
						</ul>
					</div>
					<ul style="clear: both; border: 1px solid #c4c4c4;  border-radius: 10px;">
						<li style="min-height:230px; padding:5px;">${dto.content}</li>
					</ul>
					<ul>
						<li>이전글: <c:if test="${empty preReadDto}">&nbsp;이전 게시물이 없습니다.</c:if>
						  <c:if test="${not empty preReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${preReadDto.num}&${query}">&nbsp;${preReadDto.subject}</a>
                  </c:if>
						</li>
					</ul>
					<ul>
						<li>다음글:<c:if test="${empty nextReadDto}">&nbsp;다음 게시물이 없습니다.</c:if>
						  <c:if test="${not empty nextReadDto}">
                         <a href="<%=cp%>/qna/article.do?qnaNum=${nextReadDto.num}&${query}">&nbsp;${nextReadDto.subject}</a>
                  </c:if></li>
					</ul>
					<div style="width:100%; text-align: center;">
						<c:if test="${sessionScope.member.userId=='first'}">   
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px; clean:both;" onclick="javascript:location.href='<%=cp%>/qna/reply.do?qnaNum=${dto.num}&page=${page}';">답변</button>
						</c:if>
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px; clean:both;" onclick="javascript:location.href='<%=cp%>/qna/qna.do?${query}';">돌아가기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>