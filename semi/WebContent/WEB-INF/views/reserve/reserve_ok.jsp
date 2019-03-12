<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");

	int room = Integer.parseInt(request.getParameter("room"));
	int bbc = Integer.parseInt(request.getParameter("barbecue1"));
	int result = room + bbc;
	
%>

<div>
	객실: <%=bbc %> 원
</div>