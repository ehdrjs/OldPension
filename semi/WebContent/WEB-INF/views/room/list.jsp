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

function updateRoom(roomNum) {
    var query = "roomNum="+roomNum;
    var url = "<%=cp%>/room/update.do?" + query;

    location.href=url;
}

function deleteRoom(roomNum) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var query = "roomNum="+roomNum;
		var url="<%=cp%>/room/delete.do?"+query;
		location.href=url;
	}
}

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
		<c:forEach var="dto" items="${roomList}">
			<div class="review_page" style="width:60px"><a class="btn_noDeco" href="<%=cp%>/room/list.do?roomNum=${dto.roomNum}">&nbsp;&nbsp;&nbsp;${dto.roomName}&nbsp;&nbsp;&nbsp;</a></div>
		</c:forEach>
			<c:if test="${admin=='admin'}">
				<div class="review_page_none" style="width:60px; height:300px;"> </div>
				<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="<%=cp%>/room/write.do">&nbsp;&nbsp;&nbsp;등록&nbsp;&nbsp;&nbsp;</a></div>
				<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="#" onclick="updateRoom(${roomDTO.roomNum}); return false;">&nbsp;&nbsp;&nbsp;수정&nbsp;&nbsp;&nbsp;</a></div>
				<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="#" onclick="deleteRoom(${roomDTO.roomNum}); return false;">&nbsp;&nbsp;&nbsp;삭제&nbsp;&nbsp;&nbsp;</a></div>
			</c:if>	
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
				<div class="roomTitle">▶ ${roomDTO.roomName }</div>
				<div>
					<img class="room_mainPhoto" src="<%=cp%>/img/review001.jpg"/>
				</div>
				<table>
					<tr>
						<td><span class="roomBold">- 기준인원 :</span></td>
						<td><span>&nbsp;${roomDTO.roomMin}명</span></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 최대인원 : </span></td>
						<td><span>&nbsp;${roomDTO.roomMax}명</span></td>
					</tr>
					<tr>
						<td><span class="roomBold">- 방&nbsp;&nbsp;상태  : </span></td>
						<td><span>&nbsp;${roomDTO.isRoomOK}</span></td>
					</tr>
					<tr><td><span>&nbsp;</span></td><td><span>&nbsp;</span></td></tr>
					<tr>
						<td><span class="roomBold">- 가격표 </span></td>
						<td><span></span></td>
					</tr>
					<c:forEach var="dto" items="${priceList}">	
						<tr>
							<td><span class="roomBold">&nbsp;&nbsp;&nbsp;${dto.week} </span></td>
							<td><span><fmt:formatNumber value="${dto.price}" type="number"/> 원</span></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="room_article" style="padding-top:50px; height:500px;">
				<div>
				${roomDTO.roomContent}
				</div>
			</div>
		</div>
		
	</div>
</div>


</body>
</html>