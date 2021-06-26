package com.erp.web.bss.mgr;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.erp.dao.AttSummaryDao;
import com.erp.dao.BusinessDao;
import com.erp.vo.AttSummary;
import com.erp.vo.Business;
import com.erp.vo.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bss/approval")		
public class BssAppServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 로그인여부 확인
		HttpSession session = req.getSession();
		Employee emp = (Employee)session.getAttribute("LOGINED_EMP");
		if(emp == null) {
			res.sendRedirect("/erp/index?faill=deny");
			return;
		}
		// 요청파라미터값 조회 (출장번호, 상태)
		String bssNo = req.getParameter("no");
		String status = req.getParameter("status");
		BusinessDao bDao = BusinessDao.getInstance();
		// 번호에 해당하는 출장객체 조회
		Business bss = bDao.getBusinessByNo(bssNo);
		
		
		// 반려처리일 경우
		if("reject".equals(status)){
			// 상태를 반려로 변겅
			bss.setStatus("반려");
			// 담당 매니저에 현재 접속한 매니저의 번호를 넣음
			bss.setManagerNo(emp.getNo());
			// 출장객체 업데이트 수행
			bDao.updateBusiness(bss);
			// 출장리스트페이지 재요청
			res.sendRedirect("/erp/bss/applist");
			return;
		}
		
		// 승인처리일경우 
		if("approval".equals(status)) {
			// 상태를 승인으로 변경
			bss.setStatus("승인");
			// 승인매니저에 현재 접속한 매니저의 번호 넣음
			bss.setManagerNo(emp.getNo());
			// 출장객체 업데이트
			bDao.updateBusiness(bss);
		
			////// attsum 업데이트 수행 (출장일수만큼 +, 1일당 8시간씩 +)
		
		// 출장신청한직원의 번호
		String empNo = bss.getEmpNo();
		// 현재 년 / 월을 조회하기 위해 캘린더 객체 획득
		Calendar calendar = Calendar.getInstance();
		// 현재 년 월 조회
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		AttSummaryDao asDao = AttSummaryDao.getInstance();
		// 년 월 요청한 직원번호 넣어서 그 직원의 이번달 attsum 반환
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("year", year);
		condition.put("month", month);
		condition.put("empNo", empNo);
		AttSummary existAttSum = asDao.getAttSummary(condition);
		if(existAttSum == null) { // 조회한 값ㅅ이 없는경우
			// 이번달 정보 없으면 생성하셈 다시 승인페이지 재요청
			AttSummary newAttSummary = new AttSummary();
			newAttSummary.setEmpNo(empNo);
			newAttSummary.setMonth(String.valueOf(month));
			asDao.insertAttSummary(newAttSummary);
			res.sendRedirect("/erp/bss/approval");
			return;			
		}
		// 만약 있으면 
		// attsum테이블에 승인된 출장일수만큼 + 1일당 근무시간 8시간 추가
		
		// 시작일자 종료일자의 년 월 구하기 
		SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		String startMonth = monthFormat.format(bss.getStartDate());
		String endMonth = monthFormat.format(bss.getEndDate());
		String startYear = yearFormat.format(bss.getStartDate());
		String endYear = yearFormat.format(bss.getEndDate());
		
		// 총 출장일수 구해야함
		int bssDays = bDao.getBssDays(bssNo);

		// 만약 신청일자와 종료일자 그리고 현재의 달이 모두 같아 그러면 현재 있는 서머리 테이블에다가 넣음
		if (String.valueOf(month).equals(startMonth) && String.valueOf(month).equals(endMonth)) {
			// 현재 있는 테이블에 출장일수와 근무시간 넣어서 업데이트 수행
			existAttSum.setBusinessDays(existAttSum.getBusinessDays() + bssDays);
			existAttSum.setWorkedHours(existAttSum.getWorkedHours() + (bssDays * 8));
			asDao.updateAttSummary(existAttSum);
			// 승인페이지 재요청
			res.sendRedirect("/erp/bss/applist");
			return;
		}
		// 만약 신청일자와 종료일자의 달은 같은데(7월) 현재달은 6월이야 그러면 새로 7월 테이블을 하나 생성해서 거기에다가 저장
		if (startMonth.equals(endMonth) && !String.valueOf(month).equals(startMonth)) {
			// 일단 7월 테이블이 있는지 없는지 확인하는게 중요
			Map<String, Object> find = new HashMap<String, Object>();
			find.put("year", Integer.parseInt(startYear));
			find.put("month",Integer.parseInt(startMonth));
			find.put("empNo", empNo);
			AttSummary findAttSum = asDao.getAttSummary(find);
			// 다음달 테이블이 이미 생성되어있을때 
			if(findAttSum != null) {
				// 찾은 테이블에다가 출장일수 근무시간 넣음
				findAttSum.setBusinessDays(findAttSum.getBusinessDays() + bssDays);
				findAttSum.setWorkedHours(findAttSum.getWorkedHours() + (bssDays * 8));
				asDao.updateAttSummary(findAttSum);
				res.sendRedirect("/erp/bss/applist");
				return;
			}
			// 없을떄는 테이블을 새로생성해서 출장일수 근무시간 삽입
			AttSummary newAttSummary = new AttSummary();
			newAttSummary.setEmpNo(empNo);
			newAttSummary.setMonth(String.valueOf(month + 1));
			newAttSummary.setBusinessDays(bssDays);
			newAttSummary.setWorkedHours(bssDays*8);
			asDao.insertAttSummary(newAttSummary);
			res.sendRedirect("/erp/bss/applist");
			return;
		}
		// 만약 신청일자의 시작은 이번달인데 종료일자는 다음달인 경우
		// 휴가중에 몇일은 이번달에 속하고 몇일은 다음달에 속하는지 알아내야함
		if (String.valueOf(month).equals(startMonth) && !String.valueOf(month).equals(endMonth)) {
			Date day = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(day);
			// 종료일자의 달의 1째날 알아내기
			cal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, 1);
			Date compareDay = cal.getTime();
			// 종료일자의 1일에 시작날짜 만큼 뺌 -> 전달에 해당하는 출장일수 구하기
			long dateDiff = compareDay.getTime() - bss.getStartDate().getTime();
			// 예를들면 starDate가 6월 29일 이면 compare은 2가 나와야지 7/1 - 6/29
			int compare = (int) (dateDiff / 1000 / 60 / 60 / 24);
			// 기존에 있던 테이블에는 6월분만큼만 +
			existAttSum.setBusinessDays(existAttSum.getBusinessDays() + compare);
			existAttSum.setWorkedHours(existAttSum.getWorkedHours() + (compare*8));
			asDao.updateAttSummary(existAttSum);

			// 7월분은 새로 테이블 생성해서 집어넣기 남은 만큼만
			// 7월분 테이블이 있는지 조회
			// 일단 7월 테이블이 있는지 없는지 확인하는게 중요
			Map<String, Object> find = new HashMap<String, Object>();
			find.put("year",Integer.parseInt(endYear));
			find.put("month",Integer.parseInt(endMonth));
			find.put("empNo", empNo);
			AttSummary findAttSum = asDao.getAttSummary(find);
			// 다음달 테이블이 이미 생성되어있을때
			if(findAttSum != null) {
				findAttSum.setBusinessDays(findAttSum.getBusinessDays() + (bssDays - compare));
				findAttSum.setWorkedHours(findAttSum.getWorkedHours() + ((bssDays - compare)*8));
				asDao.updateAttSummary(findAttSum);
				res.sendRedirect("/erp/bss/applist");
				return;
			}
			// 없을때
			AttSummary newAttSummary = new AttSummary();
			newAttSummary.setEmpNo(empNo);
			newAttSummary.setMonth(String.valueOf(month + 1));
			newAttSummary.setBusinessDays(bssDays - compare);
			newAttSummary.setWorkedHours((bssDays - compare)*8);
			asDao.insertAttSummary(newAttSummary);
			res.sendRedirect("/erp/bss/applist");
			return;

		}
		
		
		res.sendRedirect("/erp/bss/applist");
		return;
		
		
	}

}
}
