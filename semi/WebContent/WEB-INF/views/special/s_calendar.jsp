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

<div class="special">
	<div class="h_special">
		| 스페셜
	</div>
	
	<div class="b_special">
		<table class="tb">
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>축제일자</th>
				<th>등록일자</th>
				<th>조회수</th>
			</tr>
			<tr class="content">
				<td>1</td>
				<td>축제다</td>
				<td>2000-02-20 ~ 2000-02-25</td>
				<td>2000-02-02</td>
				<td>11</td>
			</tr>
		</table>
	</div>
	
	<div class="paging">
		<ul>
			<li>1</li>
			<li>2</li>
			<li>3</li>
			<li>4</li>
			<li>5</li>
		</ul>
	</div>
	<div class="f_special">	
		<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/special/s_created.do'">등록하기</button>
	</div>
	
</div> 



<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>