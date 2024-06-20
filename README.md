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
    select b1_0.bno,b1_0.content,b1_0.create_date,b1_0.title \
    from board b1_0 offset 0    -- 0부터 시작해서 페이지 사이즈만큼 증가 
    rows fetch first 10 rows only   -- 페이지 사이즈
    ```
    1. 페이징
        - /repository/BoardRepository.java에 findAll(pageable) 인터페이스 메서드 작성
        - /service/BoardService.java에 getList(page) 메서드 작성
        - /controller/BoardController.java에 list() 메서드 수정
        - /templates/board/list.html에 boardList -> paging 변경
        - /templates/board/list.html 하단에 페이징 버튼 추가, Thymeleaf 기능 추가
        - /service/BoardService.java에 getList() 최신순 역정렬로 변경
    2. 