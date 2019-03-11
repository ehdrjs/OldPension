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
<title>공지사항 등록페이지</title>
<style type="text/css">
*{
	margin : 0px;
	padding: 0px;	
}
body{
	font-size: 14px;
	font-family:"맑은 고딕", 나눔고딕, 돋움, sans-serif;
}
.createN{
	width : 700px;
	margin: 30px auto 0; 
	height : 700;
}
.createN ul{
	clear: both;
	list-style:none;
}
.createN ul li{
	float :left;
	
	
}
.nN{
	background:#63C5DE;
	font-size: 15px;
	font-weight: bold;
	color: black;
	width: 100px;
	border: 2px solid black;
	border-radius: 5px;
	text-align: center;
}
.nT{
	border: 1px solid black;
	border-radius: 2px;
	width: 594px
	
}
a:hover, a:active {
	color: tomato;
	text-decoration: underline;
}

</style>
<script type="text/javascript">

function deleteFile(listNum){
	if(confirm("정말 삭제하시겠습니까?")){
		 var url="<%=cp%>/notice/deleteFile.do?listNum=${listNum}&page=${page}";
	 	 location.href=url;
	}	
}
function sendUpdate(listNum){
	
}


</script>
</head>
<body>
<div class = "header">
	<jsp:include page = "/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<h3>|공지사항 등록하기</h3>

<c:if test="${mode=='created' }">
<form name = "ncreatedForm" method="post" action="<%=cp%>/notice/created_ok.do?page=${page}" enctype="multipart/form-data">
<div class = "createN">
	<ul>
		<li class = "nN">공지 제목</li>
		<li class = "nT"><input type = "text" name = "subject" style="width: 594px"></li>
	</ul>
	<ul>
		<li class = "nN">작성자</li>
		<li class = "nT">${sessionScope.member.userId }</li>
	</ul>

	<ul>
		<li class = "nN" style="height: 500px">공지 내용</li>
		<li class = "nT" style="height: 500px; width:594px">
			<textarea  name = "content" style="height: 500px; width:594px"></textarea>
		</li>
	</ul>
	<ul>
		<li class = "nN">업로드 파일</li>
		<li class = "nT"><input type = "file" name = "upload" size = "53"></li>
	</ul>
	
</div>
<div>
	<button type="submit">등록하기</button>
</div>
</form>
</c:if>


<c:if test="${mode=='update' }">
<form name = "nupdateForm" method="post" action="" enctype="multipart/form-data">

<c:if test="${sessionScope.member.userId != dto.userId }">
<p>잘못 입력하셨습니다.</p>
</c:if> 
<div class = "createN">
	<ul>
		<li class = "nN">공지 제목</li>
		<li class = "nT"><input type = "text" name = "subject" style="width: 594px" value = "${dto.noticeSubject}"></li>
	</ul>
	<ul>
		<li class = "nN">작성자</li>
		<li class = "nT">${sessionScope.member.userId }</li>
	</ul>

	<ul>
		<li class = "nN" style="height: 500px">공지 내용</li>
		<li class = "nT" style="height: 500px; width:594px">
			<textarea  name = "content" style="height: 500px; width:594px">${dto.noticeContent }</textarea>
		</li>
	</ul>
		<ul id = "noticeFileUpload">
			
				<li class = "nN">업로드 파일</li>
				<c:if test="${empty dto.originalFileName }">
					<li style="border: 1px solid black; width: 594px">
					<input type = "file" name = "upload" size = "53">
					</li>
				</c:if>
				<c:if test="${not empty dto.originalFileName }">
					<li style="border: 1px solid black;width: 594px">
					<a href="<%=cp%>/notice/download_ok.do?page=${page}&listNum=${listNum}">${dto.originalFileName }</a>
					<button type="button" onclick="deleteFile('${listNum}')">파일삭제</button> 
					</li>
					
				</c:if>
			
		</ul>
	
	
</div>
<div>
	<button type="button" onclick="sendUpdate('${listNum}');">수정하기</button>
</div>
</form>
</c:if>	
<div class = "footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
	
	

</body>
</html>