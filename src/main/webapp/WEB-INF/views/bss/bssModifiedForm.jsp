<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>HTA || 출장변경</title>
 	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@include file = "/WEB-INF/views/commons/header.jsp"%>
		</div>	
		<div class="row">
			<div class="col-3">
				<%@include file ="/WEB-INF/views/commons/sidebar.jsp"%>
			</div>
			<div class="col-9">
				<main>
					<c:if test="${param.success eq 'fail' }">
						<script type="text/javascript">
							alert("기간이 중복되는 일정은 등록할 수 없습니다");
						</script>
					</c:if>
					<div class="row">
						<div calss="col-12">
							<form id="business-form" method="post" action="/erp/bss/modify" enctype="multipart/form-data" onsubmit="return formSubmit()">
							<input type="hidden" name="bssNo" value="${business.no }" />
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">출장 제목</label>
					   			<input type="text" class="form-control" id="bss-title" name="bssTitle" placeholder="출장 제목" value="${business.title }">
					 		</div>
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">출장 메모</label>
					   			<input type="text" class="form-control" id="bss-memo" name="bssMemo" placeholder="출장 메모" value="${business.memo }">
					 		</div>		 		
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">시작일</label>
					   			<input type="date" class="form-control" id="bss-start-date" name="bssStartDate" value='<fmt:formatDate value="${business.startDate }" pattern="yyyy-MM-dd"/>'>
					 		</div>
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">마감일</label>
					   			<input type="date" class="form-control" id="bss-end-date" name="bssEndDate"  value='<fmt:formatDate value="${business.endDate }" pattern="yyyy-MM-dd"/>'>
					 		</div>
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">파일이름</label>
					   			<input type="file" class="form-control" id="bss-file-name" name="bssFileName" value="${business.fileName }">
					 		</div>
					 		<div class="form-group">
					   			<label for="formGroupExampleInput">사원 번호</label>
					   			<input type="text" class="form-control" id="emp-no" name="empNo" value="${business.empNo }" readonly>
					 		</div>	 				
							<div class="mb-1 text-end">
								<button type="button" class="btn btn-outline-primary" onclick="clickCancel()">취소</button>
								<button type="submit" class="btn btn-primary">수정</button>
							</div>
							</form>
						</div>
					</div>
						
				</main>
			</div>
		</div>
	</div>
<script type="text/javascript"> 
	function formSubmit() {
		var bssTitle = document.getElementById("bss-title")
		if(!bssTitle.value.trim()) {
			alert("출장 제목은 필수 입력값 입니다.")
			bssTitle.focus()
			return false;
		}
		
		var confirmValue = confirm("수정 하시겠습니까?")
		if(!confirmValue) {
			return false;
		}
		
		return true;
	}
	
	
	function clickCancel() {
		var confirmValue = confirm("취소 하시겠습니까?")
		if(!confirmValue){
			return;
		}
		location.href = "list"
	}
	
</script>
</body>
</html>