package project;

import java.util.Scanner;

public class Login {
	BbsMethod bbs = new BbsMethod();
	LoginMain2 main = new LoginMain2();
	Bulletin_board bb = new Bulletin_board();
	LoginMethod method = new LoginMethod();
	UserInfo user = new UserInfo();
	ConnectingUser conUser = new ConnectingUser();
	Scanner scan = new Scanner(System.in);
	
	public void loginPage() {
		main.iscontinue = false; //한바퀴 돌고 종료
		System.out.println("로그인 페이지입니다.");
		System.out.println("1.로그인 | 2.회원가입 | 3.아이디찾기 | 4.비밀번호찾기 | 5.프로그램종료"); 
		LoginMain2.select1 = scan.nextInt(); //이동할 곳을 입력받음
	}
	public void login() {
		System.out.println("id : ");
		String id = scan.next(); //아이디를 입력
		System.out.println("pw : ");
		String pw = scan.next(); //비밀번호 입력
		if(method.loginSelect(id, pw)==true) { //로그인이 성공적으로 이루어지면 아래의 내용 실행
			conUser.insertLoginUser(id, pw); //로그인한 유저의 아이디와 비밀번호를 db에 저장
			main.iscontinue2 = true; 
			while(main.iscontinue2){//회원탈퇴 실패시 아래의 코드가 반복되도록 while문을 넣음
				main.iscontinue2 = false; //반복문을 한번 실행하고 종료
				System.out.println("1.비밀번호 변경  | 2.게시판 | 3.회원탈퇴 | 4.로그아웃");

				int select2 = scan.nextInt(); //이동할 곳을 입력받음
				switch(select2) {
				case 1: //비밀번호 변경
					System.out.println("변경할 비밀번호를 입력하세요 : ");
					String npw = scan.next(); 
                    //변경하고자 하는 pw를 입력받음
					method.updatePw(id,npw); 
                    //접속중인 사용자 id와 입력받은 pw를 매개변수로 전달
					main.iscontinue2=true; //비밀번호 변경 완료후 회원정보 페이지로 이동
					break;
					
				case 2:
					boolean iscontinue3 = true;
					while(iscontinue3) {
						System.out.println("1.게시글 작성 | 2.게시글 검색 | 3.게시글 수정 | 4.게시글 삭제 | 5.회원정보 페이지로 이동 ");
						int select3 = scan.nextInt(); //할일을 입력받음
						switch(select3) {
						case 1: //게시글 작성
							bb.postWrite();
							break;
							
						case 2: //게시글 검색
							bb.postSearch(); 
							break;
						
						case 3: //게시글 수정
							bb.postUpdate();
							break;
							
						case 4: //게시글 삭제
							bb.postDelete();
							break;
							
						case 5: //이전 페이지로 이동 : 짧아서 메소드로 구현하지 않음
							System.out.println("회원정보 페이지로 이동합니다");
                            iscontinue3 = false;
							main.iscontinue2 = true;
							break;
						}
					}
					break;
					
				case 3: //회원 탈퇴
					user.secession();
					break;
					
				case 4: //로그아웃 : 로그아웃시 접속중인 사용자 목록에서 제거
					user.logout();
					break;												
				}
			}		
		} else {
			System.out.println("로그인 실패");
			main.iscontinue = true; //다시 로그인 페이지로 돌아감
		}
	}
}
