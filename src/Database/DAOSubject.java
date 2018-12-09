package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOSubject {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/se_term_1?autoReconnect=true&useSSL=false";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch(ClassNotFoundException e) {
			System.out.println("클래스 로드 실패 : "+e.getMessage());
		}
	}
	
	private DAOSubject() {}
	private static DAOSubject obj;
	
	public static DAOSubject sharedInstance() {
		if(obj == null) {
			obj = new DAOSubject();
		}
		return obj;
	}
	// 데이터베이스 연동에 필요한 변수들을 선언
	Connection conn;
	
	// SQL 실행에 필요한 변수
	Statement stmt; 
	
	// select 구문을 수행했을 때 결과를 저장할 변수
	private ResultSet rs;
	
	private boolean connect() {
		boolean result = false;
		
		try{
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			System.out.println("\n- MySQL Connection");
			result = true;
		} catch (Exception e) {
			System.out.println("연결 실패 : " +e.getMessage());
		}
		return result;
	}
	
	private void close() {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		} catch( Exception e) {
			System.out.println("해제 실패 : " + e.getMessage());
		}
	}
	
	public List<Subject> getSubjectList() { // select
		List<Subject> list = null;
		String sql = "SELECT * FROM subject";
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);
					
					list = new ArrayList<Subject>();
					
					while(rs.next()) {
						Subject subject = new Subject();
						subject.setClassIdNum(rs.getString("classIdNum"));
						subject.setSubjectName(rs.getString("subjectName"));
						subject.setClassNum(rs.getString("classNum"));
						subject.setClassTime(rs.getString("classTime"));
						subject.setClassRoom(rs.getString("classRoom"));
						subject.setSyllabus(rs.getString("syllabus"));
						subject.setAvailNum(rs.getInt("availNum"));
						
						list.add(subject);
					}
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			// 연결에 실패했을 때 작업
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}
	
	public boolean InsertSubject(Subject subject) {
		boolean result = false;
		
		if(this.connect()) {
			try {
				String sql = "INSERT INTO subject VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, subject.getClassIdNum());
				pstmt.setString(1, subject.getSubjectName());
				pstmt.setString(3, subject.getClassNum());
				pstmt.setString(4, subject.getClassTime());
				pstmt.setString(5, subject.getClassRoom());
				pstmt.setString(6, subject.getSyllabus());
				pstmt.setInt(7, subject.getAvailNum());
				
				int r = pstmt.executeUpdate();
				
				if(r>0) {
					result = true;
				}
				// 데이터베이스 생성 객체 해제
				pstmt.close();
				this.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public boolean modifySubject(Subject subject) {
		boolean result = false;

		if(this.connect()) {
			try {
				String sql = "UPDATE subject set subjectName='"+subject.getSubjectName()+"', "
						+ "classNum='"+subject.getClassNum()+"', classTime = '"+subject.getClassTime()+"', classRoom='"
						+ subject.getClassRoom()+"', syllabus = '"+subject.getSyllabus()+"', availNum = '" 
						+ subject.getAvailNum()+"' WHERE classIdNum ='"+subject.getClassIdNum()+"'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if(r>0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public boolean deleteSubject(Subject subject) {
		boolean result = false;

		if(this.connect()) {
			try {
				String sql = "DELETE from subject WHERE classIdNum='"+subject.getClassIdNum()+"'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if(r>0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}
}
