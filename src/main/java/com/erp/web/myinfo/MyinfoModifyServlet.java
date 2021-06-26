package com.erp.web.myinfo;

import java.io.IOException;
import java.util.Date;

import com.erp.dao.EmployeeDao;
import com.erp.util.CommonUtils;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myinfo/modify")
public class MyinfoModifyServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Employee emp = (Employee)session.getAttribute("LOGINED_EMP");
		String empNo = emp.getNo();
		
		EmployeeDao eDao = EmployeeDao.getInstance();
		Employee empInfo = eDao.getEmployeeByNo(empNo);
		
		req.setAttribute("employee", empInfo);
		
		req.getRequestDispatcher("/WEB-INF/views/myinfo/modifyForm.jsp").forward(req, res);
		//직원리스트에서 '수정'클릭하면 연결되는 jsp
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// post방식으로 저장된 정보 획득
		System.out.println("---------------------checkpoint0------------");
		String no = req.getParameter("empNo");
		System.out.println("---------------------checkpoint0--" + no);
		String name = req.getParameter("name");
		System.out.println("------------------check : " + name);
		int deptNo = CommonUtils.stringToInt(req.getParameter("dept"));
		String position = req.getParameter("position");
		String workType = req.getParameter("worktype");
		String job = req.getParameter("job");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String hireDate = req.getParameter("hireDate");
		String retireDate = req.getParameter("retireDate");
		System.out.println("---------------------checkpoint1------------");
		String test = req.getParameter("salary");
		System.out.println("---------------------checkpoint1-1------------" + test);
		Long salary = Long.parseLong(test.trim());
		System.out.println("---------------------checkpoint2");
		String status = req.getParameter("status");
		String admin = req.getParameter("admin");
		
		Employee emp = new Employee();
		
		emp.setNo(no);
		emp.setDeptNo(deptNo);
		emp.setName(name);
		emp.setPosition(position);
		emp.setWorkType(workType);
		emp.setJob(job);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setAddress(address);
		emp.setHireDate(CommonUtils.stringToDate(hireDate));
		emp.setRetireDate(CommonUtils.stringToDate(retireDate));
		if(salary != null) emp.setSalary(salary);
		
		if("on".equals(status)) {
			status = "ACTIVE";
		} else {
			status = "INACTIVE";
		}
		emp.setStatus(status);
		
		if("on".equals(admin)) {
			admin = "Y";
		} else {
			admin = "N";
		}
		emp.setAdmin(admin);
		
		// DB에 수정된 정보를 업데이트
		EmployeeDao empDao = EmployeeDao.getInstance();
		empDao.updateEmployee(emp);
		
		resp.sendRedirect("info");

	}
}
