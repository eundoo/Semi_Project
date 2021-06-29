<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>HTA || 출장승인</title>
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
				<a href="/erp/main">메인가기</a>
				<h4 class="border p-2 bg-light text-center">${LOGINED_EMP.name }님, [<small> ${deptName } </small>] 부서의 승인 대기중인 출장입니다.</h4>
	
				<div class="row mb-3">
					<div class="col-12">
						<div class="border p-2 bg-light">
							<table class="table">
								<colgroup>
									<col width="8%">
									<col width="8%">
									<col width="10%">
									<col width="10%">
									<col width="8%">
									<col width="14%">
									<col width="14%">
									<col width="12%">
									<col width="8%">
									<col width="8%">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center">번호</th>
										<th class="text-center">사원</th>
										<th class="text-center">시작</th>
										<th class="text-center">종료</th>
										<th class="text-center">제목</th>
										<th class="text-center">메모</th>
										<th class="text-center">파일</th>
										<th class="text-center">등록일</th>
										<th class="text-center"></th>
										<th class="text-center"></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${empty biss}">
											<tr>
												<td class="text-center" colspan="9">승인대기중인 출장이 존재하지 않습니다</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach var="bis" items="${biss}" varStatus="loop">
												<tr>
													<td class="text-center">${loop.count }</td>
													<td class="text-center">${bis.empNo }</td>
													<td class="text-center"><fmt:formatDate value="${bis.startDate }" /></td>
													<td class="text-center"><fmt:formatDate value="${bis.endDate }" /></td>
													<td class="text-center">${bis.title }</td>
													<td class="text-center">${bis.memo }</td>
													<td class="text-center"><a href="/erp/bss/download?no=${bis.no }">${bis.shortFileName }</a></td>
													<td class="text-center"><fmt:formatDate value="${bis.createdDate }" /></td>
													<td class="text-center"><a href="javascript:approvalBss(${bis.no })" class="btn btn-primary btn-sm">승인</a></td>
													<td class="text-center"><a href="javascript:rejectBss(${bis.no })" class="btn btn-danger btn-sm">반려</a></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	function approvalBss(bssNo) {
		var confirmValue = confirm("출장을 승인하시겠습니까?")
		if (!confirmValue) {
			return
		}
		location.href = "/erp/bss/approval?no=" + bssNo
				+ "&status=approval"
	}

	function rejectBss(bssNo) {
		var confirmValue = confirm("출장을 반려하시겠습니까?")
		if (!confirmValue) {
			return
		}
		location.href = "/erp/bss/approval?no=" + bssNo + "&status=reject"
	}
</script>
</body>
</html>