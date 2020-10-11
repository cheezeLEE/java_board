package project;

import java.util.Scanner;

public class UserInfo {
	BbsMethod bbs = new BbsMethod();
	LoginMain2 main = new LoginMain2();
	Bulletin_board bb = new Bulletin_board();
	LoginMethod method = new LoginMethod();
	ConnectingUser conUser = new ConnectingUser();
	Scanner scan = new Scanner(System.in);
	
	//회원탈퇴
	public void secession() {
		String id = conUser.selectLoginUserId();
		String pw = conUser.selectLoginUserPw(); //접속중인 유저의 아이디와 비밀번호를 가져옴
		System.out.println("본인 확인을 위한 비밀번호를 입력해주세요 : ");
		String repeatpw = scan.next(); //비밀번호 재확인(보통 탈퇴할떄 많이 사용해서 넣음)
		if(pw.equals(repeatpw)) { //재확인한 비밀번호가 현재 비밀번호와 일치하면
			method.delete(id, pw); //회원탈퇴 메소드 실행
		} else { //다르면 
			System.out.println("비밀번호를 확인해주세요"); 
			main.iscontinue2 = true; //회원탈퇴 실패시 다시 회원정보 선택화면으로 이동
		}
	}
	
	//로그아웃
	public void logout() {
		conUser.deletelogoutUser(); //접속중인 유저 목록에서 제거
		main.iscontinue =true; //다시 로그인 화면으로 이동
		System.out.println("로그아웃에 성공하였습니다.");
	}

	//회원가입
	public void singUp() {
		System.out.println("사용할 id를 입력해주세요 : ");
		String nid = scan.next();
		System.out.println("사용할 pw를 입력해주세요: ");
		String npw = scan.next();
		System.out.println("이름을 입력하세요: ");
		String name = scan.next();
		System.out.println("휴대폰번호를 입력하세요: ");
		String phone = scan.next(); //회원가입에 필요한 정보 입력
		method.signUpSelect(nid, npw,name,phone); //입력받은 정보를 데이터베이스에 저장하는 메소드 실행
		main.iscontinue = true; //로그인 페이지로 이동
	}
	
	//아이디찾기
	public void searchId() {
		System.out.println("이름을 입력하세요: ");
		String idname = scan.next();
		System.out.println("전화번호를 입력하세요: ");
		String idphone = scan.next(); //필요한 정보를 입력받음
		method.searchId(idname, idphone); //그정보에 해당하는 아이디를 찾는 메소드 실행
		main.iscontinue = true;
	}
	
	//비밀번호찾기
	public void searchPw() {
		System.out.println("id를 입력하세요: ");
		String pwid = scan.next();
		System.out.println("이름을 입력하세요: ");
		String pwname = scan.next();
		System.out.println("전화번호를 입력하세요: ");
		String pwphone = scan.next(); //필요한 정보를 입력받음
		method.searchPw(pwid,pwname, pwphone); //그 정보에 해당하는 비밀번호를 찾는 메소드 실행
		main.iscontinue = true;
	}
}