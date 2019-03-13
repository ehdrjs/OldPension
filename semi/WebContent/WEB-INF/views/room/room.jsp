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
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<style type="text/css">
	.review_iframelayout {
		height : 700px;
		border : none;
		float : left;
	}
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	<div class="review_container" style="text-align:center;">
		<div style="width:1360px; display:inline-block;">
			<iframe class="review_iframelayout" src="<%=cp%>/room/list.do" style="width:800px"></iframe>
			<div class="review_mid_line"></div>
			<iframe class="review_iframelayout" src="<%=cp%>/review/review.do" style="width:445px"></iframe>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</body>
</html>