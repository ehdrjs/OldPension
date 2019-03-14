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
	background:green;
	font-size: 15px;
	color: white;
	width: 100px;
	border: 1px solid black;
	text-align: center;
	height : 20px;
}
.nT{
	border: 1px solid black;
	width: 589px;
	height : 20px;
	
}
a:hover, a:active {
	color: tomato;
	text-decoration: underline;
}
.nBtn{
	border: 1px solid orange;
	border-radius: 2px;
	background: #FFF3DA;
	color : #FF7A12;
	width: 100px;
	height: 40px;
	cursor: pointer
}

</style>
<script type="text/javascript">

function deleteFile(){
	if(confirm("정말 삭제하시겠습니까?")){
		 var url="<%=cp%>/notice/deleteFile.do?listNum=${listNum}&page=${page}";
	 	 location.href=url;
	}	
}
function sendUpdate(){
	var f = document.nupdateForm;
	f.action = "<%=cp%>/notice/update_ok.do?page=${page}&listNum=${listNum}";
	f.submit();
}
function updateNotice(){
	var f = document.ncreatedForm;
	
	var str = f.subject;
	if(!str.value){
		str.focus();
		alert("제목입력하십시오");
		return;
	}
	
	str = f.content;
	if(!str.value){
		str.focus();
		alert("내용입력하십시오");
		return;
	}
	f.action = "<%=cp%>/notice/created_ok.do?page=${page}";
	f.submit();
}



</script>
</head>
<body>
<div class = "header">
	<jsp:include page = "/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>



<c:if test="${mode=='created' }">
<form name = "ncreatedForm" method="post" action="" enctype="multipart/form-data">
<div class = "createN">
	<h3>|공지사항 등록하기</h3>
	<ul>
		<li class = "nN">공지 제목</li>
		<li class = "nT"><input type = "text" name = "subject" style="width: 585px;height: 20px"></li>
	</ul>
	<ul>
		<li class = "nN">작성자</li>
		<li class = "nT" style="height: 20px;">${sessionScope.member.userId }</li>
	</ul>

	<ul>
		<li class = "nN" style="height: 501px">공지 내용</li>
		<li class = "nT" style="height: 500px;">
			<textarea  name = "content" style="height: 500px; width:585px"></textarea>
		</li>
	</ul>
	<ul>
	

		<li class = "nN" style="height: 24px;">업로드 파일</li>
		<li style="border: 1px solid black;width: 589px">
		<input type = "file" name = "upload" size = "53">
		</li>
	</ul>
	<div align="right">
	<p><br><button class = "nBtn" type="submit" onclick="updateNotice();">등록하기</button></p>
	</div>
</div>

</form>
</c:if>


<c:if test="${mode=='update' }">

<form name = "nupdateForm" method="post" action="" enctype="multipart/form-data">

<div class = "createN">
	<ul>
		<li class = "nN">공지 제목</li>
		<li class = "nT"><input type = "text" name = "subject" style="width: 585px" value = "${dto.noticeSubject}"></li>
	</ul>
	<ul>
		<li class = "nN">작성자</li>
		<li class = "nT">${dto.userId }</li>
	</ul>

	<ul>
		<li class = "nN" style="height: 500px">공지 내용</li>
		<li class = "nT" style="height: 500px;">
			<textarea  name = "content" style="height: 500px; width:585px">${dto.noticeContent }</textarea>
		</li>	
	</ul>
		<ul id = "noticeFileUpload">
			
				<li class = "nN" style="height: 24px;">업로드 파일</li>
				<c:if test="${empty dto.originalFileName }">
					<li style="border: 1px solid black; width: 589px;">
					<input type = "file" name = "upload" size = "53">
					</li>
				</c:if>
				<c:if test="${not empty dto.originalFileName }">
					<li style="border: 1px solid black;width: 585px">
					<a href="<%=cp%>/notice/download_ok.do?page=${page}&listNum=${listNum}">${dto.originalFileName }</a>
					<button type="button" onclick="deleteFile()">파일삭제</button> 
					</li>				
				</c:if>		
		</ul>
		
		<div align = "right">
			<p><button class = "nBtn" type="button" onclick="sendUpdate();">수정하기</button></p>
		</div>
</div>
</form>
</c:if>	
<div class = "footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
	
	

</body>
</html>