<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>

<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.css" type="text/css">
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(function() {
	var now = new Date();
	
	$("#startDay").datepicker({
		showMonthAfterYear: true
		,showOn: "button"
		,defaultDate: now
		,minDate: now, maxDate: "+2M"
		,buttonImage: "<%=cp %>/resource/images/calendar.gif"
		,buttonImageOnly: true
	});
	
	$("#endDay").datepicker({
		showMonthAfterYear: true
		,showOn: "button"
		,defaultDate: now
		,minDate: now, maxDate: "+2M"
		,buttonImage: "<%=cp %>/resource/images/calendar.gif"
		,buttonImageOnly: true
	});
	
	$(".ui-datepicker-trigger").css({position: "relative", top:"3px", left:"3px"});

});

function dateToYYYYMMDD(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + pad(date.getMonth()+1) + pad(date.getDate());
} 

function getDate(){
	var f = document.reserve_form1;
	var day = new Date();
	
	day = dateToYYYYMMDD(day);      // 오늘날짜 
	
	day = parseInt(day);            // 날짜 정수 변환
	
	var val = f.startDay.value;     // 입력 날짜
	var regexp=/(\.)|(\-)|(\/)/g;
	val = val.replace(regexp, "");  // 입력날짜 '-' 제거
	
	val = parseInt(val);            // 입력날짜 정수 변환
	
	if(! val){
		alert("체크인 날짜를 선택해주세요.");
		return;
	}
	
	var val2 = f.endDay.value;     // 입력 날짜
	val2 = val2.replace(regexp, "");  // 입력날짜 '-' 제거
	
	val2 = parseInt(val2);            // 입력날짜 정수 변환
	if(val2<val || val2<day){
		alert("체크아웃 날짜는 체크인 날짜보다 이전일 수 없습니다.");
		$("#endDay").val("");
		return;
	}
	
	val = f.endDay.value;
	if(! val){
		alert("체크아웃 날짜를 선택해주세요.");
		return;
	}
	
	sendOk();
	
}

function sendOk(){
	var f = document.reserve_form1;
	
	
		var val = f.reservename.value;
		if(! val){
			alert("예약자 이름을 입력해주세요.");
			return;
		}
		
		val = f.pwd.value;
		if(! val){
			alert("비밀번호를 입력해주세요.");
			return;
		}
		
		val = f.usertel.value;
		if(! val){
			alert("연락처를 입력해주세요.");
			return;
		}
	
	
	alert("예약이 완료되었습니다. 예약확인 및 취소는 예약확인/취소 페이지를 이용해주세요.");
	f.action = "<%=cp%>/reserve/reserveSubmit.do";
	f.submit();
}

	function bbcPrice() {
		var num = parseInt($("form select[name=barbecue1]").val());    // ex) 1, 2, 3
		var price = 20000;
		document.getElementById("bar01").innerHTML = num*price + "원";
		
	}
	
	function roomPrice(){		
		var num = parseInt($("form select[name=room]").val());    // ex) 1, 2, 3
		document.getElementById("roomPrice").innerHTML = num + "원";
		
		
		var room1 = ["1","2","3","4","5","6"];
		var room2 = ["1","2","3","4","5","6","7","8"];
		var room3 = ["1","2","3","4","5","6","7","8","9","10"];
		var room4 = ["1","2","3","4","5","6","7"]
			
		var selectRoom = $("#room").val();
		
		var changePerson;
		
		if(selectRoom == 100000){
			changePerson = room1;
		} else if(selectRoom == 150000){
			changePerson = room2;
		} else if(selectRoom == 150000){
			changePerson = room3;
		} else if(selectRoom == 200000){
			changePerson = room4;
		}
		
		$("#adult").empty();
		
		if($("form select[name=room]").val()!=0){
			for(var i=0; i<changePerson.length; i++){
				var option = $("<option value="+changePerson[i]+">"+changePerson[i]+"명"+"</option>");
				$("#adult").append(option);
			}
		}
		
	}
	
	function price(){
		var num = 0;
		var num1 = parseInt($("form select[name=room]").val()); 
		var num2 = parseInt($("form select[name=barbecue1]").val());
		var price2 = 20000;
		var result = num1 + num2*price2;
		
		if(num1==0){
			alert("객실을 선택해주세요");
			$("#chk1").attr("checked",false);
			return;
		}
		
		if($("form select[name=room]").on("change",function(){
			price();
			})
		)
		
		document.getElementById("priceALl").innerHTML = result + "원";
	}
	
	function resultPrice(){
		
	}
	
