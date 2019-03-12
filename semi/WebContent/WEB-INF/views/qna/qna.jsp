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
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	function qnaSearchList() {
		var f = document.qnaSearchForm;
		f.submit();
	}
	
	function check() {
		var uid="${sessionScope.member.userId}";
		if(! uid) {
			alert("로그인후에 이용가능한 페이지 입니다.");
			location.href="<%=cp%>/qna/qna.do";
			return;
		}
		
			location.href="<%=cp%>/qna/created.do";
	}
</script>

</head>
<body>
	<div class="qna">
		<jsp:include page="/WEB-INF/views/layout/import.jsp"></jsp:include>
		<div class="header">
			<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		</div>
		<div class="container">
			<div>
				<h5 style="font-weight: bold">
					<span>|&nbsp;</span>고객문의 게시판
				</h5>
			</div>
			<div class="row">
				<div class="col-md-12">
					<ul>
						<li style="display: inline">총${dataCount}글&nbsp;<span>|&nbsp;${page}/${total_page}</span>페이지
						</li>
						<li style="float: right; list-style: none">
							<button type="button" class="btn btn-info btn-sm"
								onclick="check();">글작성</button>
						</li>
					</ul>
				</div>
			</div>
			<div id="accordion" role="tablist">
				<div class="card">
					<div class="card-header" role="tab" id="headingOne">
						<h6 class="mb-0">
							<a data-toggle="collapse" href="#collapseOne"
								aria-expanded="true" aria-controls="collapseOne" style="text-decoration: none;"> #1 입/퇴실 안내
							</a>
						</h6>
					</div>

					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body">입/퇴실안내<br> 입실 : 15:00 <br>퇴실 : 11:00 <br>
							<br>픽업안내<br>
							마트픽업 픽업 가능-청평터미널 인근 365마트, 레몬마트 1인기준 15,000원 이상 물품 구매시 마트픽업가능 /<br>
							펜션문의 여행시 필요한 물품을 다른곳에서 구매하지 않고 해당마트에서 모두 구매시 픽업 가능 <br>365마트
							031-585-8051, 레몬마트 031-584-6684 픽업 시간: ~17:00</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" role="tab" id="headingTwo">
						<h6 class="mb-0">
							<a class="collapsed" data-toggle="collapse" href="#collapseTwo"
								aria-expanded="false" aria-controls="collapseTwo" style="text-decoration: none;">#2 취사 안내 </a>
						</h6>
					</div>
					<div id="collapseTwo" class="collapse" role="tabpanel"
						aria-labelledby="headingTwo" data-parent="#accordion">
						<div class="card-body">
							숯+그릴 이용: 2인 기준 10,000원 / 3~4인 기준 20,000원 / 5~6인기준 30,000원 <br>동절기,
							우천시 사용 가능한 개별테라스바베큐장, 공동 실내 바베큐장 구비 <br>7인 이상 이용시 별도문의 현장결제
							및 추가비용 펜션 문의
						</div>
					</div>
				</div>
				</div>
			<div class="row">
				<div class="col-md-12">
					<ul class="list-group list-group-horizontal">
						<li class="list-group-item" style="width: 15%">번호</li>
						<li class="list-group-item" style="width: 40%">제목</li>
						<li class="list-group-item" style="width: 20%">작성ID</li>
						<li class="list-group-item" style="width: 15%">작성일</li>
						<li class="list-group-item" style="width: 10%">조회수</li>
					</ul>
				</div>
			</div>

			<c:forEach var="dto" items="${list}">
				<div class="row">
					<div class="col-md-12">
						<ul class="list-group list-group-horizontal">
							<li class="list-group-item" style="width: 15%">${dto.listNum}</li>
							<li class="list-group-item" style="width: 40%; text-align: left;">
							<c:forEach var="n" begin="1" end="${dto.depth}">%nbsp;%nbsp;</c:forEach>
							<c:if test="${dto.depth!=0}">답변</c:if>
							<a href="${articleUrl}&qnaNum=${dto.num}">${dto.subject}</a></li>
							<li class="list-group-item" style="width: 20%">${dto.userId}</li>
							<li class="list-group-item" style="width: 15%">${dto.created}</li>
							<li class="list-group-item" style="width: 10%">${dto.count}</li>
						</ul>
					</div>
				</div>
			</c:forEach>

			<div class="">
				<nav aria-label="Page navigation example">
					<ul class="pagination" style="display: list-item">
						<li class="page-item"><c:if test="${dataCount==0}">등록된 게시물이 없습니다.</c:if></li>
						<li class="page-item"><c:if test="${dataCount!=0}">${paging}</c:if></li>
					</ul>
				</nav>
			</div>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-9">
					<form class="form-group" name="qnaSearchForm"
						action="<%=cp%>/qna/qna.do" method="post">
						<select class="form-control col-md-2" name="qnaSearchKey"
							style="float: left; margin-right: 5px" class="qnaSelectField">
							<option value="qnaSubject">제목</option>
							<option value="qnaContent">내용</option>
							<option value="userId">작성ID</option>
							<option value="qnaDate">등록일</option>
						</select> <input type="text" class="form-control col-md-4"
							style="float: left; margin-right: 5px" name="qnaSearchValue">
						<button type="button" class="btn btn-info btn-sm"
							style="margin-top: 3px" onclick="qnaSearchList()">검색</button>
					</form>
				</div>
			</div>
		</div>

		<div class="footer">
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>