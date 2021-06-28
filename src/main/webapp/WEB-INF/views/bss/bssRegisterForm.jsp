<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>HTA회사 ERP프로그램</title>
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
					<form id="business-form" method="post" action="/erp/bss/register" enctype="multipart/form-data" onsubmit="return formSubmit()">
				 		<div class="form-group">
				 		<input type="hidden" name="bssNo" value="${business.no }" />
				 		<input type="hidden" id="pre-start-date" value="${business.startDate}" />
				 		<input type="hidden" id="pre-end-date" value="${business.endDate}" />
				   			<label for="formGroupExampleInput">출장 제목</label>
				   			<input type="text" class="form-control" id="bss-title" name="bssTitle" placeholder="출장제목">
				 		</div>
				 		<div class="form-group">
				   			<label for="formGroupExampleInput">출장 메모</label>
				   			<input type="text" class="form-control" id="bss-memo" name="bssMemo" placeholder="출장 메모">
				 		</div>		 		
				 		<div class="form-group">
				   			<label for="formGroupExampleInput">시작일</label>
				   			<input type="date" class="form-control" id="bss-start-date" name="bssStartDate" placeholder="시작일">
				 		</div>
				 		<div class="form-group">
				   			<label for="formGroupExampleInput">종료일</label>
				   			<input type="date" class="form-control" id="bss-end-date" name="bssEndDate" placeholder="마감일">
				 		</div>
				 		<div class="form-group">	
				   			<label class="d-block" for="formGroupExampleInput">파일이름</label>
				   			<input type="file" class="form-control" id="bss-file-name" name="bssFileName">
				 		</div>
				 		<div class="form-group">
				   			<input type="text" class="form-control" id="emp-no" name="empNo" value="${employee.no }" placeholder="사원번호" readonly>
				 		</div>	
				 				
						<div class="mb-1 text-end">
							<a href="list" class="btn btn-primary">취소</a>
							<button type="button" class="btn btn-primary" onclick="formSubmit()">등록</button>
						</div>
					</form>
				</main>
			</div>
		</div>
	</div>
<script type="text/javascript"> 
function formSubmit() {
	var bssTitle = document.getElementById("bss-title");
	if(!bssTitle.value.trim()) {
		alert("출장 제목은 필수 입력값 입니다.")
		bssTitle.focus();
		return false;
	}
		
	var bssStartDate = document.getElementById("bss-start-date");
	if(!bssStartDate.value.trim()) {
		alert("시작일은 필수 입력값 입니다.")
		bssStartDate.focus();
		return false;
	}
	
	var bssEndDate = document.getElementById("bss-end-date");
	if(!bssEndDate.value.trim()) {
		alert("종료일은 필수 입력값 입니다.")
		bssEndDate.focus();
		return false;
	}
	
	if(bssEndDate.value.replace(/\-/g,'') - bssStartDate.value.replace(/\-/g,'') < 0)
	{
		alert("종료일자가 시작일자보다 적을 수 없습니다.");
		return false;
	}
	//여기 하고 있었음!!!
	var preStartDate = document.getElementById("pre-start-date");
	var bssStartDate = document.getElementById("bss-start-date");
	if(preStartDate.valueOf() === bssStartDate.valueOf()) {
		alart("날짜중복이랑께");
		return false;
	}
	
	var confirmValue = confirm("등록 하시겠습니까?")
	if(!confirmValue) {
		return false
	} 
	document.getElementById("business-form").submit()
}

</script>
</body>
</html>