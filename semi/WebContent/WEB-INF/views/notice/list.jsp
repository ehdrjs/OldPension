<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page = "/WEB-INF/views/layout/import.jsp"></jsp:include>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
*{
	margin: 0px; padding: 0px;
}

body {
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
}

.board {
	width: 700px; margin: 30px auto 0;height: 700px
}

.list-title {
	clear:both;
}
.list-title ul{
	clear:both;
	list-style:none;
	height:35px;
	background-color: green;
	color: #ffffff;
}
.list-title li {
	float:left;
	height:35px;
	line-height:35px;
	text-align:center;
}

.list-row ul{
	clear:both;
	list-style:none;
}
.list-row li {
	float:left;
	height:35px;
	line-height:35px;
	text-align:center;
	border-bottom:1px solid red;
}
.board .num {width:50px;}
.board .subject {width:410px;cursor: pointer;}
.board .name {width:95px;}
.board .created {width:85px;}
.board .download {width:60px;cursor: pointer;}

.list-row .subject {text-align: left; width:400px; padding-left: 10px; }

</style>
<script type="text/javascript">
function article(){
	var f = document.noticeList;
	
}

</script>
</head>
<body>
<div class = "header">
	<jsp:include page = "/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>


<h3>|공지사항</h3>
<div class="board">
	<div class="list-title">
			<ul>
				<li class="num">번호</li>
				<li class="subject">제목</li>
				<li class="name">작성자</li>
				<li class="created">작성일</li>
				<li class="download">다운로드</li>
			</ul>
	</div>
	
	<form name = "noticeList "method="post" action="<%=cp%>/notice/created.do">
		
			<div class="list-row">
			<c:forEach var = "dto" items="${list }">
					<ul>
						<li class="num" >${dto.rnum }</li>
						<li class="subject" onclick="javascript:location.href='<%=cp%>/notice/article.do?page=${page }&listNum=${dto.noticeNum }'">${dto.noticeSubject }</li>
						<li class="name">${dto.userId }</li>
						<li class="created">${dto.noticeDate }</li>
						<c:if test = "${not empty dto.saveFileName}">
							<li class="download" onclick="javascript:location.href = '<%=cp%>/notice/download_ok.do?page=${page}&listNum=${dto.noticeNum }'">
								<img alt="실패" src="<%=cp %>/img/download.png" style="width:20px; height: 20px">
							</li>
						</c:if>
						<c:if test = "${empty dto.saveFileName}">
							<li class="download"></li>
						</c:if>		
					</ul>
			</c:forEach>
			</div>
			
		
	</form>
		<c:if test = "${sessionScope.member.userRole.equals('admin')}">
		<div align="right" style="margin-top: 10">
			<button type="button" name = "createdBtn" onclick = "javascript:location.href ='<%=cp%>/notice/created.do?page=${page }'">등록하기</button>
		</div>
		</c:if>
	<div align="center">
		<c:if test="${dataCount == 0 }">
			게시물 없다.
		</c:if>
		<c:if test="${dataCount != 0 }">
			 ${paging }
		</c:if>
	
	</div>
	
		
</div>
	


<div class = "footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>