package project;

import java.util.Scanner;

public class Bulletin_board {
	BbsMethod bbs = new BbsMethod();
	LoginMain2 main = new LoginMain2();
	ConnectingUser conUser = new ConnectingUser();
	Scanner scan = new Scanner(System.in);
	
	//글을 작성하는 메소드
	public void postWrite() { 
		System.out.println("제목을 입력하세요: ");
		String title = scan.next();
		System.out.println("내용을 입력하세요: ");
		String content = scan.next(); //제목과 내용을 입력받음
		String time = bbs.thisTime(); //현재시간 가져옴
		String id = conUser.selectLoginUserId(); //사용자의 id를 받아옴
		bbs.write(title, content, time, id);
	}
	
	public void postSearch() {
		System.out.println("1.제목으로 검색 | 2.내용으로 검색");
		int select4 = scan.nextInt();
		System.out.println("검색어를 입력하세요");
		String word = scan.next(); //키워드를 받아옴
		if(select4 == 1) {
			bbs.searchTitle(word); //제목으로 검색
		} else if(select4 ==2) {
			bbs.searchContent(word); //내용으로 검색
		}
	}
	
	public void postUpdate() {
		bbs.select();
		try {
			System.out.println("수정할 게시글의 제목을 입력하세요");
			String otitle = scan.next(); //수정할 게시글의 제목 입력
			String uid = bbs.writerId(otitle); //게시글의 아이디 가져옴
			String id = conUser.selectLoginUserId(); //접속중인 사용자의 id가져옴
			if(uid.equals(id)) { //작성자와 접속자가 같은경우 수행
				System.out.println("제목을 입력하세요");
				String utitle = scan.next(); //수정할 제목 입력
				System.out.println("내용을 입력하세요");
				String ucontent = scan.next(); //수정할 내용 입력
				String utime = bbs.thisTime();
				bbs.update(utitle, ucontent, utime, uid); //매개변수로 주면서 변경
			} else { //작성자와 접속자가 다르면 수정불가
				System.out.println(id+"님이 작성하신 글이 아닙니다.");
			}
		} catch (NullPointerException e) {
			System.out.println("해당 작성자가 없습니다.");
		}
	}
	
	public void postDelete() {
		bbs.select();
		System.out.println("삭제할 게시글의 제목을 입력하세요");
		String dtitle = scan.next(); //삭제할 게시물 제목
		String did = bbs.writerId(dtitle); //삭제할 게시글의 작성자
		String id = conUser.selectLoginUserId(); //유저의 id
		String pw = conUser.selectLoginUserPw(); //유저의 pw
		
		if(id.equals(did)) { //작성자id와 유저id가 같으면 실행
			System.out.println("본인 확인을 위한 비밀번호를 입력하세요");
			String dpw = scan.next();
			if(pw.equals(dpw)) { //작성자id가 유저id이므로 유저의 비밀번호와 입력된 비밀번호가 같은지 비교
				bbs.delete(did, dtitle);
			} else {
				System.out.println("비밀번호가 올바르지 않습니다.");
			}
		} else {
			System.out.println(id+"님이 작성하신 글이 아닙니다.");
		}
	}
}
