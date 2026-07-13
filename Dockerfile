# 1단계: 빌드 스테이지 (Maven과 Java 11 환경에서 빌드 진행)
FROM maven:3.8.6-openjdk-11 AS build
WORKDIR /app

# 라이브러리 설치를 위해 pom.xml 먼저 복사 및 빌드 소스 복사
COPY pom.xml .
COPY src ./src

# 테스트를 제외하고 패키징(jar 파일 생성) 진행
RUN mvn clean package -DskipTests

# 2단계: 실행 스테이지 (가벼운 자바 런타임만 사용해서 용량 최적화)
FROM openjdk:11-jre-slim
WORKDIR /app

# 1단계 빌드 결과물인 jar 파일을 실행 스테이지로 가져오기
COPY --from=build /app/target/*.jar app.jar

# Cloud Run에서 동적으로 주입하는 포트 설정 바인딩
ENV PORT 8080
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]