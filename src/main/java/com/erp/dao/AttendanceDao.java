package com.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.erp.util.MybatisUtils;
import com.erp.vo.Attendance;

public class AttendanceDao {

	private SqlSessionFactory sqlSessionFactory;
	
	public static AttendanceDao instance = new AttendanceDao();
	
	private AttendanceDao() {
		this.sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	}
	
	public static AttendanceDao getInstance() {
		return instance;
	}	
	
	/**
	 * 직원번호를 받아서 그 날의 근태테이테이블을 생성한다
	 * @param empNo 직원번호
	 */
	public void insertAttendance(String empNo) {
		SqlSession session = sqlSessionFactory.openSession(true);
		session.insert("attendances.insertAttendance", empNo);
		session.close();
	}
	
	/**
	 * 외출과 복귀정보입력하는 메소드
	 * @param att
	 */
	public void updateAttendance(Attendance att) {
		SqlSession session = sqlSessionFactory.openSession(true);
		session.update("attendances.updateAttendance", att);
		session.close();
	}
	
	/**
	 * 퇴근시간을 입력하는 메소드
	 * @param att
	 */
	public void updateWorkOut(Attendance att) {
		SqlSession session = sqlSessionFactory.openSession(true);
		session.update("attendances.updateWorkOut", att);
		session.close();		
	}
	
	
	/**
	 * 직원번호와 , workdays를 집력해서 그날의 직원근테 테이블을 불러온다
	 * @param condition 직원번호와 workDay
	 * @return
	 */
	public Attendance getAttendence(Map<String, Object> condition) {
		SqlSession session = sqlSessionFactory.openSession();
		Attendance attendance = session.selectOne("attendances.getAttendace", condition);
		session.close();
		return attendance;		
	}


}
