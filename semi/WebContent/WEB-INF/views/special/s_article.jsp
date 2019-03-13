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
<script type="text/javascript">

	function deleteSpecial(specialNum) {
		if(confirm("게시물을 삭제하시겠습니까?")){
			var url = "<%=cp%>/special/s_delete.do?specialNum="+specialNum+"&page=${page}";
			location.href = url;
		}
	}

</script>
</head>

<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="special">
	<div class="h_special">
		| 스페셜 글
	</div>
	
	<table class="s_tb">
		<tr>
			<td colspan="4"> ${dto.specialSubject} </td>
		</tr>
		<tr>
			<td style="text-align: right;">작성자 ${dto.userId}
			</td>
			<td style="float: right; padding-right: 30px;"> 조회수 ${dto.specialCount}
			</td>
			<td style="float: right; padding-right: 30px;"> 등록일자  ${dto.specialDate }
			</td>
			<td style="float: right; padding-right: 30px;"> 축제일자 : ${dto.specialStart} ~ ${dto.specialEnd}
			</td>
		</tr>
	
		<c:forEach var = "vo" items="${listImage}">
		<tr>
			<td colspan="4" style="text-align: center;"><img src="<%=cp%>/uploads/photo/${vo.imageFileName}"></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="4">${dto.specialContent }</td>
		</tr>
	</table>
	
	<div class="s_btn_c">	
			<button type="button" class="s_btn" onclick="javascript:location.href='<%=cp%>/special/s_calendar.do?page=${page}'">리스트</button>
		<c:if test="${sessionScope.member.userRole == 'admin'}">
			<button type="button" class="s_btn" onclick="javascript:location.href='<%=cp%>/special/s_update.do?specialNum=${dto.specialNum}&page=${page}'">수정</button>
			<button type="button" class="s_btn" onclick="deleteSpecial(${dto.specialNum});">삭제</button>
		</c:if>
	</div>
</div>





<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>