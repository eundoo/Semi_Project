package com.erp.web.bss.mgr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erp.dao.BusinessDao;
import com.erp.dao.DepartmentDao;
import com.erp.vo.Business;
import com.erp.vo.Department;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bss/applist")	
public class BssAppListServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 로그인여부확인
		HttpSession session = req.getSession();
		Employee emp = (Employee)session.getAttribute("LOGINED_EMP");
		if(emp == null) {
			res.sendRedirect("/erp/index?faill=deny");
			return;
		}
		// 매니저여부확인
		
		// 로그인한 매니저의 부서번호조회
		int deptNo = emp.getDeptNo();
		// 매니저가 속한 부서에있는 출장신청들을 조회
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("deptNo", deptNo);
		condition.put("status", "신청");
		BusinessDao bDao = BusinessDao.getInstance();
		List<Business> biss = bDao.getAllListByDeptNo(condition);
		// 매니저가 속한 부서의 이름을 알아내서 요청객체에 저장(위에 표시하려고)
		DepartmentDao dDao = DepartmentDao.getInstance();
		Department dept = dDao.getDepartmentByNo(deptNo);
		String deptName = dept.getName();
		//요청객체에 부서이름과 business 정보들 보냄
		
		req.setAttribute("deptName", deptName);
		req.setAttribute("biss", biss);
		req.getRequestDispatcher("/WEB-INF/views/bss/bssApprovalList.jsp").forward(req, res);
		
	}
}
