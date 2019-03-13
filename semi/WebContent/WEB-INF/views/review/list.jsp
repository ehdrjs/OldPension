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
</head>
<body>
<div class="review">
	<div class="room_bodyContainer">
		<h3><span style="font-family:Webdings;">4</span> 방문후기</h3>
	</div>
	<div class="review_list">
		<div class="review_view" >
			<div class="review_layout">
				<div class="review_content_info" style="font-size: 9pt; font-weight: 800; color:#777777; float: left;">예정씨</div>
				<div class="review_content">
				안녕하세요.<br>
				이곳에 글을 기입합니다.<br>
				쓰는 만큼 늘어납니다.<br>
				그만쓰세요.<br>
				<a class="review_ud" style="float : left;" href="#">수정</a>
				<a class="review_bar" style="float : left;">&nbsp;|&nbsp;</a>
				<a class="review_ud" style="float : left;" href="#">삭제</a>
				<a class="review_ud" style="float : right;" href="#">▶ 답변</a>
				</div>
				<div class="review_content_info" style="font-size: 3pt; float: left">2019-02-04 09시 12분<br></div>
			</div>
			<div class="review_layout" style="clear:both;">
				<div class="review_content_info" style="font-size: 9pt; font-weight: 800; color:#777777; text-align: right;">동건씨 </div>
				<div class="review_reply">
				감사합니다.<br>
				재밌게 즐기셨는지요.<br>
				다음에 또 와주세요.<br>
				<a class="review_ud" style="float : right;" href="#">삭제</a>
				<a class="review_bar" style="float : right;" >&nbsp;|&nbsp;</a>
				<a class="review_ud" style="float : right;"  href="#" >수정</a>
				</div>
				<div class="review_content_info" style="margin-bottom: 10px ;font-size: 3pt; float: right ; text-align: right;">2019-02-06 12시 12분</div>
			</div>
		</div>
		<div class="review_input" >
			<textarea rows="4" name="reviewContent" class="review_inputText" style="display:block; width: 100%; padding: 6px 12px; box-sizing:border-box;" required="required" placeholder="이곳에 기입하세요."></textarea>
			<div style="float : right;">
				<button class="review_btn" type="button">보내기</button>
				<button class="review_btn" type="reset">다시쓰기</button>
			</div>
		</div>		
	</div>
	<div class="sidebar" style="float: left">
		<div class="review_page_none"> </div>
		<div class="review_page_np">▲</div>
		<div class="review_page">9</div>
		<div class="review_page">10</div>
		<div class="review_page">11</div>
		<div class="review_page">12</div>
		<div class="review_page">13</div>
		<div class="review_page_np">▼</div>
		<div class="review_page_none"> </div>		
	</div>
	
</div>
</body>
</html>