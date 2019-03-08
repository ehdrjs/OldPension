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
	
	
function sendOk() {
	var f = document.specialForm;
	
	var str = f.subject.value;
	if(!str){
		f.subject.focus();
		return;
	}
	
	var str = f.specialStart.value;
	if(!str){
		f.specialStart.focus();
		return;
	}
	
	
	var str = f.specialEnd.value;
	if(!str){
		f.specialEnd.focus();
		return;
	}
	
	var str = f.content.value;
	if(!str){
		f.content.focus();
		return;
	}
	
	var str = f.upload.value;
	if(!/(\.gif|\.png|\.jpg|\.jpeg)/i.test(str)){
		f.upload.focus();
		return;
	}
	
	f.action = "<%=cp%>/special/s_created_ok.do";
	f.submit();
	
}


</script>
</head>

<body>
<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="special">
	<div class="h_special">
		| 스페셜 등록
	</div>
		<form name="specialForm" method="post" enctype="multipart/form-data">
			<div class="b_special">
				<table class="tb_basic_row" style="width:100%">
					<tr>
						<th>제목</th>
						<td colspan="3"><input type="text" class="boxTF" name="subject" placeholder="제목을 입력해주세요."/></td>
					</tr>
					<tr>
						<th>시작일자</th>
						<td><input type="text" class="boxTF" name="specialStart" placeholder="예) 1992-02-25"/></td>
						<th>종료일자</th>
						<td><input type="text" class="boxTF" name="specialEnd" placeholder="예) 1992-02-25"/></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3"><textarea class="boxTA" style="height:300px" name="content" placeholder="내용을 입력해주세요."></textarea></td>
					</tr>
					<tr>
						<th>이미지파일</th>
						<td colspan="3"><input type="file" name="upload" class="boxTF" accept="image/*"/>100</td>
					</tr>
				</table>
			</div>
			<div class="c_footer">
		<button type="reset" class="btn">다시쓰기</button>
		<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/special/s_calendar.do';">등록취소</button>
		<button type="button" class="btn" onclick="sendOk();">등록하기</button>
	</div>
		</form>
	

</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>