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

<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.css" type="text/css"></link>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#specialStart").datepicker({
		showMonthAfterYear : true
	});
	
	$("#specialEnd").datepicker({
		showMonthAfterYear : true
	});
	
	$("#specialStart").change(function(){
		$( "#specialEnd" ).datepicker( "option", { minDate: $(this).val() } );
	});
});

 /*
$("#specialStart").click(function () {
	$("#specialEnd").datepicker({
		showMonthAfterYear : true
		, minDate : $(this).val()
		
	});
});
*/


function sendOk() {
	 var f = document.specialForm;
	
	var str = $("#subject").val();
	if(!str){
		var s = "제목을 입력해주세요."
		$("#subject").focus();
		$("#subject").parent().next(".s_help").html(s);
		return;
	}
	
	var str = $("#specialStart").val();
	if(!str){
		var s = "축제시작일자를 클릭해주세요."
		$("#specialStart").focus();
		$("#specialStart").parent().parent().find("p:first-child").html(s);
		return;
	}
	
	
	var str = $("#specialEnd").val();
	if(!str){
		var s = "축제마지막일자를 클릭해주세요."
		$("#specialEnd").focus();
		$("#specialEnd").parent().parent().find("p:first-child").html(s);
		return;
	}
	
	var str = $("#content").val();
	if(!str){
		$("#content").focus();
		return;
	}
	
	var str = $("#upload1").val();
	if(!/(\.gif|\.png|\.jpg|\.jpeg)/i.test(str)){
		var s = "이미지 파일을 업로드해주세요."
		$("#upload1").focus();
		$("#upload1").parent().parent().find("p:first-child").html(s);
		return;
	}
	
	var str = $("#upload2").val();
	if(!/(\.gif|\.png|\.jpg|\.jpeg)/i.test(str)){
		var s = "이미지 파일을 업로드해주세요."
		$("#upload2").focus();
		$("#upload2").parent().parent().find("p:first-child").html(s);
		return;
	}
	
	// 이미지파일만 가능하게 유효성검사 
	
	var mode = "${mode}";
	if(mode == "created") {
		f.action = "<%=cp%>/special/s_created_ok.do";
	}
	else if(mode == "update") {
		f.action = "<%=cp%>/special/s_update_ok.do";
	}
	
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
		| 스페셜 ${mode=='update' ? '수정하기' : '글쓰기'}
	</div>
		<form name="specialForm" method="post" enctype="multipart/form-data">
			<div class="b_special">
				<table class="tb_basic_row" style="width:100%">
					<tr>
						<th>제목</th>
						<td colspan="3">
							<p>
								<input type="text" class="boxTF" name="subject" id="subject" placeholder="예 ) 가족 친구와 함께..." value="${dto.specialSubject}"></p>
							<p class = "s_help"></p>
						</td>
					</tr>
					<tr>
						<th>시작일자</th>
						<td>
							<p class = "s_help"></p>
							<p>
								<input type="text" class="boxTF" name="specialStart" id="specialStart" readonly="readonly" placeholder="예) 1992-02-25" value="${dto.specialStart}"></p>
								</td>
						<th>종료일자</th>
						<td>
							<p class = "s_help"></p>
							<p>
								<input type="text" class="boxTF" name="specialEnd" id="specialEnd" readonly="readonly" placeholder="예) 1992-02-25" value="${dto.specialEnd}"></p>					
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3"><textarea class="boxTA" style="height:300px" name="content" id = "content" placeholder="내용을 입력해주세요.">${dto.specialContent}</textarea></td>
					</tr>
					<tr>
						<th>이미지파일_1</th>
						<td colspan="3">
							<p class="s_upload"></p>
							<p><input type="file" name="upload1" id="upload1" class="boxTF" accept="image/*" ></p>
						</td>
					</tr>
					<tr>
						<th>이미지파일_2</th>
						<td colspan="3">
							<p class="s_upload"></p>
							<p><input type="file" name="upload2" id="upload2" class="boxTF" accept="image/*" ></p>
						</td>
					</tr>
					
					
					<c:if test="${mode == 'update'}">
						<input type="hidden" name="specialNum" value="${dto.specialNum}">
						<input type="hidden" name="imageFileName" value="${dto.imageFileName}">
						<input type="hidden" name="userId" value="${dto.userId}">
						<input type="hidden" name="page" value="${page}">
					</c:if>
					<c:if test="${mode == 'created'}">
						<input type="hidden" name="specialNum" value="${dto.specialNum}">
					</c:if>
				</table>
			</div>
			<div class="c_footer">
		<button type="reset" class="btn">다시쓰기</button>
		<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/special/s_calendar.do';">${mode=='update' ? '수정취소' : '등록취소'}</button>
		<button type="button" class="btn" onclick="sendOk();">${mode=='update' ? '수정하기' : '등록하기'}</button>
	</div>
		</form>
	

</div>

<div class="footer">
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>