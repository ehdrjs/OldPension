<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
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
	
<body>
	<div id="container">
		<ul id="contain">
			<li>
				<img src="<%=cp %>/img/1.jpg" />
			</li>
			<li>
				<img src="<%=cp %>/img/2.jpg" />
			</li>
			<li>
				<img src="<%=cp %>/img/3.jpg" />
			</li>

		</ul>
	</div>


	<script type='text/javascript'>
		var x = document.getElementById("container").offsetWidth;
		var contain = document.getElementById("contain");
		var containArray = contain.getElementsByTagName("li");
		var containMax = containArray.length - 1;
		var curContainNo = 0;
		
		
		for (i = 0; i <= containMax; i++) {
			if (i == curContainNo) containArray[i].style.left = 0;
			else containArray[i].style.left = -x + "px";
		}
	 
		contain.addEventListener('click', function () {
			changeContain();
		}, false);
		
		contain.addEventListener('resize', function () {
 			x = document.getElementById("container").offsetWidth;
 			containArray = contain.getElementsByTagName("li");
			containMax = containArray.length - 1;
			changeContain();

		}, false); 
	 	
		var aniStart = false;
		var next = 1;
		var changeContain = function(){
			if (aniStart === true) return;
			next = curContainNo + 1;
			if (next > containMax) next = 0;
			aniStart = true;
			sliding();
		}
	 
		function sliding() {
			var curX = parseInt(containArray[curContainNo].style.left, 10);
			var nextX = parseInt(containArray[next].style.left, 10);
			var newCurX = curX + 10;
			var newNextX = nextX + 10;
			if (newCurX >= x) {
				containArray[curContainNo].style.left = -x + "px";
				containArray[next].style.left = 0;
				curContainNo = curContainNo + 1;
				if (curContainNo > containMax) curContainNo = 0;
				aniStart = false;
				return;
			}
			containArray[curContainNo].style.left = newCurX + "px";
			containArray[next].style.left = newNextX + "px";
			setTimeout(function () {
				sliding();
			}, 20);
		}
		setInterval(changeContain,2000);
		
	</script>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>