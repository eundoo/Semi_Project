package com.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.erp.util.MybatisUtils;
import com.erp.vo.Business;

public class BusinessDao {
	
	public static BusinessDao instance = new BusinessDao();
	
	private BusinessDao() {
		this.sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	}
	private SqlSessionFactory sqlSessionFactory;
	
	public static BusinessDao getInstance() {
		return instance;
	}
	
	
	/**
	 * 새로운 출장정보를 받아서 db에 저장
	 * @param business 새 출장정보
	 */
	public void insertBusiness(Business business) {
		SqlSession Session = sqlSessionFactory.openSession(true);
		Session.insert("business.insertbusiness", business);
		Session.close();
	}
	
	/**
	 * 사원번호를 전달받아 해당사원의 출장정보들 반환
	 * @param EmpNo 조회할 사원번호
	 * @return 사원에 해당하는 출장정보
	 */
	public List<Business> getAllListByEmpNo(String EmpNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Business> business = session.selectList("business.getAllListByEmpNo", EmpNo);
		session.close();
		return business;
	}

	/**
	 * 수정된 출장정보를 전달받아서 정보수정
	 * @param business 수정정보를 포함하는 출장객체
	 */
	public void updateBusiness(Business business) {
		SqlSession session = sqlSessionFactory.openSession(true);
		session.update("business.updateBusiness", business);
		session.close();
	}

	/**
	 * 출장번호로 해당하는 출장객체 조회
	 * @param no 출장번호
	 * @return 해당하는 출장객체
	 */
	public Business getBusinessByNo(String no) {
		SqlSession session = sqlSessionFactory.openSession();
		Business bss = session.selectOne("getBusinessByNo", no);
		session.close();
		return bss;
	}
	
	/**
	 * 부서정보와 출장상태를 전달받아서 해당하는 출장정보들 반환
	 * @param condition 부서정보, 상태(신청)을 담고있는 map객체
	 * @return 해당하는 출장리스트
	 */
	public List<Business> getAllListByDeptNo(Map<String, Object> condition) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Business> business = session.selectList("business.getAllListByDeptNo", condition);
		session.close();
		return business;
	
	}
	
	public List<Business> getTodayBusiness(int deptNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Business> business = session.selectList("business.getTodayBusiness", deptNo);
		session.close();
		return business;
		
	}
	
	public List<Business> getAppBusiness(String empNo) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Business> business = session.selectList("business.getAppBusiness", empNo);
		session.close();
		return business;
		
	}
	
	/**
	 * 출장객체의 총 출장일수를 반환하는 메소드
	 * @param bss 출장객체
	 * @return 출장일수
	 */
	public int getBssDays(String bssNo) {
		SqlSession session = sqlSessionFactory.openSession();
		int bssDays = session.selectOne("business.getBssDays", bssNo);
		session.close();
		return bssDays;
	}

}
