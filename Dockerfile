# 1단계: 빌드 스테이지 (자바 17 메이븐 이미지 사용)
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# 라이브러리 캐싱을 위해 pom.xml 먼저 복사 및 다운로드
COPY pom.xml .
RUN mvn dependency:go-offline

# 소스 코드 복사 및 빌드
COPY src ./src
RUN mvn clean package -DskipTests

# 2단계: 실행 스테이지 (가벼운 자바 17 런타임 사용)
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 쏙 빼오기
COPY --from=build /app/target/*.jar app.jar

# 서버 실행 포트 설정
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