</script>
</head>

<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	<div class="reserve">
		<div>
			<form name="reserve_form1" method="post">
				<p class="mt10 mb10">객실</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 30%" />
						<col style="width: 20%" />
						<col style="width: 30%" />
					</colgroup>
					<tr>
						<th>객실명</th>
						<td><select id="room" name="room" onchange="roomPrice();">
								<option value="0">선택</option>
							<c:forEach var="dto" items="${roomList}">
								<option value="${dto.price}">${dto.roomName}</option>
							</c:forEach>
						</select></td>
						<th>가격</th>
						<td id="roomPrice">0원</td>
					</tr>
				</table>
				<p class="mt10 mb10">예약 날짜</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 30%" />
						<col style="width: 20%" />
						<col style="width: 30%" />
					</colgroup>
					<tr>
						<th>체크인 날짜</th>
						<td><input type="text" id="startDay" name="startDay" readonly="readonly"></td>

					</tr>
					<tr>
						<th>체크아웃 날짜</th>
						<td><input type="text" id="endDay" name="endDay" readonly="readonly"></td>

					</tr>
				</table>
				<p class="mt10 mb10">인원</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 30%" />
						<col style="width: 20%" />
						<col style="width: 30%" />
					</colgroup>
					<tr>
						<th>성인</th>
						<td><select id="adult" name="adult">
								<option value="0">0명</option>
							</select></td>
						<th>아동</th>
						<td><select id="child" name="child">
							<option value="0">0명</option>
							<option value="1">1명</option>
							<option value="2">2명</option>
						</select></td>
					</tr>
				</table>
				<p class="mt10 mb10">부가서비스</p>
				<table class="tb_basic">
					<colgroup>
						<col style="width: 25%" span="4" />
					</colgroup>
					<thead>
						<tr>
							<th>메뉴</th>
							<th>수량</th>
							<th>단가</th>
							<th>가격</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>바베큐2인</td>
							<td><select id="barbecue1" name="barbecue1" onchange="bbcPrice();">
							<option value="0">안함</option>
							<option value="1">1개</option>
							<option value="2">2개</option>
							<option value="3">3개</option>
						</select></td>
							<td>0원</td>
							<td id="bar01">0원</td>
						</tr>
					</tbody>
				</table>
				<p class="mt10 mb10">결제 금액</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width: 20%" />
					</colgroup>
					<tr>
						<th>금액</th>
						<td id="priceALl"><input type="checkbox" id="chk1" name="chk1" onclick="price();"><label for="chk1">금액확인</label></td>
					</tr>
				</table>
				<p class="mt10 mb10">예약자 정보</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 30%" />
						<col style="width: 20%" />
						<col style="width: 30%" />
					</colgroup>
					<tr>
						<th>예약자 이름</th>
						<c:if test="${sessionScope.member.userId==null}">
						<td><input type="text" name="reservename"></td>
						</c:if>
						<c:if test="${sessionScope.member.userId!=null}">
						<td><input type="text" name="reservename" value="${sessionScope.member.userName}" style="border: none;" readonly="readonly"></td>
						</c:if>
						<th>비밀번호</th>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td colspan="3"><input type="text" name="useremail" style="width:520px;"></td>
						
					</tr>
					<tr>
						<th>연락처</th>
						<td><input type="text" name="usertel"></td>
						<th>입금 은행</th>
						<td><select id="bank" name="bank">
							<option value="">선택</option>
							<option>농협</option>
							<option>국민은행</option>
							<option>신한은행</option>
							<option>우리은행</option>
						</select></td>
					</tr>
					<tr>
						<th>요청사항</th>
						<td colspan="3"><textarea name="reserve_memo" style="width:520px"></textarea></td>
					</tr>
				</table>
				<div class="reserve_btn">
				<input type="hidden" value="${rm_dto.roomNum}" id="roomNum" name="roomNum">
				<input type="hidden" value="${r_dto.reserveNum}" id="reserveNum" name="reserveNum">
				
					<button type="button" onclick="getDate();" id="btn">예약하기</button>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
	
</body>
</html>