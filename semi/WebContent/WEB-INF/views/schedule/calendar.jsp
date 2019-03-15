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
</head>
<body>

<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="contents">
	<div class="paging">
		<div class="paging2">	
			<ul>
			<li><a href = "calendar.do?year=${year}&month=${month-1}">◀</a></li>
			<li>${year}년 ${month}월</li>
			<li><a href = "calendar.do?year=${year}&month=${month+1}">▶</a></li>
			</ul>
		</div>
	</div>	
	
	<div class="cal">
		<table class="tb_basic w100p">
			<thead>
				<tr class="week">
					<th>일</th>
					<th>월</th>
					<th>화</th>
					<th>수</th>
					<th>목</th>
					<th>금</th>
					<th>토</th>
				</tr>
			</thead>
			
			<tbody>
				<c:set var="col" value="0"/>
			<tr>
			 	<c:forEach var="a" begin="1" end="${week-1}" step="1" >
					<c:set var="col" value="${col+=1}"/>
					<td>&nbsp;</td>
				</c:forEach> 
				
				<c:forEach var="b" begin="1" end="${e}" step="1">
					<c:set var="col" value="${col+=1}"/>
					<td>${b}</td>
					<c:if test="${col % 7 ==0  && b != e}">
						<c:out value="</tr><tr>" escapeXml="false"/>
						<c:set var="col" value="0"/>						
					</c:if>							
				</c:forEach>
			
			
			<c:forEach var="a" begin="${col}" end="7" step="1">
				<td>&nbsp;</td>
			</c:forEach>
			</tr>
			</tbody>
		</table>
	</div>
</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>