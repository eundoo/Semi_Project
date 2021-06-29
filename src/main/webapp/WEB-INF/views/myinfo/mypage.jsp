<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  	<title>HTA || 내정보</title>
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
				<div class="row mb-3 mt-3">
					<div class="col-12">
						<h1>마이페이지</h1>
						<h3 class="mt-3">${empInfo.name }님의 정보입니다.</h3>
					</div>
				</div>

				<div class="row mt-3 mb-1">
					<div class="col-12">
						<c:choose>
							<c:when test="${empInfo.status eq 'ACTIVE' }">
								<p><span class="badge bg-primary">active</span> 재직중인 직원입니다.</p>
							</c:when>
							<c:otherwise>
								<p><span class="badge bg-danger">inactive</span> 퇴사처리된 직원입니다.</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<table class="table">
							<tr>
								<th class="table-info text-center">사원번호</th><td>${empInfo.no }</td>
								<th class="table-info text-center">소속부서</th><td>${empInfo.deptNo }</td>
							</tr>
							<tr>
								<th class="table-info text-center">사원이름</th><td>${empInfo.name }</td>
								<th class="table-info text-center">사원직급</th><td>${empInfo.position }</td>
							</tr>
							<tr>
								<th class="table-info text-center">담당업무</th><td>${empInfo.job }</td>
								<th class="table-info text-center">고용형태</th><td>${empInfo.workType }</td>
							</tr>
							<tr>
								<th class="table-info text-center">이 메 일</th><td>${empInfo.email }</td>
								<th class="table-info text-center">연 락 처</th><td>${empInfo.phone }</td>
							</tr>
							<tr>
								<th class="table-info text-center">입사일자</th><td><fmt:formatDate value="${empInfo.hireDate }" pattern="yyyy-MM-dd"/> </td>
								<th class="table-info text-center">퇴사일자</th><td><fmt:formatDate value="${empInfo.retireDate }" pattern="yyyy-MM-dd"/></td>
							</tr>
							<tr>
								<th class="table-info text-center">사원주소</th><td>${empInfo.address }</td>
								<th class="table-info text-center">사원연봉</th><td><fmt:formatNumber value="${empInfo.salary }"/> 원</td>
							</tr>
							<tr>
								<th class="table-info text-center">등록일자</th><td><fmt:formatDate value="${empInfo.createdDate }" pattern="yyyy-MM-dd"/> </td>
								<th class="table-info text-center">관리자여부</th><td>${empInfo.admin }</td>
							</tr>
						</table>
						<div class="col-12">
							<a href="modify" class="btn btn-primary float-right">수정</a>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</div>
</body>
</html>