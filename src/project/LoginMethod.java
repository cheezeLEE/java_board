package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginMethod {
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connect conn;
	LoginMain2 main = new LoginMain2();
	
	//로그인을 실행하는 메소드
	public boolean loginSelect(String id, String pw) { 
		boolean success =false;
		conn = new Connect();
		conn.connect(); //데이터베이스 연결
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT * FROM login WHERE id='"+id+"'"; //매개변수로 들어온 id와 일치하는 데이터를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //매개변수로 들어온 id에 해당하는 데이터가 존재하면 실행
				if(pw.equals(rs.getString(2))) { //비밀번호가 같으면 로그인
					System.out.println("로그인에 성공하였습니다.");
					success = true; 
				}
				else { //비밀번호가 다를시
					System.out.println("비밀번호가 틀립니다.");				
				}
			}
		} catch (SQLException e) { //데이터가 존재하지 않을떄
			System.out.println("존재하지 않는 아이디입니다.");
			
		} finally {
			try { //사용이 끝난 것들을 닫아줌
				stmt.close();
				rs.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
		return success; //로그인실패(false)
	}
	
	//회원가입을 실행하는 메소드
	public boolean signUpSelect(String nid, String npw, String name, String phone) { 
		boolean success = false;
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT * FROM login WHERE id='"+nid+"'"; //같은 id가 db에 존재하는지 검색
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //중복되는 아이디가 존재하면 회원가입 안함
				System.out.println("이미 존재하는 아이디입니다");
				try {
					stmt.close();
					rs.close();
					conn.con.close();
				} catch (SQLException e) {
				}
				return success; //회원가입 실패(false)
			}
			
			insert(nid,npw,name,phone); //중복되는 id가 없으면, id와 pw를 회원 데이터베이스에 저장
			success = true; 
		
		} catch (SQLException e) {
			System.out.println("출력 실패");
		} finally {
			try {
				stmt.close();
				rs.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
		return success;
	}
	
	//회원가입할 id와 pw를 회원 데이터베이스에 저장하는 메소드
	public void insert(String id, String pw, String name, String phone) { 
		conn = new Connect();
		conn.connect();
		try { 
			String sql = "INSERT INTO login VALUES(?,?,?,?)"; //동적쿼리 
			pstmt = conn.con.prepareStatement(sql);  
			
			pstmt.setString(1, id); //인덱스1에 id값을 넣음
			pstmt.setString(2, pw);	//인덱스2에 pw값을 넣음
			pstmt.setString(3, name); //인덱스 3에 name값을 넣음
			pstmt.setString(4, phone); //인덱스 4에 phone값을 넣음
			pstmt.executeUpdate(); //업데이트 내용 적용 (COMMIT같은 역할)
			System.out.println("회원가입 성공");
		} catch (SQLException e) {
			System.out.println("삽입 실패");
		} finally {
			try {
				pstmt.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
	}
	
	//비밀번호를 변경하는 메소드
	public void updatePw(String id, String pw) { 
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "UPDATE login SET pw='"+pw+"' WHERE id='"+id+"'"; 
			//매개변수로 들어온 id에 해당하는 데이터의 pw를 매개변수에 들어온대로 변경해주는 쿼리
			stmt.executeUpdate(sql); //적용
			System.out.println("비밀번호 변경 완료");
		} catch (SQLException e) {
			System.out.println("수정 실패");
		} finally {
			try {
				stmt.close();
				conn.con.close();
			} catch (SQLException e) {
			}
		}
	}
	
	//회원탈퇴를 수행하는 메소드
	public void delete(String id, String pw) { 
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement();  
			String sql = "DELETE FROM login WHERE id='"+id+"' AND pw='"+pw+"'"; //id와 pw가 데이터베이스의 데이터와 동일한 행 삭제
			stmt.executeUpdate(sql); //적용~
			System.out.println("회원 탈퇴 성공");
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
	
	//아이디 찾기
	public void searchId(String name, String phone) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT id FROM login WHERE name='"+name+"' AND phone='"+phone+"'"; 
			//매개변수로 들어온 이름과 전화번호와 일치하는 id를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //다음이 있으면 실행됨 -> 매개변수로 들어온 아이디가 데이터베이스에 존재한다.
				String id = rs.getString(1); //id값을 받아와서 출력
				System.out.println("아이디는 "+id+"입니다.");
				try {
					stmt.close();
					rs.close();
					conn.con.close();
				} catch (SQLException e) {
				}
				return; //찾고자 하는 아이디를 출력하고 메소드 종료
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
		System.out.println("일치하는 회원정보가 없습니다.");
	}
	
	//비밀번호 찾기
	public void searchPw(String id, String name, String phone) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT pw FROM login WHERE id='"+id+"' AND name='"+name+"' AND phone='"+phone+"'"; 
			//매개변수로 들어온 id, 이름, 전화번호와 일치하는 pw를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //다음이 있으면 실행됨 -> 매개변수로 들어온 값을 가진 데이터가 데이터베이스에 존재한다.
				String pw = rs.getString(1);
				System.out.println("비밀번호는 "+pw+"입니다.");
				try {
					stmt.close();
					rs.close();
					conn.con.close();
				} catch (SQLException e) {
				}
				return; //찾고자 하는 비밀번호를 출력하고 메소드 종료
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
		System.out.println("일치하는 회원정보가 없습니다."); 
	}
}