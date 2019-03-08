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
/* $(function() {
	$("form input[name=startDay]").datepicker({
		showMonthAfterYear: true
		,defaultDate: "2019-03-07"
		// ,minDate: 0, maxDate: "+5D"
		//,minDate: -3, maxDate: "+1M+5D"
		,minDate: 0, maxDate: "+2M"
	});
	
}); */

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
	
	f.action = "<%=cp%>/reserve/reserveSubmit.do";
	f.submit();
	}

	function price() {
		var num = $("form select[name=barbecue1]").val();
		document.getElementById("bar01").innerHTML = num + "원";
	}

	function price2() {
		// var price = $("#bar01").val();
		if ($("form checkbox[name=chk_service1] checked:checked")) {
			// alert(price);
		}
	}
	
	function roomPrice(){
		var num = $("form select[name=room]").val();
		document.getElementById("roomPrice").innerHTML = num + "원";
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
								<option value="">선택</option>
								<option value="100000">방1</option>
								<option value="120000">방2</option>
								<option value="200000">방3</option>
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
						<td><input type="date" id="startDay" name="startDay"></td>

					</tr>
					<tr>
						<th>체크아웃 날짜</th>
						<td><input type="date" id="endDay" name="endDay"></td>

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
								<option value="1">1명</option>
								<option value="2">2명</option>
								<option value="3">3명</option>
								<option value="4">4명</option>
								<option value="5">5명</option>
								<option value="6">6명</option>
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
							<td><select id="barbecue1" name="barbecue1" onchange="price();">
							<option value="0">안함</option>
							<option value="20000">1개</option>
							<option value="40000">2개</option>
							<option value="60000">3개</option>
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
						<td>0원</td>
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
						<td><input type="text" name="reserve_name"></td>
						<th>비밀번호</th>
						<td><input type="password" name="reserve_pwd"></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td colspan="3"><input type="text" name="reserve_email" style="width:520px;"></td>
					</tr>
					<tr>
						<th>연락처</th>
						<td><input type="text" name="reserve_tel"></td>
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
					<button type="button" onclick="getDate();">예약하기</button>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

	<script type="text/javascript" src="<%=cp%>/resource/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="<%=cp%>/resource/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>