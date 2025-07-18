# Review Service (리뷰 서비스)

## 💡 프로젝트 개요

`Review Service`는 사용자들이 특정 게시글에 대한 리뷰를 작성하고, 다른 사용자의 리뷰를 조회하며, 자신의 리뷰를 관리할 수 있도록 지원하는 마이크로서비스입니다. 이 서비스는 리뷰 생성, 수정, 삭제 및 조회 기능을 제공하며, 회원별 리뷰 평점 집계 기능도 포함합니다.

## 🛠️ 기술 스택

*   **언어**: Java 17
*   **프레임워크**: Spring Boot 3.5.0
*   **데이터베이스**:
    *   **MongoDB**: 리뷰 정보 저장 (NoSQL)
    *   **MySQL**: 회원 리뷰 집계 정보 저장 (RDBMS)
*   **메시지 브로커**: Apache Kafka
*   **서비스 디스커버리**: Spring Cloud Netflix Eureka Client
*   **API 통신**: Spring Cloud OpenFeign
*   **API 문서**: Springdoc OpenAPI (Swagger UI)
*   **빌드 도구**: Gradle

## ✨ 주요 기능

### 1. 리뷰 관리

*   **리뷰 생성**: 특정 게시글에 대한 리뷰를 작성합니다. 평점, 내용, 이미지 첨부 기능을 지원합니다.
*   **리뷰 내용 수정**: 작성된 리뷰의 내용을 수정합니다.
*   **리뷰 이미지 수정**: 작성된 리뷰에 첨부된 이미지를 수정합니다. (전체 교체 방식)
*   **리뷰 삭제**: 작성된 리뷰를 소프트 딜리트 방식으로 삭제합니다.
*   **리뷰 단건 조회**: 리뷰 ID를 통해 특정 리뷰의 상세 정보를 조회합니다.

### 2. 회원 리뷰 집계

*   **회원 리뷰 평균 평점 조회**: 특정 회원이 받은 모든 리뷰의 평균 평점을 조회합니다. (MySQL에 저장된 집계 데이터 활용)
*   **Kafka 연동**:
    *   `review-created` 토픽을 통해 새로운 리뷰 생성 이벤트를 수신하여 회원 리뷰 평점을 집계합니다.
    *   `member-created` 토픽을 통해 새로운 회원 생성 이벤트를 수신하여 회원 리뷰 정보를 초기화합니다.

## 📚 API 문서

서비스가 실행되면 다음 URL에서 Swagger UI를 통해 API 문서를 확인할 수 있습니다.

`http://localhost:{port}/v3/api-docs`

## 🚀 실행 방법

1.  **환경 설정**:
    *   Java 17 설치
    *   Gradle 설치
    *   MongoDB 및 MySQL 데이터베이스 설정
    *   Kafka 브로커 설정
    *   Eureka Server 실행 (서비스 디스커버리를 위해)

2.  **프로젝트 클론**:
    ```bash
    git clone <repository-url>
    cd review-service
    ```

3.  **의존성 설치**:
    ```bash
    ./gradlew build
    ```

4.  **애플리케이션 실행**:
    ```bash
    ./gradlew bootRun
    ```
    또는 IDE (IntelliJ IDEA 등)에서 `ReviewServiceApplication.java`를 실행합니다.

5.  **프로파일 설정**:
    `src/main/resources/application.yml` 파일에서 `spring.profiles.active`를 `dev` 또는 `prod`로 설정하여 개발 또는 운영 환경에 맞게 실행할 수 있습니다.

