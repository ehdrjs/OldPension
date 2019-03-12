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
	<style type="text/css">
		.txt_overflow{display:block;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}
	</style>
</head>
<body>

<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="special">
	<div class="h_special">
		| 스페셜 <span style="font-size: 15px;"> ${dataCount}개 (${page}/${total_page}페이지)  </span>
	</div>
	
	<div class="b_special">
		<table class="tb_basic" style="width:100%;">
			<colgroup>
				<col style="width:80px">
				<col>
				<col style="width:220px">
				<col style="width:130px">
				<col style="width:80px">
			</colgroup>
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>축제일자</th>
					<th>등록일자</th>
					<th>조회수</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="dto" items="${list}">	
					<tr class="content">
						<td>${dto.listNum}</td>
						<td class="txt_overflow" style=";text-align:left !important;"><a href="${articleUrl}&specialNum=${dto.specialNum}">${dto.specialSubject}</a></td>
						<td>${dto.specialStart} ~ ${dto.specialEnd}</td>
						<td>${dto.specialDate}</td>
						<td>${dto.specialCount}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="paging">
		<c:if test="${dataCount==0}">
			등록된 게시물이 없습니다.
		</c:if>
		
		<c:if test="${dataCount!= 0}">
			${paging}			
		</c:if>
	</div>
	<div class="f_special">	
	<c:if test="${sessionScope.member.userRole == 'admin' }">
		<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/special/s_created.do'">등록하기</button>
	</c:if>
	</div>
	
</div> 



<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>