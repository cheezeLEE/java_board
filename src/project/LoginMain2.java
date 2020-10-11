package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginMain2 {	
	Statement stmt; ResultSet rs; PreparedStatement pstmt; Connection con;
	String id, pw;
	static int select1;
	boolean iscontinue, iscontinue2, iscontinue3;
	
	public static void main(String[] args) {
		LoginMain2 main = new LoginMain2();
		UserInfo user = new UserInfo();
		Login login = new Login();
		main.iscontinue = true; //로그아웃, 회원가입 실패시 다시 로그인 페이지로 이동하기 위한 변수
		while(main.iscontinue) {
			login.loginPage();
			switch(select1) {
			
			case 1: //로그인
				login.login();
				break;
				
			case 2: //회원가입
				user.singUp();
				break;
			
			case 3: //아이디 찾기
				user.searchId();
				break;
				
			case 4: //비밀번호 찾기
				user.searchPw();
				break;
				
			case 5: //프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				main.iscontinue= false;
				break;
			}
		}
	}
}