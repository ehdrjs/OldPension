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
	width:600px;
	margin: 30px auto 0;
	clear: both;
}

#nTitle ul{
	clear:both;
	border-collapse: collapse;
	list-style: none;
	text-align: center;
	align-content: center;
	
}
#nTitle ul li{
	align-content: center;
	align-item : center;
	float: left;
}
.nTitleName{
	width:200px; 
	font-weight: bold; 
	align-items: center; 
	border-bottom: 1px solid black; 
	border-right: 2px solid black;
	border-left: 2px solid black;
	height: 40px;
	background: #eee;
}
.nTitleContent{
	width:591px; 
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	text-align: left;
	height: 40px;
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
a:hover, a:active {
	color: tomato;
	text-decoration: underline;
}
#noticeArticleFile a{
color : blue;
}

</style>

<script type="text/javascript">
function ndeleteOk(){
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url = "<%=cp%>/notice/delete.do?page=${page}&listNum=${listNum}";
		location.href = url;
	}
}
</script>
</head>
<body>
<div class = "header">
	<jsp:include page = "/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div style="background:#E3F5DE; margin: 30px auto 0; z-index: 3">
<div id = "nArticle" style="padding-top 20px;border: 1px solid black; border-radius: 10px; box-shadow: #eee; background: white; z-index: 1">
	<div id = "nTitle">
		<h3 style="padding-bottom: 10px"> 공지 내용</h3>
		<div align="center" style="margin-bottom: 10px; height: 50px; border-bottom: 2px solid black">
			<h1>${dto.noticeSubject }</h1>
		</div>

		<div align="right">
			<div style="width: 150px" align = "left">
				<b>글쓴이 :&nbsp</b>${dto.userId }
			</div>	
		</div>
		<div align="right">
			<div style="width: 150px" align = "left">
			<b>날짜 :&nbsp</b>${dto.noticeDate }
			</div>
		</div>
	</div>
	<div align="center" style="height: 320px; border-bottom: 1px solid black;">
		<div id = "nContent" style="border: 1px solid black; border-radius:5px ;width:500px;height: 300px;text-align: left;" align="left">
				${dto.noticeContent}
				<br>
				
				<c:if test="${not empty image }">
				<div align="center">
					<img alt="" src="<%=cp%>/uploaded/notice/${image}" style="max-width: 100px; height: auto;">
				</div>
				</c:if>
				
		</div>
	</div>
	<div id = "noticeArticleFile" style = "border-bottom: 1px solid black;" align="right">
		<c:if test="${not empty dto.noticeSubject}">
				<a href = "<%=cp %>/notice/download_ok.do?listNum=${listNum }&page=${page}" style="font-weight: bold;" onmouseup="">${dto.originalFileName }</a>		
		</c:if>
	</div>
	
	<div align="right" style="z-index: 2; padding-right: 10px; padding-top:10px; padding-bottom: 10px" >
	<c:if test="${sessionScope.member.userRole.equals('admin')}">
			<button type = "button" class = "nBtn" onclick = "javascript:location.href='<%=cp%>/notice/update.do?page=${page }&listNum=${listNum }'">수정</button>	
			<button type = "button" class = "nBtn" onclick = "ndeleteOk();">삭제</button>
	</c:if>
	<button type = "button" class = "nBtn" onclick = "javascript:location.href='<%=cp%>/notice/list.do?page=${page }'">리스트</button>
	</div>
	
</div>

	
</div>
<div class = "footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>