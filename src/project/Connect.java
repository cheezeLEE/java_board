package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	//드라이버 로드
		static{
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		} catch (ClassNotFoundException e) {}
		}

	//드라이버 연결
	public void connect() { 
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String userid = "java";
		String pwd = "java";
		
		try {
			con = DriverManager.getConnection(url,userid,pwd); 
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 실패");
		}
	}
}
