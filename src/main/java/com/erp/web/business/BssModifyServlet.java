package com.erp.web.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.erp.dao.BusinessDao;
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

@WebServlet("/bss/modify")	
@MultipartConfig //수정폼 서블릿
public class BssModifyServlet extends HttpServlet{

	private static final String saveDirectory = "C:\\Users\\jhta\\eclipse-workspace\\upload";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("LOGINED_EMP");
		 
		if(emp == null) {
			res.sendRedirect("/erp/index?fail=deny");
			return;
		}
		
		String no = req.getParameter("no");
		
		BusinessDao bDao = BusinessDao.getInstance();
		Business business = bDao.getBusinessByNo(no);
		req.setAttribute("business", business);
		
		//직원리스트에서 '등록을'클릭하면 연결되는 jsp
		req.getRequestDispatcher("/WEB-INF/views/bss/bssModifiedForm.jsp").forward(req, res);
		
		
		/*
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("LOGINED_EMP");
		
		String bssNo = req.getParameter("no");	
		
		BusinessDao bDao = BusinessDao.getInstance();
		Business bss = bDao.getBusinessByNo(bssNo);

		req.setAttribute("business", bss);
		
		//직원리스트에서 '수정을'클릭하면 연결되는 jsp
		req.getRequestDispatcher("/WEB-INF/views/bss/bssModifiedForm.jsp").forward(req, res);
		*/
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String no = req.getParameter("bssNo");
		String title = req.getParameter("bssTitle");
		String memo = req.getParameter("bssMemo");
		Date startDate = CommonUtils.stringToDate(req.getParameter("bssStartDate"));
		Date endDate = CommonUtils.stringToDate(req.getParameter("bssEndDate"));
		Part part = req.getPart("bssFileName");		
		
		String fileName =  System.currentTimeMillis() + part.getSubmittedFileName();
		String empNo = req.getParameter("empNo");	
		FileOutputStream out = new FileOutputStream(new File(saveDirectory,fileName));
		InputStream in = part.getInputStream();
		IOUtils.copy(in, out);
		out.close();
		
		BusinessDao businessDao = BusinessDao.getInstance();
		Business business = businessDao.getBusinessByNo(no);
		business.setTitle(title);
		business.setMemo(memo);
		
		business.setFileName(fileName);
		
		 boolean isInserted = false;
			List<Business> savedBusiness = businessDao.getAllListByEmpNo(empNo);
			if(savedBusiness.isEmpty()) {
				business.setStartDate(startDate);
				business.setEndDate(endDate);
				
				businessDao.updateBusiness(business);
				req.setAttribute("business", business);
				isInserted = true;
				
			} else {
				boolean canInsert = true;
				
				for (Business busin : savedBusiness) {				
					if(!((startDate.compareTo(busin.getStartDate()) == 1 && endDate.compareTo(busin.getEndDate()) == 1) 
						|| (startDate.compareTo(busin.getStartDate()) == -1 && endDate.compareTo(busin.getEndDate()) == -1))) {
						canInsert = false;
						break;
					}
				}
				
				if (canInsert) {
					business.setStartDate(startDate);
					business.setEndDate(endDate);
					
					businessDao.updateBusiness(business);
					req.setAttribute("business", business);
					
					isInserted = true;
				}
				System.err.println("업데이트 되라~~~~~~~~~~~~~~~~");
			
			}
			
			if (isInserted) {
				res.sendRedirect("/erp/bss/list?");
				
			} else {
				res.sendRedirect("/erp/bss/modify?success=fail");
			}
		
		
		
		
				


	}
}
