package com.erp.web.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import com.erp.dao.BusinessDao;
import com.erp.vo.Business;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bss/download")	//파일 다운로드할 수 있는 서블릿
public class BssDownloadServlet extends HttpServlet {

	private static final String saveDirectory = "C:\\Users\\jhta\\eclipse-workspace\\upload";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		
		HttpSession session = req.getSession();
	      Employee emp = (Employee) session.getAttribute("LOGINED_EMP");
	      
	      if(emp == null) {
	         res.sendRedirect("/erp/index?fail=deny");
	         return;
	      }
	      
	    String bssNo = req.getParameter("no");
	    BusinessDao bDao = BusinessDao.getInstance();
		Business business = bDao.getBusinessByNo(bssNo);
		String fileName = business.getFileName();

		res.setContentType("application/octet-stream"); // 바이너리 데이터에 대한 컨텐츠 타입
		// 다운로드 되는 파일이름 응답메세지의 헤더에 설정
		String shortFileName = business.getShortFileName();
		shortFileName = URLEncoder.encode(shortFileName, "utf-8");
		res.setHeader("Content-Disposition", "attachment; fileName=" + shortFileName);
		// 파일 읽어서 브라우저와 연결된 출력스트림으로 내려보냄
		InputStream in = new FileInputStream(new File(saveDirectory, fileName));
		OutputStream out = res.getOutputStream();
		IOUtils.copy(in, out);
		IOUtils.close();
	}
}
