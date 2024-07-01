# Springboot-2024
자바 빅데이터 개발자과정 springboot 학습 리포지토리

## 1일차
- Spring Boot 개요
    - Servlet > EJB > JSP > Spring > Spring Boot

    - MVC    
        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp002.png" width="730">

    - 장점
        - Spring의 기술을 그대로 사용가능(simple migration)
        - JPA를 사용하면 ERD나 DB 설계를 하지않고도 손쉽게 DB 생성
        - Tomcat 서버 내장(따로 설치할 필요 없음)
        - Support 기능 다수 사용 가능
        - JUnit 테스트 Log4J2 로그 모두 포함
        - JSP, **Thymeleaf**, Mustache 편하게 사용가능
        - DB 연동이 매우 쉽다

- Spring Boot 개발환경 설정
    - Java JDK 확인 > 17버젼 이상
        - https://jdk.java.net/archive/
        - 시스템 속성(sysdm.cpl) > 환경변수 > JAVA_HOME 설정
        
    - Visual Studio Code
        - System Installer로 설치
        - Extensions > Korean 언어팩
        - Extensions > JAVA > Extension Pack for Java
        - Extensions > Spring > Spring Boot Extension Pack
        - Extensions > Gradle > Gradle for Java

    - Gradle build tool 고려
        - https://gradle.org/releases/

    - Oracle latest version Docker
    - Node.js
    - React

- Spring Boot 프로젝트 생성
    - 메뉴 보기 > 명령 팔레트(ctrl + shift + p)
        - Spring Initalzr: Create a Gradle project
        - Specify Spring Boot version: 3.2.6
        - Specify project language: Java
        - Input group ID: com.vinca (개인적으로 변경)
        - Input Artifact ID: spring01 (대문자 불가)
        - Specify package type: JAR
        - Specify Java version: 17
        - Choose dependencies: selected 0 dependencies
        - 폴더 선택 Dialog 팝업: 원하는 폴더 선택 후 generate
        - 오른쪽 하단 팝업에서 open
        - Git 설정 옵션, language Support for Java(TM) by Red Hat 설정 항상

    - TroubleShooting
        1. 프로젝트 생성이 진행되다 Gradle Connection 에러가 뜨면, 
            - Extensions > Gradle > Gradle for Java 제거 후 
            - vscode 재시작한 뒤 프로젝트 재생성
        2. Gradle 빌드 시 버전 에러로 빌드가 실패하면,
            - Gradle build tool 사이트에서 에러에 표시된 버젼의 Gradle bt 다운로드
            - 개발 컴퓨터에 설치
        3. ':compileJava' execution failed
            - Java JDK 잘못된 설치 x86(32bit) x64비트 혼용 설치
            - JDK 17버전 새로 설치 후 시스템 환경설정(환경변수)
            - vscode 재시작
            - build.gradle SpringBoot Framework 버전 다운그레이드 3.3.0 -> 3.1.5
            - Gradle 빌드
                1. 터미널에서 .\gradlew.bat 실행
                2. Gradle for Java > Tasks > Build > Build play icon(Run task) 실행
            - Spring Boot Dashboard
                - Apps > Spring01 run | debug 중 하나 클릭하여 서버 실행
           
    - 프로젝트 생성 후
        - build.gradle 확인
        - src/main/resources/application.properties(또는 .yaml) 확인
        - src/java/groupid/artifactid/java 소스파일 위치, 작업
        - src/main/resources/프로젝트 설정 파일, 웹 리소스 파일(css, js, html, jsp 등등)
        - Spring Boot DashBoard
            - Spring01Application.Java, run | debug 중 하나 클릭하여 서버 실행
            - 디버그로 실행해야 Hot code replace 동작

                <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp001.png" width="350">

        - 브라우저 설정 변경
            - 설정(ctrl + ,) > browser > Spring > DashBoard Open with 'Internal' -> 'external'로 변경
            - 크롬으로 기본 브라우저로 사용하는 거 추천

## 2, 3일차
- Oracle 도커로 설치
    - Docker는 Virtual Machine을 업그레이드한 시스템
    - 윈도우 서비스 내(services.msc) Oracle 관련 서비스 종료
    - Docker에서 Oracle 이미지 컨테이너를 다운로드 후 실행
    - Docker 설치 시 오류 Docker Desktop - WSL failed
        - Docker Desktop 종료
        - Windows 업데이트 최신판
        - https://github.com/microsoft/WSL/releases wsl2.x.x.x64.msi 다운로드 설치 한 뒤 
        - Docker Desktop 재실행
    - Oracle 최신판 설치
    ```shell
    > docker --version
    Docker version 26.1.1, ....
    > docker pull container-registry.oracle.com/database/free:latest
    lateset: ...
    ...: Download comlete
    Repository                                      TAG         IMAGE ID        CREATED         SIZE     
    container-registry.oracle.com/database/free     latest      7510f8869b04    7 weeks ago     8.7GB

    > docker run -d -p 1521:1521 --name oracle container-registry.oracle.com/database/free
    ....
    > docker logs oracle
    ....
    > docker exec -it oracle bash
    bash-4.4$
    ```

    - Oracle system 사용자 비번을 oralce로 설정
    ```shell
    bash-4.4$./setPassword.sh oracle
    ```
    
    - Oracle 접속확인
        - DBeaver 탐색기 > Create > Connection

