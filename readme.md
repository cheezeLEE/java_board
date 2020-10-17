 java와 oracle database를 이용한 게시판 만들기
 ===========================================
본 프로젝트는 java8과 oracle database11g를 사용하여 로그인, 사용자 정보 수정, 게시판기능을 구현하였다.

1. 프로젝트 생성 이유
    - java와 oracle을 연동하여 데이터를 주고받기를 연습하기 위함
   - 단순한 연습보다는 게시판의 형태를 갖추어 만들면 좋은 연습이 될 것 같아 게시판형식으로 구현함
  
1. 구상
   - scanner로 값을 입력하여 그에 해당하는 기능을 하는 게시판 형식 구현
   - 로그인 페이지, 회원정보 관리 페이지, 게시판 페이지로 구성됨
 
1. 기능

    1. 로그인 페이지
         - 로그인 기능 : 로그인 성공시 DB에 접속중인 아이디로 등록
         - 회원가입 기능 : 아이디, 비밀번호, 이름, 휴대폰번호를 입력하여 회원가입 성공시 DB에 정보 저장
         - 아이디/비밀번호 찾기 기능 : 회원가입에서 이용한 정보를 입력하여 DB와 정보를 비교 후 아이디/비밀번호를 알려줌
         - 프로그램 종료

    1. 회원정보 관리 페이지
        - 비밀번호 변경 : 변경할 비밀번호를 입력받아 DB를 수정하여 비밀번호를 변경
        - 게시판 페이지로 이동
        - 회원탈퇴 : 본인확인을 위한 비밀번호 확인후 회원데이터를 DB에서 삭제
        - 로그아웃 : 로그인시 등록한 접속중인 아이디를 제거해 로그인이 필요한 서비스에 접근이 불가능하도록함

    1. 게시판 페이지
        - 게시글 작성 : 제목과 내용을 입력받고, 작성자 아이디는 접속중인 사용자 아이디, 작성시간은 현재시간을 받아와 글을 등록함
        - 게시글 검색 : 제목으로 검색과 내용으로 검색으로 분리하여 선택된 곳에 입력한 키워드가 들어있는 글의 제목과 내용을 출력
        - 게시글 수정 : 등록되어있는 게시글의 제목을 입력받고, 접속중인 아이디와 작성자를 비교한 이후 새로운 제목과 내용을 입력받아 변경
        - 게시글 삭제 : 삭제할 게시글의 제목을 입력받고, 접속중인 아이디와 작성자를 비교한 이후 비밀번호 확인절차를 거쳐 DB에서 삭제함

1. 사용방법
    - oracledatabase를 사용하려는 프로그램(eclipse등)과 연동시키고 sql파일의 내용을 database에 입력하여 필요한 테이블을 만들고 실행
  
1. 실행화면
<img width="500" height="500" src="https://postfiles.pstatic.net/MjAyMDA5MzBfMjMg/MDAxNjAxNDcxODQyNjkx.omeEIk1_vIevXgCx3U7I-a3_WHryXZh4jXz1EC9jIuIg.3pROydlI1uLZUtB5k3EhFMPnN2E_x2JDza5UUGrA9wog.PNG.zndn121/image.png?type=w773">
<img width="500" height="500" src="https://postfiles.pstatic.net/MjAyMDA5MzBfNyAg/MDAxNjAxNDcxODc0NDQz.oY2b6vu0SUWrJVLnJa5dEeFAPYmmqWQv6KW2xhnHKVkg.eBY7OsKgwD77T891lBqzDfYMQfxyKpgNzosH8W9KKIog.PNG.zndn121/image.png?type=w773">
<img width="500" height="500" src="https://postfiles.pstatic.net/MjAyMDA5MzBfNTQg/MDAxNjAxNDcxODkxMDE0.rPrO9-ew4GLfs6Ih10h8HdVg_PJh8gS-HGyND-A5tcYg.5GAcKZt9dvh0TuNwIPWgcr4Ej7ZAOjwzx15Se6sLSEkg.PNG.zndn121/image.png?type=w773">
<img width="500" height="100" src="https://postfiles.pstatic.net/MjAyMDA5MzBfMjUw/MDAxNjAxNDcxOTIwNTIx.xrvc2gqo3G106aKR1tG7KKNSykdyUAFnMI0WvBqw0uwg.A265BkqGt7m00Yqd9Z8GP7lD7X8haLBd-emZoZN-pmQg.PNG.zndn121/image.png?type=w773">
