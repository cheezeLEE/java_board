package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BbsMethod {
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Connect conn;
	ConnectingUser conUser = new ConnectingUser();
	
	//현재시간을 받아오는 메소드 : 게시글을 작성한 시간을 표시하기 위한 메소드
	public String thisTime() {
		conn = new Connect();
		conn.connect();
		String time = null;
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT SYSDATE FROM dual"; //현재 시간을 받아오는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행
			while(rs.next()) {
				time = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("시간 출력 실패");
		} finally {
			try {
				conn.con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
			}
		}
		return time;
	}
	
	//글을 작성하는 메소드 : 게시판에 글 작성
	public void write(String title, String content, String time, String id) {
		conn = new Connect();
		conn.connect();
		id = conUser.selectLoginUserId();
		try { 
			String sql = "INSERT INTO bbs VALUES(?,?,?,?)"; //동적 쿼리
			pstmt = conn.con.prepareStatement(sql); 
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, time);
				pstmt.setString(4, id); //접속중인 유저의 id를 받아옴
				pstmt.executeUpdate(); 
				System.out.println("성공적으로 게시물이 작성되었습니다.");
		} catch (SQLException e) {
			System.out.println("게시물 작성 실패");
		} finally {
			try {
				pstmt.close();
				conn.con.close();
			} catch (SQLException e) {}
		}
	}
	
	//작성된 글의 목록을 보여주는 메소드 : 글을 수정/삭제할때, 글의 목록을 보여줌
	public void select() {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT * FROM bbs";
			rs = stmt.executeQuery(sql); //쿼리실행
			System.out.println("글 목록");
			while(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				String time = rs.getString(3);
				String id = rs.getString(4); //글의 정보를 변수에 저장해 출력
				System.out.printf("제목: %s, 내용: %s, 작성시간: %s, 작성자: %s%n",title, content, time, id);
			}
		} catch (SQLException e) {
			System.out.println("게시글이 없습니다.");
		} finally {
			try {
				conn.con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
			}
		}
	}
	
	//글의 작성자를 출력하는 메소드 : 수정/삭제할 게시물의 작성자가 로그인한 사용자 본인과 동일한지 확인하는 기능
	public String writerId(String text) {
		conn = new Connect();
		conn.connect();
		String id = null;
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT * FROM bbs WHERE title='"+text+"'"; 
			//매개변수로 받아온 제목의 게시물의 작성자를 확인
			rs = stmt.executeQuery(sql); //쿼리실행
			while(rs.next()) {
				id = rs.getString(4); //id만 가져옴
			}
		} catch (SQLException e) {
			System.out.println("해당하는 게시글이 없습니다.");
		} finally {
			try {
				conn.con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
			}
		}
		return id;
	}
		
	//자신이 쓴 글을 수정하는 메소드 : 자기가 쓴 글중에 제목이 일치하는것의 제목과 내용을 변경
	public void update(String title, String content, String time, String id) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement();
			
			//여러개의 쿼리문을 받아 실해하는 방법
			String sql1 = "UPDATE bbs SET title='"+title+"' WHERE id = '"+id+"'"; 
			String sql2 = "UPDATE bbs SET content='"+content+"' WHERE id = '"+id+"'"; 
			conn.con.setAutoCommit(false); //오토커밋을 해제 : 오토커밋을 true로 하면 rollback을 할 수 없고, 예외에 대한 처리를 수동으로 해줘야한다.
		      stmt.addBatch(sql1);
		      stmt.addBatch(sql2);
		      stmt.executeBatch(); //쿼리문 실행
		      
			System.out.println("글 내용 수정 완료");
		} catch (SQLException e) {
			System.out.println("해당 게시물이 존재하지 않습니다.");
		} finally {
			try {
				stmt.close();
				conn.con.close();
			} catch (SQLException e) {}
		}
	}
	
	//아이디와 비밀번호가 맞으면 글을 삭제하는 메소드  : 게시판 글 삭제 -> 삭제하기 전에 본인확인을 위한 비밀번호를 main페이지에서 입력해줌
	public void delete(String id, String title) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement();  
			String sql = "DELETE FROM bbs WHERE id='"+id+"' AND title ='"+title+"'"; //id와 제목이 일치하면 글 삭제
			stmt.executeUpdate(sql); //적용~
			System.out.println("글 삭제 성공");
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
	
	//제목을 검색하는 메소드 : 키워드를 검색하면 그 키워드가 들어간 제목을 가진 게시글을 가져옴
	public void searchTitle(String word) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT title, content FROM bbs WHERE title LIKE '%"+word+"%'"; //체목이 받아온 키워드를 포함할때 
			rs = stmt.executeQuery(sql); //쿼리실행
			System.out.println("검색 결과");
			while(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				System.out.println("제목 : "+title);
				System.out.println("내용 : "+content); //검색결과를 표시
			}
		} catch (SQLException e) {
			System.out.println("검색 결과가 없습니다.");
		} finally {
			try {
				conn.con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
			}
		}
	}
	
	//내용을 검색하는 메소드 : 키워드를 검색하면 그 키워드가 들어간 내용을 가진 게시글을 가져옴
	public void searchContent(String word) {
		conn = new Connect();
		conn.connect();
		try {
			stmt = conn.con.createStatement(); 
			String sql = "SELECT title, content FROM bbs WHERE content LIKE '%"+word+"%'";  //위의 내용과 동일
			rs = stmt.executeQuery(sql); //쿼리실행
			System.out.println("검색 결과");
			while(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				System.out.println("제목 : "+title);
				System.out.println("내용 : "+content);
			}
		} catch (SQLException e) {
			System.out.println("검색 결과가 없습니다.");
		} finally {
			try {
				conn.con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
			}
		}
	}
}