- DataBase 설정
    - Oracle: 운영 시 사용할 DB (main)
    - Oracle PKNUSB / pknu_P@ss 로 생성
        - 콘솔
        ```shell
        > sqlplus system/password
        //서비스명 확인
        SQL> select name from v$database;
        //최신버전에서 사용자 생성 시 C## prefix 방지 쿼리
        SQL> ALTER SESSION SET "_ORACLE_SCRIPT"=true;
        //사용자 생성
        SQL> create user pknusb identified by "pknu_p@ss";
        //사용자 권한
        SQL> grant CONNECT, RESOURCE, CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW to pknusb;
        //사용자 계정 테이블 공간 설정, 공간 쿼터
        SQL> alter user pknusb default tablespace users;
        SQL> alter user pknusb quota unlimited on users;
        ```
    - H2 DB: Spring Boot에 손쉽게 사용가능한 Inmemory DB, Oracle, MySql, sqlServer와 쉽게 호환
    - MySql: 운영 시 사용할 DB (optional)

- Spring Boot + MyBatis
    - application name: spring02
    - Spring boot 3.3.x 에서는 Mybatis 없음 -> 3.2.6 선택
    - Dependency
        - Spring Boot DevTools
        - Lombok
        - Thymeleaf
        - Oracle Driver
        - MyBatis starter

    - Dependency 중 DB가 선택 시, application.properties에 DB 설정이 안되면 서버 실행 안됨
    - build.gradle 확인
    - application.properties 추가 작성
    ```properties
        ## 포트변경
        server.port=8091

        ## 로그 색상
        spring.output.ansi.enabled=always

        ## 수정사항이 있으면 서버 자동 재빌드 설정
        spring.devtools.livereload.enabled=true
        spring.devtools.restart.enabled=true

        ## 로그레벨 설정
        logging.level.org.springframework=info
        logging.level.org.zerok=debug

        ## Oracle 설정 
        spring.datasource.username=pknusb
        spring.datasource.password=pknu_p@ss
        ### docker X
        spring.datasource.url= jdbc:oracle:thin@localhost:1521:XE
        spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

        ## MyBatis 설정
        ## mapper 폴더 및에 여러가지 폴더가 내재, 확장자는 .xml이지만 파일명은 뭐든지
        mybatis.mapper-locations=classpath:mapper/**/*.xml
        mybatis.type-aliases-package=com.vinca.spring02.mapper
    ```
    
    - MyBatis 적용
        - Spring Boot 이전 resource/WEB-INF 위치에 root-context.xml에 DB, Mybatis 설정
        - Spring Boot 이후 appliction.properties 설정 + Config.java로 변경

    - 개발 시 순서
        0. application.properties > jdbc:oracle:thin:@localhost:1521:XE, thin 뒤에 : 이 삭제되어 있었음
        1. Database 테이블 생성
        2. MyBatis 설정 -> /config/MyBatisConfig.java
        3. 테이블과 일치하는 클래스 (domain, entity, DTO, VO(readonly), etc...) 생성
            - 테이블 컬럼에 있는 _은 Java 클래스는 사용안함
        4. DB에 데이터를 주고 받을 수 있는 클래스(DAO, **mapper**, repository) 생성
            - 쿼리를 클래스 내 작성가능, xml로 분리가능 
        5. **(Model)**분리 할 경우 /resources/mapper/클래스.xml 생성, 쿼리 입력
        6. 서비스 인터페이스(/service/*Service.java), 서비스를 구현한 클래스(/service/*ServiceImpl.java) 생성, 작성
        7. 사용자가 접근하는 컨트롤러 @RestController 클래스 생성 -> @Controller 변경 가능
        8. **(Controller)**경우에 따라 @SpringBootApplication 클래스에 SqlSessionFactory 빈을 생성, 매소드 작성
        9. **(View)**/resources/templates/Thymeleaf html 생성, 작성

## 4일차
- Spring Boot JPA + Oracle + Thymeleaf + React
    - JPA: DB 설계를 하지 않고 엔티티 클래스를 DB로 자동생성 해주는 기술, Query도 만들 필요 없음
    - H2: Oracle, MySQL, SQLServer 등과 달리 Inmemory DB, 스프링부트 실행되면 같이 실행되는 DB
        - 개발 편의성. 다른 DB로 전환 시 편리, 개발하는 동안 사용 추천
    - Thymeleaf: JSP의 단점인 복잡한 템플릿 형태 + 스파게티코드를 해소시켜주는 템플릿
    - Bootstrap: CSS, 웹디자인 최고
    - React: 프론트 엔드를 분리, 백엔드 서버와 프론트 엔드 서버 따로 관리
    - 소셜로그인: 구글, 카카오, 네이버 등 소셜로 로그인 가능

- Spring Boot JPA 프로젝트 생성
    - 명령 팔레트 시작, Spring Initializr: Create a Gradle Project
    - Spring Boot version: 3.2.6
    - Project language: Java
    - Group Id: com.vinca
    - Articfact id: backboard
    - Packaging type: Jar
    - Java version: 17
    - Dependency
        1. Spring Boot Devtools
        2. Lombok
        3. Spring Web
        4. Thymeleaf
        5. Ocacle DB Driver(나중에)
        6. H2 Database(나중에)
        7. Data JPA(나중에)
        8. spring03 폴더에 Generate into this folder
    
- Spring Boot JPA 프로젝트 개발 시작
    <!-- 설정 -->
    1. build.gradle 의존성 확인
    2. application.properties 기본설정 입력(포트번호, 로그 색상, 자동재빌드, 로그레벨)
    3. MVC 패턴에 맞춰서 각 기능별로 폴더를 생성(Controller, service, entity...)
    4. /controller/MainController.java 생성, 기본 기능 구현
    5. application.properties에 H2, JPA 설정 추가
    6. 웹 서버 실행 http://localhost:8080/h2-console DB 연결확인
    <!-- 개발 -->
    7. /entity/Board.java 생성
        - GenerationType 타입
            - AUTO: Spring Boot에서 자동으로 선택
            - IDENTITY: MySQL, SQLServer
            - SEQUENCE: Oracle(!)

        - Column 이름을 createDate로 만들면 DB에 컬럼명이 create_date로 생성
            - 컬럼명에 언더바(_)를 안 넣으려면 @Column(name="createDate")로 명시해서 사용
    8. /entity/Reply.java 생성
    9. 두 엔티티간 @OneToMany, @ManyToOne 설정
    10. 웹 서버 재시작 후 h2-console에서 테이블 생성 확인\
    11. /repository/BoardRepository.java 빈 인터페이스(JpaRepository 상속) 생성
    12. /repository/ReplyRepository.java 빈 인터페이스(JpaRepository 상속) 생성
    13. application.properties ddl-auto= create에서 update 변경
    14. /test/.../repository/BoardRepositoryTests.java 생성, 테스트 메서드 작성
    15. 테스트 시작 > 확인 > 웹서버 실행 > h2-console 확인

## 5일차
- Java Test 중 OpenJDK 64-Bit Server VM warning:... 발생
    - 설정 > java test config > setting.json 편집
    ```json
        "java.test.config": {
        "vmArgs": [
            "-Xshare:off"
        ]
    }
    ```
    - 저장 후 실행

- Spring Boot 프로젝트 오류처리
    - 빌드를 해도 제대로 결과가 반영 안되면
    - 깃허브 원격 리포지토리에 모두 커밋 후 로컬 리포지토리 삭제 후 새로 프로젝트 로드

- Spring Boot JPA 프로젝트 개발 계속
    1. jUnit 테스트로 CRUD 확인
    2. /service/BoardService.java 생성 후 getList 메서드 작성
    3. /controller/BoardController.java 생성 후 /board/list 실행할 수 있는 메서드 작성
    4. /templates/board/list.html 생성
        - Thymelaef 속성
            - th:if= "${board != null}"
            - th:each= "${boardList}"
            - th:text= "${boarrd.title}"
    5. /service/BoardService.java에 getBoard() 추가
    6. /controller/BoardCopntroller.java에 /board/detail{bno} 실행 메서드 작성
    7. /templates//board/detail.html 작성

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp003.png" width="730">

    8. templates/board/detail.html에 댓글 영역 추가
    9. service/ReplyService.java 생성, 댓글 저장 메서드 작성
    10. /controller/ReplyController.java 생성, /reply/create{bno} 포스트 매핑 메서드 작성
    
    11. Bootstrap 적용
        - 다운로드 후 프로젝트에 위치
        - CDN 링크를 추가
        - http://www.getbootstrap.com 다운로드 후 압축 해제
        - bootstrap.min.css, bootstrap.min.js templates/static에 위치
    12. /templates/board/list.html, detail.html 부트스트랩 적용

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp004.png" width="730">

## 6일차
- Spring Boot JPA 프로젝트 개발 계속
    1. build.gradle에 Thymeleaf 레이아웃 사용을 위한 디펜던시 추가
    2. /templates/layout.html Thymeleaf로 레이아웃 템플릿 생성
    3. list.html, detail.html 레이아웃 템플릿 적용
    4. /templates/layout.html에 Bootstrap CDN 적용
    5. /templates/list.html에 게시글 등록버튼 추가
    6. /templates/board/create.html 게시글 작성 페이지 생성
    7. /controller/BoardController.java create() getMapping 메서드 작성
    8. /service/BoardService.java setBoard() 작성
    9. /controller/BoardConteroller.java create() PostMapping 메서드 작성
    10. (문제) 아무 내용도 안 적어도 저장됨
    11. (설정) build.gradle 입력값 검증 Spring Boot Validation dependency 추가
    12. /validation/BoardForm.java 클래스 생성
    13. /controller/BoardController.java에 BoardForm을 전달(Get, PostMapping 둘다)
    14. create.html 입력항목 name, id를 th:field로 변경(ex. th:field=*{title})
    15. 댓글 등록에도 반영. ReplyForm, ReplyController, detail.html 작업(12 ~ 14번 내용과 유사)
    16. detail.html 경고영역 div는 create.htm에서 복사함
    17. 각 입력창에 공백을 넣었을 때 입력되는 문제: @NotEmpty는 스페이스를 허용 -> @NotBlank로 변경

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp005.png" width="730">

    18. 네비게이션바(navbar) 추가
    19. 테스트로 더미데이터 추가

## 7일차
- Spring Boot JPA 프로젝트 개발 계속
    0. 개념
    ```sql
    -- Oracle 전용(11g 이하는 이 쿼리 동작안함)
    select b1_0.bno,b1_0.content,b1_0.create_date,b1_0.title
    from board b1_0 offset 0    -- 0부터 시작해서 페이지 사이즈만큼 증가 
    rows fetch first 10 rows only   -- 페이지 사이즈
    ```
    1. 페이징(중요)
        - /repository/BoardRepository.java에 findAll(pageable) 인터페이스 메서드 작성
        - /service/BoardService.java에 getList(page) 메서드 작성
        - /controller/BoardController.java에 list() 메서드 수정
        - /templates/board/list.html에 boardList -> paging 변경
        - /templates/board/list.html 하단에 페이징 버튼 추가, Thymeleaf 기능 추가
        - /service/BoardService.java에 getList() 최신순 역정렬로 변경
        - /templates/board/list.html에 게시글 번호 수정하기

            <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp006.png" width="730">
        
    2. /templates/board/list.html td에 뱃지태그 추가

    3. H2 -> Oracle로 DB 변경
        - build.gradle, Oracle 디펜던시 추가
        - application.properties Oracle 관련 설정 추가, H2 설정 주석처리
        - 재시작
    
    4. 스프링 시큐리티(중요)
        - (설정) build.gradle 스프링 시큐리티 관련 디펜던시 추가
        - (설정) Gradle 재빌드, 서버 실행
        - user / 로그상 UUID 입력
        - /security/SecurityConfig.java 보안설정 파일 생성, 작성 -> 시큐리티를 다시 풀어주는 일
        - /entity/Member.java 생성
        - /repository/MemberRepository.java 인터페이스 생성
        - /service/MemberService.java 생성 setMember() 메서드 작성

## 8일차
- Spring Boot JPA 프로젝트 개발 계속
    1. 스프링 시큐리티 계속
        - /security/SecurityConfig.java에 BCryptPasswordEncoder를 빈으로 작업
        - /validation/MemberForm.java 생성
        - /controller/MemberController.java 생성
        - /entity/Member.java에 regDate 추가
        - /service/MemberService.java에 regDate() 부분 추가 작성
        - /templates/member/register.html
        - (설정) Member 테이블에 저장된 회원정보 확인
        - templates/layout.html에 회원가입 링크 추가
        - /controller/MemberController.java에 PostMapping register에 중복 회원가입 방지 추가
        - /security/MemberRole.java enum으로 ROLE_ADMIN, ROLE_USER 생성
        - /entity/Member.java role 변수 추가

    2. 로그인 기능
        - /security/SecurityConfig.java 에 login url 설정
        - /templates/layout.html 로그인 링크 수정
        - /templates/member/login.html
        - /repository/MemberRepository.java find* 메서드 추가
        - /controller/MemberController.java에 login Get 메서드 작성
        - /service/MemberSecurityService.java - 로그인은 post를 사용하지 않고, Spring Security가 지원하는 UserDetailsService 클래스 사용
        - /security/SecurityConfig.java 계정관리자 빈 추가
        -/templates/layout.html 로그인/로그아웃 토글 메뉴 추가

    3. 게시글 작성자 추가
        - /entity/Board.java, /entity/Reply.java에 작성자 변수(속성) 추가
        - /service/MemberService.java getMember() 메서드
        - (Tip) defaul Exception 으로 예외를 처리하면 메서드 뒤에 항상 throws Exception을 적어줘야 함
        - /common/NotFoundException.java 생성 -> throws Exception 쓰는데 반영
        - /service/ReplyService.java setReply() 사용자 추가
        - /controller/ReplyController.java 오류나는 setReply() 파라미터 수정
        - /service/BoardService.java setBoard
        - /controller/BoardController.java serBoard() 사용자 추가
        - /controller/작성하는 get, postMapping 메서드에 @PreAuthorize 어노테이션 추가
        - /config/SecurityConfig.java #PreAuthorize 동작하도록 설정 추가
        - /templates/board/detail.html 답변 textarea 로그인 전, 로그인 후로 구분

        - /templates/board/list.html table 태그에 작성자 컬럼 추가
        - /templates/board/detail.html 게시글 작성자, 댓글 작성자 표시 추가

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp006.png" width="730">

## 9일차
- Spring Boot JPA 프로젝트 개발 계속
    1. 수정, 삭제 기능
        - /entity/Board, Reply.java 수정일자 필드 추가
        - /templates/board/detail.html 수정, 삭제 버튼 추가
            - sec:authorize="isAuthenticated()" 가 없으면 500 에러발생
        - /controller/BoardController.java 에 modify() GET 메서드 작성
        - /templates/board/create.html form th:action을 삭제
            - create.html 생성, 수정할 때 모두 사용
            - get이 /board/create로 들어가면 post도 /board/create로 실행되고, /board/modify/{bno}로 페이지를 들어가면 post도 같은 url로 실행
        - service/BoardService.java 수정관련된 메서드 추가작성
        - /controller/BoardController.java 에 modify() POST 메서드 작성
            - html에는 BoardForm 객체값이 들어있음. 컨트롤러에 받아서 Board 객체 다시 만들어 서비스로 전달
        - /service/BoardService.java 삭제관련 메서드 추가
        - /controller/BoardController.java delete() Get 메서드 작성
        - /templates/board/detail.html 댓글 수정, 삭제 버튼 추가
        - /service/ReplyService.java 수정, 삭제관련 메서드 추가
        - /controller/ReplyController.java modify GET, POST 메서드, 삭제 GET 메서드 작성
        - /templates/reply/modify.html 생성, 작성
        - /templates/board/detail.html에 게시글, 댓글 수정 날짜 표시

    2. 앵커기능
        - 추가, 수정, 삭제 시 이전 자신의 위치로 되돌아가는 기능
        - /temlplates/board/detail.html 댓글마다 앵커링 추가
        - /controller/ReplyController.java modify Post 매핑에, return에 앵커링 추가
        - /service/ReplyService.java 생성 메서드 void -> Reply 변경
        - /controller/ReplyController.java create Post 메서드 변경
        - /controller/BoardController.java detail() 메서드 수정

    3. 검색기능
        - /service/BoardService.java search() 메서드 추가
        - /repository/BoardRepository.java findAll() 메서드 추가
        - /service/BoardService.java getList() 메서드를 추가 생성
        - /controller/BoardController.java list() 메서드 추가
        - /templates/board/list.html 검색창 추가, searchForm 폼영역 추가, **페이징 영역 수정, javascript 추가**

## 10일차
- Spring Boot JPA 프로젝트 개발 계속
    1. 검색 기능 -> JPA Query
        - @Query 어노테이션으로 직접 쿼리를 작성
        - 단순 쿼리가 아니라서 JpaRepository가 자동으로 만들어 줄 수 없을 때 사용
        - DB의 표준 쿼리와 차이가 있음(Java Entity와 일치)
        - /repository/BoardRepository.java findAll() 메서드 추가
        - JPA Query @Query("")에 작성
        - /service/BoardService.java getList() 수정

    2. 마크다운 적용
        - Wysiwyg 에디터: CKEditor,TinyMCE
        - SimpleMDE(https://simplemde.com/) github에 CDN 링크 복사, layout.html 링크 추가
        - create.html에 textarea id content를 simpleMDE로 변환하는 js 추가
        - detail.hrml textarea content simplemde js 추가

        - (설정) build.gradle 마크다운 디펜던시 추가
        - /common/CommonUtil.java 생성
        - /templates/board/detail.html 마크다운 뷰어 적용

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp009.png" width="730">

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp010.png" width="730">


    3. 카테고리 추가
        - /entity/Category.java 클래스 생성
        - /repository/CategoryRepository.java 인터페이스 생성
        - /service/CategoryService.java 생성
        - /entity/Board.java에 catrgory 속성을 추가
        - /service/BoardService.java getList(), searchBoard(), **setBoard()** 추가 생성
        - 카테고리를 자유게시판. 질문응답 게시판 분리
        - /templates/layout.html 사이드바 태그 추가기입
        - /Controller/BoardController.java CategoryService, GetMapping 메서드에 카테고리 매개변수를 추가
        - /templates/list.html 카테고리 변수 추가
        - /controller/BoardController.java create() GET, POST 메서드에 catgegory 추가
    
    4. 조회수 표시
        - /entity/Board.java 조회수 필드 추가
        - /service/BoardService.java 메서드 추가
        - /controller/BoardController.java detail() 메서드 추가
        - /templates/board/list.html 조회수 컬럼 추가


## 11일차
- Spring Boot JPA 프로젝트 개발 계속
    0. Restful URL 잘못된 부분 발견
        - /controller/MainController.java main() 메서드 URL 변경

    1. 조회수 표시
        - /entity/Board.java 조회수 필드 추가
        - /service/BoardService.java hitBoard() 메서드 추가
        - /controller/BoardController.java detail() 메서드 수정
        - /templates/board/list.html 조회수 컬럼 추가
        - DB를 Oracle -> H2
    
    2. (설정) AWS 사용
        - https://aws.amazon.com/ko/ 접속
            - 회원가입 및 로그인
        - 라이트세일(https://lightsail.aws.amazon.com/) 접속
            - 인스턴스 클릭 > 인스턴스 생성
            - 리전: 서울
            - 인스턴스 이미지 > Linux/Unix
            - 블루프린트 > 운영체제 전용 > Ubuntu 22.04 LTS
            - 인스턴스 플랜 > 듀얼 스택
            - 크기 선택
            - 인스턴스 확인 > 원하는 이름으로 변경 후 
            - 인스턴스 생성
            - 실행 중 확인 > 관리
            - 네트워킹 > 고정 IP 연결 > IP 명 입력 > 생성
            - IPv4 방화벽 > 규칙 추가 > 8080 추가
            - 계정 > SSH키 > 기본키 다운로드(*.pem)
        - PuTTY AWS 리눅스 서버 연결
            - https://www.putty.org/ 다운로드
            - PuTTYgen 실행 > Load > 기본키 선택 > Save private key 클릭 > .ppk로 저장
            - PuTTY 실행
                - Host Name: AWS 고정 IP 입력
                - Connection > SSH > Auth > Credential : Private key를 .ppk로 선택
                - Session > Saved Session명 입력 > Save
                - Open 후 콘솔 login as: ubuntu 입력
        - FileZilla (https://filezilla-project.org/download.php) 다운로드
            - 사이트 관리자 열기
                - 새 사이트
                - 프로토콜: SFTP
                - 호스트: 고정 IP 입력
                - 사용키: ubuntu
                - 키 파일: .ppl
                
        - 설정 변경
            ```shell
            > sudo ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime (한국 시간 변경)
            > hostname
            > sudo hostnamectl set-hostname vinca
            > sudo reboot (서버 재시작)

            > sudo apt-get update (전체 서버 패키지 업데이트)
            > java
            > sudo apt-get install openjdk-17-jdk
            > Do you want to continue? y
            > java -version
            openjdk version "17.0.11" 2024-04-16
            penJDK Runtime Environment (build 17.0.11+9-Ubuntu-122.04.1)
            OpenJDK 64-Bit Server VM (build 17.0.11+9-Ubuntu-122.04.1, mixed mode, sharing)
            ``` 

        - VSCode
            - Gradle for java > Tasks > build > bootJar
            - *-SNAPSHOT.jar 생성 확인

        - FileZilla
            - *.jar > AWS로 전송

        - PuTTY
            ```shell
            > ls
            ...
            > cd boostserver
            > ls
            backboard-1.0.1-SNAPSHOT.jar
            > java -jar backboard-1.0.1-SNAPSHOT.jar
            ```
            - sudo java -jar.. 로 실행하면 안됨

      - 스프링부트서버 백그라운드 실행 쉘 작성
         - > nano start.sh
            ```shell
            #!/bin/bash

            JAR=backboard-1.0.2-SNAPSHOT.jar
            LOG=/home/ubuntu/bootserver/backbord_log.log

            nohup java -jar $JAR > $LOG 2>&1 &
            ```
         - 파일권한 바꾸기(실행가능)
            ```shell
            > chmod +x start.sh
            ```

         - > nano stop.sh
            ```shell
            #!/bin/bash

            BB_PID=$(ps -ef | grep java | grep backboard | awk '{print $2}')

            if [ -z "$BB_PID" ];
            then
               echo "BACKBOARD is not running"
            else
               kill -9 $BB_PID
               echo "BACKBOARD terminated!"
            fi
            ```
         - 파일권한 바꾸기(실행가능)
            ```shell
            > chmod +x stop.sh
            ```
         
         - 서버실행

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp011.png" width="730">

## 12일차
- Spring Boot JPA 프로젝트 개발 계속
    1. 에러페이지 작업(404, 500, etc)
        - application.properties 에러페이지 관련 설정 추가
        - resource/static/img/bg_error.jpg 저장
        - resource/templates/404.html, 500.html, error.html 페이지 생성
        - /controller/CustomErrorController.java 생성
        
    2. 비밀번호 초기화
        - build.gradle 메일을 보내기위한 디펜던시 추가
        - application.properties 메일 설정(네이버) 입력
        - 네이버 메일 SMTP 설정 > 환경설정 > PO3/IMAP 설정

        <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp012.png" width="730">

        - /config/SecurityConfig.java CSRF 설정 변경
        - /service/MailService.java 생성
        - /restController/EmailController.java
        - 비밀번호 초기화 기능(메일 서버 세팅)
        - 비밀번호 초기화 화면으로 이동
        - 비밀번호, 비밀번호 확인 입력

## 13일차
- Spring Boot JPA 프로젝트 개발 계속
    0. 메일 작업 중 생긴 오류
        - 로그인하고 글 적으려면 500 에러 발생
        - CSRF 토큰때문에 발생하는 오류
        - /board/create.hmtl, /reply/modify.html 에 있는 csrf 관련 태그 주석처리

    1. 비밀번호 초기화 계속
        - /templates/member/loguin.html 비밀번호 초기화 버튼
        - /controller/MemberController.java reset() 메서드 추가
        - /templates/member/reset.html 생성 -> reset.html 가져와서 수정
        - /controller/MailController.java 생성, /mail/reset-mail GET mapping 메서드 생성
        - /service/MemberService.java 에 메일주소로 검색하는 getMemberByEmail() 메서드 추가 
        - /service/MailService.java 에 메일전송 메서드 생성, 수정
            - UUID 생성해서 메일로 전송하는 기능 추가

            <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp014.png" width="730">

        - /entity/Reset.java 생성
        - /repository/ResetRepository.java 인터페이스 생성, findByUuid() 추가
        - /service/ResetService.java 생성
        - /service/MailService.java에 ResetService 객체 생성, 메일 전송 후 setReset() 사용
        - /controller/MemberController.java. /member/reset-password GET 메서드 작성
        - /templates/member/newpassword.html 생성
        - /controller/MemberController.java, /member/reset-password POST 메서드 작성
        - /service/MemberService.java 에 setMember() 메서드 추가

            <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/sp013.png" width="730">
        
## 14일차
- 리액트 개요
    - 서버사이드 -> 백엔드
    - 클라이언트 -> 프론트엔드
    - 프론트엔드 : html + css + js(html, jsp, aspx, php, ...)
    - js만 가지고 프론트엔드를 만들어보자 -> react
    - css는 있어야 함
    - 페이스북이 자기 웹페이지 프론트를 좀 더 개선해보고자 개발 시작
    - 기본적으로 SPA(single page application)을 목적으로 
    - node.js 서버사이드 js를 사용해서 서버를 동작
    - 패키지 매니저 종류: nmp, chocolatey, yarn, ... (필요한 것만 공부)

- 리액트 개발환경 설치순서
    1. node.js 설치
        - https://nodejs.org, Download Node.js 
        - 설치 후 node --version 확인(현재 v20.15.0)
    2. 리액트 페키지 설치
        - > npm uninstall -g create-react-app
        - > npm install -g create-react-app
    3. 리액트 프로젝트 초기화
        - VS Code 터미널 오픈
        - >npx create-react-app basic-app
    4. 리액트 실행
        - 콘솔에서 위에서 만든 프로젝트앱 이름까지 진입 basic-app
        - > npm start
        - node가 3000 포트 웹서버 실행
        - 웹서버가 실행된 상태에서 개발하는 것이 가장 좋음
        - index.html(jsp, php) 가 맨 처음 화면, App.js가 메인 개발 부분

- 리액트 기본구조 및 개발방법
    1. 깃헙 .gitignore에 react(node) 관련 설정 내용 추가
    2. 깃험에 .gitignore 먼저 커밋
    3. src/App.css, App.js, index.js 파일만
    4. js 위주로 개발
    5. App.js부터 개발 시작

- 리액트 기초 공부
    1. html의 태그처럼 개발자가 새로운 요소(객체)를 생성할 수 있다.
        ```jsx
        function CustomButton(){    // CustomButton 객체 생성
            return (
            <button>My Button</button>
            );
        }
        ```
    2. /component/CutomButton.js 생성, 위의 소스 옮김
        - 같은 파일이 아닌 곳에 객체를 만들면, 가져와 쓰기위해서는 export default 객체이름; 필수
    
    3. React 문법은 JSX. 일반 js와 조금 차이가 있음
        - className은 JSX에만 존재
        - HTML에 있던 class는 JSX에서 className으로 변경
        - 인라인으로 style 쓸 때 CSS 명칭이 다름
        - 대신, *.CSS 파일로 작업할 때는 기존과 동일
        - JSX 문법에는 모든 요소는 상위 태그 하나에 속해야 함
        - https://transform.tools/html-to-jsx (html -> jsx)
    
    4. 데이터 화면에 표시
        - 변수 생성 시 const 많이 사용
        - 변수 사용시 중괄호{} 사이에 입력
        - CSS를 *.css 파일에 작성할 때는 html에서 사용할 때와 동일
            - ex. border-radius: 50%;
        - JSX에서 사용할 때는 변경
            - ex. borderRadius: '50%'
        - 리액트에서 css를 쓸 때는 *.css 파일로 작업할 것

    5. 조건부 렌더링
        ```jsx
        function CustomButton(){

            let isLoggedIn = true;
            let content;

            if (isLoggedIn) {
                content = <button>Log Out</button>
            }else {
                content = <button>Log In</button>
            }

            return (
                <>
                    {content}
                </>
            );
        }
        ```
        - 또는
        ```jsx
        {
            isLoggedIn ? (
                <button>Log Out</button>
            ) : (
                <button>Log In</button>
            )
        }
        ```

    6. 목록 표시
        - for, map() 함수를 많이 사용
        - map()을 쓰면 for문보다 짧게 구현가능
        - 각 child 요소마다 key 속성이 필요(없으면 warning 발생)

            <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/react001.png" width="730">

    7. 이벤트 핸들링
        - form + onSubmit, tag + onClick
        - 이벤트 파라미터 전달
        - 파라미터가 필요해서 함수 뒤에 ()를 쓰면 이벤트 순서에 따라 refresh 후 자동실행 됨 
        - onClick={() => function()} 같은 람다식으로 변경
    
    8. 컴포넌트 간 데이터 전달
        - props 속성
        - props.속성이름.keyname

            <img src="https://raw.githubusercontent.com/vinca0224/Springboot-2024/main/images/react002.png" width="730">

    9. 화면 업데이트
        - userState : 앱 화면의 상태를 기억하고 사용하기 위한 객체
        - import {userState} from 'react'; 필수
        - const [count, setCount] = useState(0);

    10. Hooks
        - use로 시작하는 함수를 Hooks 라고 호칭. State, Effect 외 잘 안씀
        - useState: React 컴포넌트 상태를 추가, 보관
        - useEffect: 컴포넌트에서 사이드이펙트 수행할 때
        - 기타: useContext, useReducer, useCallback, useRef, ...

- 리액트 추가 내용
    1. 리액트 관련 프레임워크
        - Next.js - 풀스택 React 프레임워크
        - Gatsby - 정적사이트 React 프레임워크
        - React Native - Androidm iOs 멀티플랫폼 모바일 프레임워크

    2. npm으로 추가 라이브러리 설치
        - > npm install react react-dom

    3. VS Code 확장
        - ES7 + React/Redux/React-Native snippet 설치
        - Simple React Snippets
        - Import Cost: 라이브러리 비용 계산
        - VSCode React Refactor
    
    4. 리액트 개발자 도구
        - 크롬, 엣지 브라우저 별로 따로 존재
        - React Developer Toole 설치

- Spring Boot React 연동 프로젝트 개발 계속
    1. 리액트 프로젝트 생성
        - 터미널 /spring03으로 이동
        - /spring03/frontboard 폴더 생성

## 15일차

## 계속
- Spring Boot JPA 프로젝트
    - 파일 업로드 - AWS S3 체크
    - 로그인한 사용자 헤더에 표시

    2. backboard(Rest API)

    3. 구글 로그인
        - http:/console.cloud.google.com/ 구글 클라우드 콘솔
        - 프로젝트 생성
        - OAuth 동의화면 설정
        - 개발 계속

    - 리액트 적용
    - 리액트로 프론트엔드 설정
    - thymeleaf - 리액트로 변경
    - Spring boot RestAPI 작업
    - 8080 -> 80 서버
    - http -> https