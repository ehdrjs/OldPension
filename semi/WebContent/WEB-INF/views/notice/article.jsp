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
<meta charset="UTF-8">
<title>펜션</title>
<jsp:include page = "/WEB-INF/views/layout/import.jsp"></jsp:include>
<style type="text/css">
*{
	margin:0px; padding:0px
}
body{
	font-size: 14px;
	font-family:"맑은 고딕", 나눔고딕, 돋움, sans-serif;
	color: black;
}
#nArticle{
	width:800px;
	margin: 30px auto 0;
	clear: both;
}

#nTitle ul{
	clear:both;
	border-collapse: collapse;
	list-style: none;
	text-align: center;
	align-contents : center;
}
#nTitle ul li{
	float: left;
}
.nTitleName{
	width:200px; 
	font-weight: bold; 
	align-items: center; 
	border-bottom: 1px solid black; 
	border-right: 1px solid black;
	border-left: 1px solid black;
}
.nTitleContent{
	width:593px; 
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	text-align: left;
}
.nBtn{
	border: 1px solid orange;
	border-radius: 2px;
	background: #FFF3DA;
	color : #FF7A12;
	width: 100px;
	height: 40px;
}
#nContent ul{
	clear: both;
	list-style: none;
	align-content: center;
	border-left: 1px solid black;
	border-right: 1px solid black;
	width : 795px;
	border-bottom: 1px solid black;
	height: 300px
}
.nFiles ul{
	clear: both;
	list-style: none;
	align-content: center;
	border-left: 1px solid black;
	border-right: 1px solid black;
	border-bottom: 1px solid black;
	width : 795px;
}

</style>

<script type="text/javascript">
function deleteOk(num){
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url = "<%=cp%>/notice/delete.do?page${page}&listNum=num";
		location.href = url;
	}
}
</script>
</head>
<body>
<div class = "header">
	<jsp:include page = "/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<h3>| 공지 내용</h3>
<div id = "nArticle">
	<div id = "nTitle">
		<ul>
			<li class = "nTitleName" style="border-top: 1px solid black">글쓴이</li>
			<li class = "nTitleContent" style="border-top: 1px solid black">${dto.userId }</li>
		</ul>
		<ul>
			<li class = "nTitleName">제목</li>
			<li class = "nTitleContent">${dto.noticeSubject }</li>
		</ul>
		<ul>
			<li class = "nTitleName">날짜</li>
			<li class = "nTitleContent">${dto.noticeDate }</li>
		</ul>
	</div>
	
	<div id = "nContent">
	
		<ul style = "border-bottom: 1px solid black">
			<li>${dto.noticeContent }</li>
		</ul>
		
	
		
		
	</div>
	<div class = "nFiles">
		<c:if test="${not empty dto.noticeSubject}">
		<ul>
			<li>
				<a href = "<%=cp %>/notice/download_ok.do?listNum=${listNum }&page=${page}">${dto.originalFileName }</a>		
			</li>
		</ul>
		</c:if>
	</div>
	
	<c:if test="${sessionScope.member.userRole.equals('admin')}">
	
		<div align="right" style="padding-top: 20px">
			<button type = "button" class = "nBtn" onclick = "javascript:location.href='<%=cp%>/notice/update.do?page=${page }&listNum=${listNum }'">수정</button>
			
			<button type = "button" class = "nBtn" onclick = "ndeleteOk('${listNum}');">삭제</button>
		</div>
		
	</c:if>
</div>

<div class = "footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>