<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");

	String cp = request.getContextPath();
%>

<head>
<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>

</head>
<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="reserveOk">
	<form name="reserve_ok_Form" action="<%=cp %>/main.do">
		예약이 완료 되었습니다.<br>
		<button type="submit" id="reserveOk_btn" name="reserveOk_btn">메인으로</button>
		
	</form>
</div>
<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>