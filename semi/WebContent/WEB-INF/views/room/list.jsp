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
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">

<script type="text/javascript">
jQuery(function(){
	jQuery(".room_mini").click(function(){
		jQuery("#img").attr({
			src:"<%=cp%>/img/review001.jpg"
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
	<div class="room_bodyContainer">
		<h3><span style="font-family:Webdings;">4</span> 방소개</h3>
	</div>
	<div class="sidebarRoom" style="float: left">
		<div class="review_page_none" style="width:60px"> </div>
		<div class="review_page" style="width:60px">청룡</div>
		<div class="review_page" style="width:60px">백호</div>
		<div class="review_page" style="width:60px">주작</div>
		<div class="review_page" style="width:60px">현무</div>
		<div class="review_page" style="width:60px">해태</div>
		<div class="review_page_none" style="width:60px; height:300px;"> </div>
		<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="<%=cp%>/room/write.do">등록</a></div>
		<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="<%=cp%>/room/write.do">수정</a></div>
		<div class="review_page_admin" style="width:60px;">삭제</div>		
	</div>
	<div class="room_form">
		<div class="room_sidePhoto">
			<div style="width:1px; height: 22px; background-color:none; margin-bottom:-1px;"></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/review001.jpg"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/review002.jpg"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/review003.jpg"/><span class="mask"></span></a></div>
			<div class="room_miniPhoto"><a href="#"><img class="room_mini" src="<%=cp%>/img/review004.jpg"/><span class="mask"></span></a></div>
		</div>
		<div class="room_articleForm">
			<div class="room_article">
				<div class="roomTitle">▶ 방이름</div>
				<div>
					<img class="room_mainPhoto" src="<%=cp%>/img/review001.jpg"/>
				</div>
				<table>
					<tr>
						<td><span class="roomBold">- 기준인원 :</span></td>
						<td><span>인원수</span></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 최대인원 : </span></td>
						<td><span>인원수</span></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 방&nbsp;&nbsp;상태  : </span></td>
						<td><span>가능</span></td>
					</tr>
				</table>
			</div>
			<div class="room_article" style="padding-top:50px; height:500px;">
				<div>
				남이섬은 오늘이 좋다.<br>
				관광객들에게 지금 준비 중이니<br> 
				다음에 오시라고 할 수는 없다.<br> 
				이것은 관광지에서<br> 
				일하는 사람들의 숙명 아닌가.<br>
				내일 오는 사람이 있으니<br> 
				내일은 또 새로워져야 한다.<br> 
				한 가지가 좋다고 보존만 한다면<br> 
				유적지가 되지 않겠는가.<br>
				설레는 봄,<br> 
				싱그러운 여름,<br> 
				시가 되는 가을 단풍도 좋지만<br> 
				호텔 정관루에서 하룻밤을 보낸 새벽,<br> 
				소복이 쌓인 눈을 
				가지에 받치고 있는<br> 
				잣나무 앞에서는 아무 말도 할 수가 없다.<br> 
				그 새벽은 모두 나의 것이다.<br> 
				아무도 가지 않은 길, <br>
				내가 갈 수 있다. <br>
				
				<br>
				- 전명준의《볼펜 그림 남이섬》중에서 -<br>
				<br>
				</div>
			</div>
		</div>
		
	</div>
</div>


</body>
</html>