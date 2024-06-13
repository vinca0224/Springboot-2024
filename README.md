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

## 2일차
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
        ```
    - H2 DB: Spring Boot에 손쉽게 사용가능한 Inmemory DB, Oracle, MySql, sqlServer와 쉽게 호환
    - MySql: 운영 시 사용할 DB (optional)
