<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("utf-8");
	String cp = request.getContextPath();

	String name = request.getParameter("name");
	String room = request.getParameter("room");
	
	Map<String, String[]> map = request.getParameterMap();
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

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
					<col style="width:20%"/>
					<col style="width:30%"/>
					<col style="width:20%"/>
					<col style="width:30%"/>
				</colgroup>
				<tr>
					<th>객실명</th>
					<td>OO방</td>
				</tr>
			</table>
			<p class="mt10 mb10">예약 날짜</p>
			<table class="tb_basic_row">
				<colgroup>
					<col style="width:20%"/>
					<col style="width:30%"/>
					<col style="width:20%"/>
					<col style="width:30%"/>
				</colgroup>
				<tr>
					<th>체크인 날짜</th>
					<td>${dto.startDay}</td>
					
				</tr>
				<tr>
					<th>체크아웃 날짜</th>
					<td>20OO년 O월 O일</td>
					
				</tr>
				<tr>
					<th>숙박 기간</th>
					<td>O박 O일</td>
					
				</tr>
			</table>
			<p class="mt10 mb10">인원</p>
			<table class="tb_basic_row">
				<colgroup>
					<col style="width:20%"/>
					<col style="width:30%"/>
					<col style="width:20%"/>
					<col style="width:30%"/>
				</colgroup>
				<tr>
					<th>인원</th>
					<td> 
					O명
					</td>
				</tr>
			</table>
			<p class="mt10 mb10">부가서비스</p>
			<table class="tb_basic">
				<colgroup>
					<col style="width:25%" span="4"/>
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
						<td>0개</td>
						<td>20000원</td>
						<td id="bar01">0원</td>
					</tr>
				</tbody>
			</table>
			<p class="mt10 mb10">결제 금액</p>
			<table class="tb_basic_row">
				<colgroup>
					<col style="width:20%"/>
				</colgroup>
				<tr>
					<th>금액</th>
					<td>${dto.price}</td>
				</tr>
			</table>
			<p class="mt10 mb10">예약자 정보</p>
				<table class="tb_basic_row">
					<colgroup>
						<col style="width:20%"/>
						<col style="width:30%"/>
						<col style="width:20%"/>
						<col style="width:30%"/>
					</colgroup>
					<tr>
						<th>예약번호</th>
						<td>${dto.reserveNum}</td>
						<th>예약자 이름</th>
						<td>${dto.userName}</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${dto.tel}</td>
						<th>입금 은행</th>
						<td>${dto.bank}</td>
					</tr>
					<tr>
						<th>요청사항</th>
						<td colspan="3">${dto.reserveMemo}</td>
					</tr>
				</table>
				<div class="reserve_btn">
					<button type="button" onclick="">메인으로</button>
					<button type="button">예약취소</button>
				</div>
			</form>
		</div>
	</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>