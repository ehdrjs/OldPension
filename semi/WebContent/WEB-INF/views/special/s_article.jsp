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
		| 스페셜 글
	</div>

</div>





<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>