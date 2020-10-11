package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectingUser {
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connect conn;
	String id, pw;
	
	public void insertLoginUser(String id, String pw) { //로그인중인 사용자의 id와 pw를 db에 저장
		conn = new Connect();
		conn.connect();
		try { 
			String sql = "INSERT INTO users VALUES(?,?)"; 
			pstmt = conn.con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw); //id와 pw를 데이터베이스에 넣음
			pstmt.executeUpdate(); //커밋
			System.out.println("로그인 유저 저장 성공");
		} catch (SQLException e) {
			System.out.println("로그인 유저 저장 실패");
		} finally {
			try {
				pstmt.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public String selectLoginUserId() { //접속중인 사용자의 id를 출력
		conn = new Connect();
		conn.connect();
		String conid = null;
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT id FROM users"; //users에 저장된 id출력 
			rs = stmt.executeQuery(sql); 
			while(rs.next()) { 
				String id = rs.getString(1);
				conid = id;
			}
		} catch (SQLException e) {
			System.out.println("SQL오류");
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
		return conid;
	}
	
	public String selectLoginUserPw() { //접속중인 사용자의 pw를 선택
		conn = new Connect();
		conn.connect();
		String conpw = null;
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT pw FROM users"; 
			rs = stmt.executeQuery(sql); 
			while(rs.next()) { 
				String pw = rs.getString(1); //pw를 변수에 저장
				conpw = pw;
			}
		} catch (SQLException e) {
			System.out.println("SQL오류");
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
		return conpw;
	}
	
	public void deletelogoutUser() { //로그아웃하거나 프로그램 종료시 db에서 삭제
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement();  
			String sql = "DELETE FROM users"; //접속중인 아이디 삭제
			stmt.executeUpdate(sql); 
			System.out.println("로그아웃한 유저를 접속중인 유저 데이터에서 삭제");
			} catch (SQLException e) {
			System.out.println("삭제 실패");
		} finally {
			try {
				stmt.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
	}	
}
