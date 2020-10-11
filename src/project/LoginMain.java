package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginMain {
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	//드라이버 로드
	static {
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {}
	}
	
	//드라이버 연결
	public void connect() { 
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String userid = "java";
		String pwd = "java";
		
		try {
			con = DriverManager.getConnection(url,userid,pwd); 
			System.out.println("Connection Success!");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 실패");
		}
	}
	
	//로그인을 실행하는 메소드
	public boolean loginSelect(String id, String pw) { 
		boolean success =false;
		try {
			stmt = con.createStatement(); 
			String sql = "SELECT * FROM login WHERE id='"+id+"'"; //매개변수로 들어온 id와 일치하는 데이터를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //다음이 있으면 실행됨 -> 매개변수로 들어온 아이디가 데이터베이스에 존재한다.
				if(pw.equals(rs.getString(2))) { //비밀번호가 같으면
					System.out.println("로그인에 성공하였습니다.");
					success = true; 
					return success; //로그인성공(true)
				}
				else {
					System.out.println("비밀번호가 틀립니다.");
					return success; //로그인실패(false)
				}
			}
		} catch (SQLException e) {
			System.out.println("출력 실패");
			return success; //로그인실패(false)
		}
		System.out.println("존재하지 않는 아이디입니다.");
		return success; //로그인실패(false)
	}
	
	//회원가입을 실행하는 메소드
	public boolean signUpSelect(String nid, String npw, String name, String phone) { 
		boolean success = false;
		try {
			stmt = con.createStatement(); 
			String sql = "SELECT * FROM login WHERE id='"+nid+"'"; //id와 일치하는 쿼리문이
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //중복되는 아이디가 존재하면
				System.out.println("이미 존재하는 아이디입니다");
				return success; //회원가입 실패(false)
			}
			insert(nid,npw,name,phone); //id와 pw를 회원 데이터베이스에 저장
			success = true; 
			return success; //회원가입 성공
		
		} catch (SQLException e) {
			System.out.println("출력 실패");
			return success; //회원가입 실패
		}
	}
	
	//회원가입할 id와 pw를 회원 데이터베이스에 저장하는 메소드
	public void insert(String id, String pw, String name, String phone) { 
		try { 
			String sql = "INSERT INTO login VALUES(?,?,?,?)"; //동적쿼리, 
			pstmt = con.prepareStatement(sql); //반복문을 돌리기 위한 가변쿼리를 사용하기 위해 PreparedStatement이용
																 //이거는 resultstatement가 필요없음
				pstmt.setString(1, id); //인덱스1에 id값을 넣음
				pstmt.setString(2, pw);	//인덱스2에 pw값을 넣음
				pstmt.setString(3, name);
				pstmt.setString(4, phone);
				pstmt.executeUpdate(); //업데이트 내용 적용 (COMMIT같은 역할)
				System.out.println("회원가입 성공");
		} catch (SQLException e) {
			System.out.println("삽입 실패");
		}
	}
	
	//비밀번호를 변경하는 메소드
	public void updatePw(String id,String pw) { 
		try {
			stmt = con.createStatement(); //3. 
			String sql = "UPDATE login SET pw='"+pw+"' WHERE id='"+id+"'"; //매개변수로 들어온 id에 해당하는 데이터의 pw를 매개변수에 들어온대로 변경해주는 쿼리
			stmt.executeUpdate(sql); //적용
			System.out.println("비밀번호 변경 완료");
		} catch (SQLException e) {
			System.out.println("수정 실패");
		}
	}
	
	//회원탈퇴를 수행하는 메소드
	public void delete(String id, String pw) { 
		try {
			stmt = con.createStatement();  
			String sql = "DELETE FROM login WHERE id='"+id+"' AND pw='"+pw+"'"; //id와 pw가 데이터베이스의 데이터와 동일한 행 삭제
			stmt.executeUpdate(sql); //적용~
			System.out.println("회원 탈퇴 성공");
			} catch (SQLException e) {
			System.out.println("삭제 실패");
		}
	}
	
	//아이디 찾기
	public void searchId(String name, String phone) {
		try {
			stmt = con.createStatement(); 
			String sql = "SELECT id FROM login WHERE name='"+name+"' AND phone='"+phone+"'"; //매개변수로 들어온 id와 일치하는 데이터를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //다음이 있으면 실행됨 -> 매개변수로 들어온 아이디가 데이터베이스에 존재한다.
				String id = rs.getString(1);
				System.out.println("아이디는 "+id+"입니다.");
				return; //찾고자 하는 아이디를 출력하고 메소드 종료
			}
		} catch (SQLException e) {
			System.out.println("SQL오류");
		}
		System.out.println("일치하는 회원정보가 없습니다.");
	}
	
	//비밀번호 찾기
	public void searchPw(String id, String name, String phone) {
		try {
			stmt = con.createStatement(); 
			String sql = "SELECT pw FROM login WHERE id='"+id+"' AND name='"+name+"' AND phone='"+phone+"'"; //매개변수로 들어온 id와 일치하는 데이터를 검색하는 쿼리문
			rs = stmt.executeQuery(sql); //쿼리실행

			while(rs.next()) { //다음이 있으면 실행됨 -> 매개변수로 들어온 아이디가 데이터베이스에 존재한다.
				String pw = rs.getString(1);
				System.out.println("비밀번호는 "+pw+"입니다.");
				return; //찾고자 하는 비밀번호를 출력하고 메소드 종료
			}
		} catch (SQLException e) {
			System.out.println("SQL오류");
		}
		System.out.println("일치하는 회원정보가 없습니다."); 
	}
	
	
	public static void main(String[] args) {
		LoginMain login = new LoginMain();
		login.connect(); //연결
		Scanner scan = new Scanner(System.in); //사용자 입력
		boolean iscontinue = true; //로그아웃, 회원가입 실패시 다시 로그인 페이지로 이동하기 위한 변수
		while(iscontinue) {
			iscontinue = false; //다시 true로 설정해 주지 않는한 한바퀴 돌고 종료
			System.out.println("로그인 페이지입니다.");
			System.out.println("1.로그인 | 2.회원가입 | 3.아이디찾기 | 4.비밀번호찾기   | 5.프로그램종료"); 
			int select1 = scan.nextInt();
			switch(select1) {
			
			case 1:
				System.out.println("id : ");
				String id = scan.next();
				System.out.println("pw : ");
				String pw = scan.next();
				if(login.loginSelect(id, pw)==true) { //로그인이 성공적으로 이루어지면  아래의 내용 실행
					
					boolean iscontinue2 = true; 
					while(iscontinue2){//회원탈퇴 실패시 아래의 코드가 반복되도록 while문을 넣음
						iscontinue2 = false;
						System.out.println("1.비밀번호 변경  | 2.회원탈퇴 | 3.로그아웃 | 그외.프로그램종료"); //글쓰기, 글수정, 글삭제 나중에 구현
						int select2 = scan.nextInt();
	
						switch(select2) {
						case 1:
							System.out.println("변경할 비밀번호를 입력하세요 : ");
							pw = scan.next(); //id는 이미 입력받았으니 변경하고자 하는 pw를 입력받음
							login.updatePw(id, pw); //로그인시 사용한 id와 변경하고자하는 pw를 매개변수로 전달
							iscontinue2=true; //비밀번호 변경 완료후 회원정보 페이지로 이동
							break;
						case 2:
							System.out.println("본인 확인을 위한 비밀번호를 입력해주세요 : ");
							String repeatpw = scan.next(); //비밀번호 재확인(보통 탈퇴할떄 많이 사용해서 넣음)
							if(pw.equals(repeatpw)) { //재확인한 비밀번호가 현재 비밀번호와 일치하면
								login.delete(id, pw); //회원탈퇴 메소드 실행
							} else { //다르면 
								System.out.println("비밀번호를 확인해주세요"); 
								iscontinue2 = true; //회원탈퇴 실패시 다시 회원정보 선택화면으로 이동
							}
							break;
						case 3:
							id = null;
							pw = null;
							iscontinue =true; //다시 로그인 화면으로 이동
							System.out.println("로그아웃에 성공하였습니다.");
							break;
						}
					} //여기까지 안쪽 while문
					
				} else {
					System.out.println("로그인 실패");
					iscontinue = true; //다시 로그인 페이지로 돌아감
					break;
				} //case 1은 여기까지..ㄷㄷ
				break;
				
			case 2: //회원가입
				System.out.println("사용할 id를 입력해주세요 : ");
				String nid = scan.next(); //case가 다른 switch문인데 중복이 안되네...
				System.out.println("사용할 pw를 입력해주세요: ");
				String npw = scan.next(); //가입할 id와 pw를 입력
				System.out.println("이름을 입력하세요: ");
				String name = scan.next();
				System.out.println("휴대폰번호를 입력하세요: ");
				String phone = scan.next();
				login.signUpSelect(nid, npw,name,phone);
				iscontinue = true;
				break;
			
			case 3: //아이디 찾기
				System.out.println("이름을 입력하세요: ");
				String idname = scan.next();
				System.out.println("전화번호를 입력하세요: ");
				String idphone = scan.next();
				login.searchId(idname, idphone);
				iscontinue = true;
				break;
				
			case 4: //비밀번호 찾기
				System.out.println("id를 입력하세요: ");
				String pwid = scan.next();
				System.out.println("이름을 입력하세요: ");
				String pwname = scan.next();
				System.out.println("전화번호를 입력하세요: ");
				String pwphone = scan.next();
				login.searchPw(pwid,pwname, pwphone);
				iscontinue=true;
				break;
				
			case 5: //프로그램 종료
				System.out.println("프로그램을 종료합니다.");
//				break;
			}
		}
		
		try {
			login.con.close();
//			login.stmt.close();
//			login.pstmt.close();
//			login.rs.close(); //NullPointerException
		} catch (SQLException e) {}
		scan.close();
	}

}
