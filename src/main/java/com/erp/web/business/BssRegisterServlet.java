package com.erp.web.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.erp.dao.BusinessDao;
import com.erp.dao.EmployeeDao;
import com.erp.util.CommonUtils;
import com.erp.vo.Business;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/bss/register")		//출장 등록폼으로 이어지는  서블릿
@MultipartConfig
public class BssRegisterServlet extends HttpServlet{
	
	private static final String saveDirectory = "C:\\workspace\\eunhye\\java_web\\erp\\upload";
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("LOGINED_EMP");
		String empNo = emp.getNo();
		
		EmployeeDao empDao = EmployeeDao.getInstance();
		Employee finedEmp = empDao.getEmployeeByNo(empNo);
		
		// 수정할 사원정보 요청객체에 저장
		req.setAttribute("employee", finedEmp);
		
		BusinessDao bDao = BusinessDao.getInstance();
		Business bss = bDao.getBusinessByNo(empNo);
		
		req.setAttribute("business", bss);
		
		req.getRequestDispatcher("/WEB-INF/views/bss/bssRegisterForm.jsp").forward(req, res);	
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String title = req.getParameter("bssTitle");
		String memo = req.getParameter("bssMemo");
		Date startDate = CommonUtils.stringToDate(req.getParameter("bssStartDate"));
		Date endDate = CommonUtils.stringToDate(req.getParameter("bssEndDate"));
		
		Part part = req.getPart("bssFileName");		
		
		String fileName =  System.currentTimeMillis() + part.getSubmittedFileName();
		
		
		OutputStream out = new FileOutputStream(new File(saveDirectory,fileName));
		InputStream in = part.getInputStream();
		IOUtils.copy(in, out);
		out.close();
		
		String empNo = req.getParameter("empNo");		
		String managerNo = req.getParameter("managerNo");		
		
		Business business = new Business();
		business.setTitle(title);
		business.setMemo(memo);
		business.setStartDate(startDate);
		business.setEndDate(endDate);
		business.setFileName(fileName);
		business.setEmpNo(empNo);
		business.setManagerNo(managerNo);
		
		if(business.getEmpNo().equals(business.getManagerNo())) {
		business.setManagerNo(empNo);
		}
		
		BusinessDao businessDao = BusinessDao.getInstance();
		businessDao.insertBusiness(business);
		
		res.sendRedirect("/erp/bss/list");
	}

}
