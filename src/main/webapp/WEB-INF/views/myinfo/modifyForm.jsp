<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  	<title>사원정보수정</title>
  	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" >
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
				<div>
					<h1 class="pt-3">사원정보 수정</h1>
				</div>
				<div class="row mt-3 mb-5">
					<div class="col-8">
						<form action="/erp/myinfo/modify" id="formID" method="post" class="border p-3 bg-light">
							<div>
								<table class="table text-center">
									<tr>
										<th>사원번호</th><td>${employee.no }</td>
										<th>등록일자</th><td><fmt:formatDate value="${employee.createdDate }"/> </td>
									</tr>
								</table>
								<input type="hidden" name="empNo" value="${employee.no }">
								<input type="hidden" name="createdDate" value="${employee.createdDate }">
							</div>
							<div class="form-group row text-center">
								<div class="col-2">
							   		<label for="emp-name" class="col-form-label">
							   			<strong>사원이름*</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
							    	<input type="text" id="emp-name" name="name" class="form-control "  value="${employee.name }" readonly="readonly">
							  	</div>
								<div class="col-2">
							   		<label for="emp-dept" class="col-form-label">
							   			<strong>소속부서*</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
							  		<select class="form-control" id="emp-dept" name="dept" >
							  			<option value="200" ${employee.deptNo == 200 ? 'selected' : 'disabled' }>경영지원본부</option>
							  			<option value="300" ${employee.deptNo == 300 ? 'selected' : 'disabled' }>생산관리본부</option>
							  			<option value="210" ${employee.deptNo == 210 ? 'selected' : 'disabled' }>인사팀</option>
							  			<option value="220" ${employee.deptNo == 220 ? 'selected' : 'disabled' }>재무팀</option>
							  			<option value="310" ${employee.deptNo == 310 ? 'selected' : 'disabled' }>생산팀</option>
							  			<option value="320" ${employee.deptNo == 320 ? 'selected' : 'disabled' }>영업팀</option>
							  			<option value="330" ${employee.deptNo == 330 ? 'selected' : 'disabled' }>품질팀</option>
							  		</select>
							  	</div>
							</div>
							<div class="form-group row text-center">
								<div class="col-2">
							   		<label for="emp-position" class="col-form-label">
							   			<strong>직급*</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
							  		<select class="form-control" id="emp-position" name="position">
							  			<option value="사원" ${employee.position == "사원" ? 'selected' : 'disabled' }>사원</option>
							  			<option value="대리" ${employee.position == "대리" ? 'selected' : 'disabled' }>대리</option>
							  			<option value="팀장" ${employee.position == "팀장" ? 'selected' : 'disabled' }>팀장</option>
							  			<option value="본부장" ${employee.position == "본부장" ? 'selected' : 'disabled' }>본부장</option>
							  			<option value="대표" ${employee.position == "대표" ? 'selected' : 'disabled' }>대표</option>
							  		</select>
							  	</div>
								<div class="col-2">
							   		<label for="emp-worktype" class="col-form-label">
							   			<strong>고용형태</strong>
							   		</label>
							 	</div>
			
							  	<div class="form-check col-2 mt-2" >
							  		<input class="form-check-input" type="radio" id="worktype-full" name="worktype" value="FULL" ${empInfo.workType eq 'FULL' ? 'checked' : ''} disabled>
									<label class="form-check-label" for="worktype-full">FULL</label>
							  	</div>
							  	<div class="form-check col-2 mt-2"  >
							  		<input class="form-check-input" type="radio" id="worktype-part" name="worktype" value="PART" ${empInfo.workType eq 'FULL' ? '' : 'checked'} disabled>
									<label class="form-check-label" for="worktype-part">PART</label>
							  	</div>
			
							</div>
							<div class="form-group row ">
								<div class="col-2 text-center">
							   		<label for="emp-job" class="col-form-label">
							   			<strong>담당업무</strong>
							   		</label>
							 	</div>
							  	<div class="col-10">
							    	<input type="text" id="emp-job" class="form-control" name="job" value="${employee.job }"> 
							  	</div>
							</div>
							<div class="form-group row">
								<div class="col-2 text-center">
							   		<label for="emp-email" class="col-form-label">
							   			<strong>이메일주소</strong>
							   		</label>
							 	</div>
							  	<div class="col-10">
							    	<input type="text" id="emp-email" name="email" class="form-control" value="${employee.email }">
							  	</div>
							</div>
							<div class="form-group row">
								<div class="col-2 text-center">
							   		<label for="emp-phone" class="col-form-label">
							   			<strong>연락처</strong>
							   		</label>
							 	</div>
							  	<div class="col-10">
							    	<input type="text" id="emp-phone" name="phone" class="form-control" aria-describedby="phoneHelp" value="${employee.phone}">
									<div id="phoneHelp" class="form-text"><p>* 연락처는 '-'를 포함해서 입력해주세요</p></div>
							  	</div>
							</div>
							<div class="form-group row text-center">
								<div class="col-2 text-center">
							   		<label for="emp-address" class="col-form-label">
							   			<strong>주소</strong>
							   		</label>
							 	</div>
							  	<div class="col-10">
							    	<input type="text" id="emp-address" name="address" class="form-control" value="${employee.address }">
							  	</div>
							</div>
							<div class="form-group row text-center">
								<div class="col-2">
							   		<label for="emp-hire-date" class="col-form-label">
							   			<strong>입사일자*</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
							    	<input readonly type="date" id="emp-hire-date" name="hireDate" class="form-control" value="<fmt:formatDate value="${employee.hireDate }" pattern="yyyy-MM-dd"/>">
							  	</div>
							  	
								<div class="col-2">
							   		<label for="emp-retire-date" class="col-form-label">
							   			<strong>퇴사일자</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
			  					  	<c:if test="${empty employee.retireDate }">
								    	<input readonly type="date" id="emp-retire-date" name="retireDate" class="form-control" value="<fmt:formatDate value="${employee.retireDate }" pattern="yyyy-MM-dd"/>">
			  					  	</c:if>
			  					  	<c:if test="${not empty employee.retireDate }">
								    	<input readonly type="date" id="emp-retire-date" name="retireDate" class="form-control" value="<fmt:formatDate value="${employee.retireDate }" pattern="yyyy-MM-dd"/>">
			  					  	</c:if>
							  	</div>
							</div>
							<div class="form-group row text-center">
								<div class="col-2">
							   		<label for="emp-salary" class="col-form-label">
							   			<strong>급여</strong>
							   		</label>
							 	</div>
							  	<div class="col-4">
							    	<input type="text" id="emp-salary" name="salary" class="form-control" value="${employee.salary }" readonly>
									
							  	</div>
							</div>
							<div class="form-group row text-center">
								<div class="col-2">
							   		<label for="emp-name" class="col-form-label">
							   			<strong>계정상태</strong>
							   		</label>
							 	</div>
							 	<div class="form-check form-switch col-2 mt-2">
								 	<input class="form-check-input" type="checkbox" onchange="statusCheck()" id="emp-status" name="status" ${employee.status eq 'ACTIVE' ? 'checked' : '' } >
								</div>	
			
							  	<div class="col-2"></div>
								<div class="col-2">
							   		<label for="emp-name" class="col-form-label" ${employee.admin eq 'Y' ? 'checked' : '' }>
							   			<strong>관리자여부</strong>
							   		</label>
							 	</div>
				 			 	<div class="form-check form-switch col-2 mt-2">
								 	<input class="form-check-input" id="admin-check" onchange="adminCheck()" type="checkbox" name="admin" >
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<button type="button" onclick="submitCheck()" class="btn btn-primary float-right">수정</button>
									<a class="btn btn-dark float-right mr-5" href="/erp/myinfo/info">목록</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">

	// 계정상태 비활성화 시 퇴사일자 활성화
	   function statusCheck() {
      var statusEl = document.getElementById("emp-status");
      
      alert("관리자 권한입니다");
      if(statusEl.checked == true) {
         statusEl.checked = false;
      } else {
         statusEl.checked = true;
      }
   }
   function adminCheck() {
      var adminEl = document.getElementById("admin-check");
      
      alert("관리자 권한입니다");
      if(adminEl.checked == true) {
         adminEl.checked = false;
      } else {
         adminEl.checked = true;
      }
   	}
	function submitCheck() {
		// 비활성화 -> 퇴사일자 입력 유효성 체크
		var statusEl = document.getElementById("emp-status");
		var retireDateEl = document.getElementById("emp-retire-date");
		if(!statusEl.checked) {
			if(!retireDateEl.value) {
				alert("계정 비활성화의 경우 퇴사일자가 반드시 필요합니다.");
				retireDateEl.focus();
				return;
			}
		}
		// not null 유효성 체크
		var nameEl = document.getElementById("emp-name");
		if(!nameEl.value.trim()) {
			alert("사원이름은 필수 입력 사항입니다.");
			nameEl.focus();
			return;
		}
		
		var deptEl = document.getElementById("emp-dept");
		if(!deptEl.value) {
			alert("부서는 필수 입력 사항입니다.");
			deptEl.focus();
			return;			
		}
		
		var positionEl = document.getElementById("emp-position");
		if(!positionEl.value) {
			alert("직급은 필수 입력 사항입니다");
			positionEl.focus();
			return;
		}
		
		var hireDateEl = document.getElementById("emp-hire-date");
		if(!hireDateEl.value) {
			alert("입사일자는 필수 입력 사항입니다.");
			hireDateEl.focus();
			return;
		}
		
		var phoneEl = document.getElementById("emp-phone");
		if(!(phoneEl.value.trim().length == 13)) {
			alert("연락처 형식을 확인하세요.");
			phoneEl.focus();
			return;
		}
		var confirmValue = confirm('수정 하시겠습니까?');
		if(!confirmValue) {
			return;
		} else {
	    	document.getElementById("formID").submit();
		}
		// 모든 폼 입력값이 유효한 입력값임으로 서버로 제출되게 한다.
	}
</script>
</body>
</html>