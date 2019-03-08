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

<script src="<%=cp%>/resource/js/jquery-1.11.0.min.js"></script>
<script src="<%=cp%>/resource/js/util.js"></script>

<script type="text/javascript">
$(function() {
	$("form input[name=startDay]").datepicker({
		showMonthAfterYear: true
		,defaultDate: "2019-03-07"
		// ,minDate: 0, maxDate: "+5D"
		//,minDate: -3, maxDate: "+1M+5D"
		,minDate: 0, maxDate: "+2M"
	});
	
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
	
	/* day = dateToYYYYMMDD(day);      // 오늘날짜 
	
	day = parseInt(day);            // 날짜 정수 변환
	
	var val = f.startDay.value;     // 입력 날짜
	var regexp=/(\.)|(\-)|(\/)/g;
	val = val.replace(regexp, "");  // 입력날짜 '-' 제거
	
	val = parseInt(val);            // 입력날짜 정수 변환 */
	
	
	
	if(! val){
		alert("체크인 날짜를 선택해주세요.");
		return;
	}
	
	var val2 = f.endDay.value;     // 입력 날짜
	val2 = val2.replace(regexp, "");  // 입력날짜 '-' 제거
	
	val2 = parseInt(val2);            // 입력날짜 정수 변환
	if(val2<val || val2<day){
		alert("체크아웃 날짜를 다시 선택해주세요.");
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
	
	var val = f.reserve_name.value;
	if(! val){
		alert("예약자 이름을 입력해주세요.");
		return;
	}
	
	val = f.reserve_pwd.value;
	if(! val){
		alert("비밀번호를 입력해주세요.");
		return;
	}
	
	val = f.reserve_tel.value;
	if(! val){
		alert("연락처를 입력해주세요.");
		return;
	}
	
	f.action = "<%=cp%>/reserve/reserve_detail.do";
	f.submit();
}

function price() {
	var num1 = $("form select[name=barbecue1]").val();
	var price1 = 10000;
	var sum1 = price1*num1;
	document.getElementById("bar01").innerHTML = sum1+"원";
	
	var num2 = $("form select[name=barbecue2]").val();
	var price2 = 20000;
	var sum2 = price2*num2;
	document.getElementById("bar02").innerHTML = sum2+"원";
}

function price2() {
	// var price = $("#bar01").val();
	if($("form checkbox[name=chk_service1] checked:checked")){
		// alert(price);
	}
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
			<p class="clear mt10 mb10">객실</p>
			<div class="list_tb">
				<ul class="list4">
					<li class="head">객실명</li>
					<li>
						<select id="room">
								<option value="">선택</option>
								<option value="room1">방1</option>
								<option value="room2">방2</option>
								<option value="room3">방3</option>
						</select>
					</li>
					<li class="head">가격</li>
					<li>100000원</li>
				</ul>
			</div>
			<p class="clear mt10 mb10">예약 날짜</p>
			<div class="list_tb">
				<ul class="list2">
					<li class="head">체크인 날짜</li>
					<li><input type="text" id="startDay" name="startDay" value="체크인 날짜" readonly="readonly"></li>
					
				</ul>
				<ul class="list2">
					<li class="head">체크아웃 날짜</li>
					<li><input type="text" id="endDay" name="endDay" value="체크아웃 날짜" readonly="readonly"></li>
					
				</ul>
			</div>
			<p class="clear mt10 mb10">인원</p>
			<div class="list_tb">
				<ul class="list4">
					<li class="head">성인</li>
					<li> 
						<select id="adult">
							<option value="">선택</option>
							<option value="1">1명</option>
							<option value="2">2명</option>
							<option value="3">3명</option>
							<option value="4">4명</option>
							<option value="5">5명</option>
							<option value="6">6명</option>
						</select>
					</li>
					<li class="head">아동</li>
					<li>
						<select id="kids">
							<option value="">선택</option>
							<option value="1">1명</option>
							<option value="2">2명</option>
						</select>
					</li>
				</ul>
			</div>
			<p class="clear mt10 mb10">부가서비스</p>
			<div class="list_tb">
				<ul class="list5">
					<li class="head">메뉴</li>
					<li class="head">수량</li>
					<li class="head">단가</li>
					<li class="head">가격</li>
					<li class="head">선택</li>			
				</ul>
				<ul class="list5">
					<li>바베큐1인/2인</li>
					<li>
						<select id="barbecue1" name="barbecue1" onchange="price();">
							<option value="0">선택</option>
							<option value="1">1개</option>
							<option value="2">2개</option>
							<option value="3">3개</option>
						</select>
					</li>
					<li>10000원</li>
					<li id="bar01">0원</li>
					<li><input type="checkbox" id="chk_service1" name="chk_service1" onclick="price2();"></li>
				</ul>
				<ul class="list5">
					<li>바베큐3인/4인</li>
					<li>
						<select id="barbecue2" name="barbecue2" onchange="price();">
							<option value="0">선택</option>
							<option value="1">1개</option>
							<option value="2">2개</option>
							<option value="3">3개</option>
						</select>
					</li>
					<li>20000원</li>
					<li id="bar02">0원</li>
					<li><input type="checkbox" id="chk_service2" name="chk_service2"></li>
				</ul>
			</div>
			<p class="clear mt10 mb10">결제 금액</p>
			<div class="list_tb">
				<ul class="list2">
					<li class="head">금액</li>
					<li>0원</li>
				</ul>
			</div>
			<p class="clear mt10 mb10">예약자 정보</p>
			<div class="list_tb">
				<ul class="list4">
					<li class="head">예약자 이름</li>
					<li><input type="text" name="reserve_name"></li>
					<li class="head">비밀번호</li>
					<li><input type="password" name="reserve_pwd"></li>
				</ul>
				<ul class="list4">
					<li class="head">연락처</li>
					<li><input type="text" name="reserve_tel"></li>
					<li class="head">입금 은행</li>
					<li>
						<select id="bank">
							<option value="">선택</option>
							<option>농협</option>
							<option>국민은행</option>
							<option>신한은행</option>
							<option>우리은행</option>
						</select>
					</li>
				</ul>
				<ul class="list2">
					<li class="head">요청사항</li>
					<li><textarea name="reserve_memo" style="width:100%"></textarea></li>
				</ul>
			</div>
			<div class="reserve_btn clear">
				<button type="button" onclick="getDate();">예약하기</button>
			</div>
		</form>
	</div>
</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>