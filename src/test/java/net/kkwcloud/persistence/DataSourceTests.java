package net.kkwcloud.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.kkwcloud.mapper.TimeMapper;

@RunWith(SpringJUnit4ClassRunner.class) // 메서드 별로 테스트
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // 참조할 파일
@Log4j2 // log 출력용
public class DataSourceTests {

	@Setter(onMethod_ = @Autowired)
	private DataSource dataSource;
	
	@Setter(onMethod_ = @Autowired)
	private SqlSessionFactory sqlSessionFactory;
	
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName()); // 클래스의 이름을 출력
		//  INFO  net.kkwcloud.persistence.DataSourceTests(testGetTime38) - com.sun.proxy.$Proxy36
		log.info(timeMapper.getTime()); // select 쿼리가 있는 메서드
		//  INFO  net.kkwcloud.persistence.DataSourceTests(testGetTime39) - 2024-08-13 15:58:37
	}
	
	@Test
	public void testGetTimeXML() {
		log.info(timeMapper.getClass().getName());
		// INFO  net.kkwcloud.persistence.DataSourceTests(testGetTimeXML46) - com.sun.proxy.$Proxy36
		log.info(timeMapper.getTimeXML());
		// INFO  net.kkwcloud.persistence.DataSourceTests(testGetTimeXML47) - 2024-08-13 16:29:22
		
		
		// log4jdbc적용 후 결과 
		//|--------------------|
		//|sysdate             |
		//|--------------------|
		//|2024-08-13 16:40:45 |
		//|--------------------|
	}
	
	@Test
	public void testMyBatis() {
		// 동적쿼리문을 처리해주는 sql 매핑용 테스트 
		
		try {
			
			SqlSession sqlSession = sqlSessionFactory.openSession(); 
			Connection connection = sqlSession.getConnection();
			log.info(sqlSession);
			log.info(connection);
			// INFO  net.kkwcloud.persistence.DataSourceTests(testMyBatis41) - org.apache.ibatis.session.defaults.DefaultSqlSession@61f80d55
			// INFO  net.kkwcloud.persistence.DataSourceTests(testMyBatis42) - HikariProxyConnection@665317128 wrapping oracle.jdbc.driver.T4CConnection@6dc1484
			
		} catch (Exception e) {
			fail(e.getMessage());
			
		}
		
	}
	
	@Test // JUnit 테스트용
	public void testConnection() {
	
		Connection connection;
		try {
			connection = dataSource.getConnection();
			log.info(connection); // 성공시 처리된는 로그
			//  INFO  net.kkwcloud.persistence.DataSourceTests(testConnection33)
			//  HikariProxyConnection@822359325 wrapping oracle.jdbc.driver.T4CConnection@3249a1ce
		} catch (Exception e) {
			fail(e.getMessage()); // 실패시 처리되는 로그
			
		}
			
	}
	
	
	
}
