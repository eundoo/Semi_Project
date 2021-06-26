package com.erp.web.business;

import java.io.IOException;

import com.erp.dao.BusinessDao;
import com.erp.vo.Business;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bss/status")		//매니저에게 '승인'처리된 출장리스트를 자신이 '삭제', 또는'완료'가 가능하다.
public class BssStatusServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String bssNo= req.getParameter("no");
		BusinessDao bDao = BusinessDao.getInstance();
		Business bss = bDao.getBusinessByNo(bssNo);
		
		String status = req.getParameter("status");
		bss.setStatus(status);
		
		bDao.updateBusiness(bss);
		res.sendRedirect("/erp/bss/list");
	}

}
