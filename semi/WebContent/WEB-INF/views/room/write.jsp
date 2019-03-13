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

function sendRoom() {
	var f = document.room_articleForm ;
	
	var mode="${mode}"
	if(mode=="create")
		f.action="<%=cp%>/room/write_ok.do";
	else if(mode=="update")
		f.action="<%=cp%>/room/update_ok.do";
	
	f.submit();
} 

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
		<c:forEach var="dto" items="${roomList}">
			<div class="review_page" style="width:60px"><a class="btn_noDeco" href="<%=cp%>/room/list.do?roomNum=${dto.roomNum}">&nbsp;&nbsp;&nbsp;${dto.roomName}&nbsp;&nbsp;&nbsp;</a></div>
		</c:forEach>
		<div class="review_page_none" style="width:60px; height:300px;"> </div>
		<div class="review_page_admin" style="width:60px;"><a class="btn_noDeco" href="#" onclick="sendRoom(); return false;">${mode=='update'?'수정완료':'등록완료'}</a></div>
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
		<form name="room_articleForm" >
			<div class="room_articleForm">
				<div class="room_article">
					<div class="roomTitle">▶ 방이름 : <input style="width:140px;" class="room_blank" name="roomName" type="text" value="${roomDTO.roomName}"></div>
					<div>
						<img class="room_mainPhoto" src="<%=cp%>/img/img_not.png"/>
					</div>
					<table>
						<tr>
							<td><span class="roomBold">- 기준인원 :</span></td>
							<td><input class="room_blank" name="roomMin" type="text" value="${roomDTO.roomMin}"></td>
						</tr>
						<tr>
							<td><span class="roomBold">- 최대인원 : </span></td>
							<td><input class="room_blank" name="roomMax" type="text" value="${roomDTO.roomMax}"></td>
						</tr>
						<tr>
							<td><span class="roomBold">- 방&nbsp;&nbsp;상태  : </span></td>
							<td><input class="room_blank" name="isRoomOK" type="text" value="${roomDTO.isRoomOK}"></td>
						</tr>
						<tr><td><span>&nbsp;</span></td><td><span>&nbsp;</span></td></tr>
						<tr>
							<td><span class="roomBold">- 가격표 </span></td>
							<td><span></span></td>
						</tr>
						
						<c:if test="${mode=='create'}">
							<tr>
								<td><span class="roomBold">&nbsp;&nbsp;&nbsp;주중 </span></td>
								<td><input class="room_blank" name="price_mid" type="text"></td>
							</tr>
							<tr>
								<td><span class="roomBold">&nbsp;&nbsp;&nbsp;주말 </span></td>
								<td><input class="room_blank" name="price_end" type="text"></td>
							</tr>
							<tr>
								<td><span class="roomBold">&nbsp;&nbsp;&nbsp;성수기 </span></td>
								<td><input class="room_blank" name="price_high" type="text"></td>
							</tr>
						</c:if>
						
						<c:if test="${mode=='update'}">
							<c:forEach var="dto" items="${priceList}">
								<tr>
									<td><span class="roomBold">&nbsp;&nbsp;&nbsp;${dto.week}</span></td>
									<td><input class="room_blank" name="price" type="text" value="${dto.price}"></td>
								</tr>
							</c:forEach>
						</c:if>
						
						
						<tr>
							<td><input type="hidden" name="roomNum" value="${roomDTO.roomNum}"></td>
							<td></td>
						</tr>
					</table>
				</div>
				<div class="room_article" style="padding-top:10px; height:540px;">
					<div class="roomTitle">
						▶ ${mode=='update'?'내용 수정':'내용 적기'}
					</div>
					<textarea class="roomContent" name="roomContent" >${roomDTO.roomContent}</textarea>
				</div>
			</div>
		</form>
	</div>
</div>

</body>
</html>