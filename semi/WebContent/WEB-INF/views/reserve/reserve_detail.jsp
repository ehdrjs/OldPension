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

<script src="<%=cp%>/resource/js/jquery-1.11.0.min.js"></script>
<script src="<%=cp%>/resource/js/util.js"></script>

<script type="text/javascript">

</script>
</head>

<body>
<%
	Iterator<String> it = map.keySet().iterator();
	while(it.hasNext()){
		String _name = it.next();
		String[] ss = map.get(_name);
		for(String s : ss){
			out.print(s);
		}
	}
%>
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
					<td><%=room %></td>
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
					<td>20OO년 O월 O일</td>
					
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
					<th>성인</th>
					<td> 
					O명
					</td>
					<th>아동</th>
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
						<td>바베큐O인/O인</td>
						<td>0개</td>
						<td>0원</td>
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
					<td>0원</td>
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
						<td>20OOOOOO0001</td>
						<th>예약자 이름</th>
						<td><%=name %></td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>010-0000-0000</td>
						<th>입금 은행</th>
						<td>00은행</td>
					</tr>
					<tr>
						<th>요청사항</th>
						<td colspan="3">000해주세요</td>
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