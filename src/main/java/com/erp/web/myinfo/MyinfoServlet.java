package com.erp.web.myinfo;

import java.io.IOException;

import com.erp.dao.EmployeeDao;

import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myinfo/info")
public class MyinfoServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 수정할 사원정보 DB에서 조회
		HttpSession session =req.getSession();
		Employee emp = (Employee)session.getAttribute("LOGINED_EMP");
		String empNo = emp.getNo();
		
		EmployeeDao empDao = EmployeeDao.getInstance();
		Employee finedEmp = empDao.getEmployeeByNo(empNo);
		
		// 수정할 사원정보 요청객체에 저장
		req.setAttribute("empInfo", finedEmp);
		empDao.updateEmployee(finedEmp);
		
		req.getRequestDispatcher("/WEB-INF/views/myinfo/mypage.jsp").forward(req, resp);
	}
}
