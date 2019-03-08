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
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<script type="text/javascript">
jQuery(function(){
	jQuery(".room_mini").click(function(){
		jQuery("#img").attr({
			src:"<%=cp%>/img/img_not.png"
		});
	});
});

jQuery(function(){
	jQuery("body").on("click", ".room_form .room_mini", function(){
		var index = jQuery(".room_form .room_mini").index(this);
		jQuery(".room_form .mask").hide();
		
		var src = jQuery(this).attr("src");
		jQuery(".room_mainPhoto").attr("src", src);
		jQuery(this).next(".mask").show();
	});
});
</script>
<style type="text/css">




</style>
</head>
<body>
<div class="room">
	<div class="room_bodyContainer" style="width:400; height:150">
		<h3><span style="font-family:Webdings;">4</span> 방소개</h3>
	</div>
	<div class="sidebarRoom" style="float: left">
		<div class="review_page_none" style="width:50px"> </div>
		<div class="review_page" style="width:60px">청룡</div>
		<div class="review_page" style="width:60px">백호</div>
		<div class="review_page" style="width:60px">주작</div>
		<div class="review_page" style="width:60px">현무</div>
		<div class="review_page" style="width:60px">해태</div>
		<div class="review_page_none" style="width:60px; height:300px;"> </div>
		<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="#">등록완료</a></div>
		<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="<%=cp%>/room/list.do">돌아가기</a></div>		
	</div>
	<div class="room_form">
		<div class="room_sidePhoto">
			<div style="width:1px; height: 22px; background-color:none; margin-bottom:-1px;"></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/img_not.png"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/img_not.png"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/img_not.png"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/img_not.png"/><span class="mask"></span></a></div>
		</div>
		<div class="room_articleForm">
			<div class="room_article">
				<div class="roomTitle">▶ 방이름 : <input style="width:140px;" class="room_blank" name="roomName" type="text"></div>
				<div>
					<img class="room_mainPhoto" src="<%=cp%>/img/img_not.png"/>
				</div>
				<table>
					<tr>
						<td><span class="roomBold">- 기준인원 :</span></td>
						<td><input class="room_blank" name="roomMin" type="text"></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 최대인원 : </span></td>
						<td><input class="room_blank" name="roomMax" type="text"></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 방&nbsp;&nbsp;상태  : </span></td>
						<td><input class="room_blank" name="isRoomOk" type="text"></td>
					</tr>
				</table>
			</div>
			<div class="room_article" style="padding-top:10px; height:540px;">
				<div class="roomTitle">
					▶ 내용 적기
				</div>
				<textarea class="roomContent" name="roomContent">여기를 클릭하고 작성하세요.</textarea>
			</div>
		</div>
		
	</div>
</div>


</body>
</html>