package com.erp.web.business;

import java.io.IOException;
import java.util.List;

import com.erp.dao.BusinessDao;
import com.erp.vo.Business;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bss/list") 	//직원이 볼 수 있는 리스트 (자기가 등록한것만 볼 수 있음)
public class BssListServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Employee emp = (Employee)session.getAttribute("LOGINED_EMP");
		
		BusinessDao businessDao = BusinessDao.getInstance();
		List<Business> allBusiness = businessDao.getAllListByEmpNo(emp.getNo());
		req.setAttribute("business", allBusiness);

		// 여기 아래링크로 들어가는게 이제 내부객체
		req.getRequestDispatcher("/WEB-INF/views/bss/businessList.jsp").forward(req, res);
	}
}
