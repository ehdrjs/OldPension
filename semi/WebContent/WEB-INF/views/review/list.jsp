<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<style type="text/css">

</style>
<script type="text/javascript">
function sendReview() {
	var uid="${sessionScope.member.userId}";
	if(! uid) {
		alert("로그인 하세요!");
		return;
	}
	
	var f = document.reviewForm;
	var str;
	
	str=f.review_inputText.value;
	if(!str){
		alert("내용을 입력하세요!");
		f.review_inputText.focus();
		return;
	}

	var mode="${mode}"
	if(mode=="create") {
		f.action="<%=cp%>/review/write_ok.do";
	} else if(mode=="update") {
		f.action="<%=cp%>/review/update_ok.do";
	}
	f.submit();
}

function deleteReview(reviewNum) {
	if(confirm("게시물을 삭제 하시겠습니까?")) {
		var query = "reviewNum="+reviewNum;
		var url="<%=cp%>/review/delete.do?"+query;
		location.href=url;
	}
}

</script>


</head>
<body>
<form name="reviewForm">
<div class="review">
	<div class="room_bodyContainer">
		<h3><span style="font-family:Webdings;">4</span> 방문후기</h3>
	</div>
	<div class="review_list">
		<div class="review_view" >
			<c:forEach var="dto" items="${reviewList}">
				<c:if test="${dto.admin!='admin'}">
					<div class="review_layout">
						<div class="review_content_info" style="font-size: 9pt; font-weight: 800; color:#777777; float: left;">${dto.userId}님</div>
						<div class="review_content">
						${dto.reviewContent}<br>
						<c:if test="${admin=='admin'||id==dto.userId}">
							<a class="review_ud" style="float : left;" href="#" onclick="deleteReview(${dto.reviewNum});return false;">삭제</a>
						</c:if>
						</div>
						<div class="review_content_info" style="font-size: 3pt; float: left">${dto.reviewDate}<br></div>
					</div>
				</c:if>
				<c:if test="${dto.admin=='admin'}">
					<div class="review_layout" style="clear:both;">
						<div class="review_content_info" style="font-size: 9pt; font-weight: 800; color:#777777; text-align: right;">${dto.userId}님 </div>
						<div class="review_reply">
						${dto.reviewContent}<br>
						<c:if test="${admin=='admin'}">
							<a class="review_ud" style="float : right;" href="#" onclick="deleteReview(${dto.reviewNum});return false;">삭제</a>
						</c:if>
						</div>
						<div class="review_content_info" style="margin-bottom: 10px ;font-size: 3pt; float: right ; text-align: right;">${dto.reviewDate}</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="review_input" >
			<c:if test="${admin!='logout'}">
				<textarea rows="4" name="review_inputText" class="review_inputText" style="display:block; width: 100%; padding: 6px 12px; 
						  box-sizing:border-box;" required="required" placeholder="이곳에 기입하세요.">${dto.reviewContent}</textarea>
			</c:if>
			<c:if test="${admin=='logout'}">
				<textarea rows="4" name="review_inputText" class="review_inputText" style="background-color:#f0f0f0 ;display:block; width: 100%; padding: 6px 12px; 
						  box-sizing:border-box;" required="required" readonly="readonly">로그인을 해야만 작성할 수 있습니다.</textarea>
			</c:if>
			<div style="float : right;">
				<button class="review_btn" type="button" onclick="sendReview(); return false;">${mode=='update'?'내용수정':mode=='create'?'리뷰쓰기':'답변하기'}</button>
				<button class="review_btn" type="reset">다시쓰기</button>
			</div>
		</div>		
	</div>
	${paging}

	
</div>
</form>
</body>
</html>