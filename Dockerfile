# 1️⃣ Maven 빌드용 이미지 (builder)
FROM eclipse-temurin:21-jdk AS builder

# 작업 디렉토리
WORKDIR /app

# 소스 복사
COPY . .

# Maven Wrapper 실행 권한 부여 및 빌드
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 2️⃣ 실행 전용 이미지
FROM eclipse-temurin:17-jdk

WORKDIR /app

# 빌드 단계에서 생성된 jar 파일 복사
COPY --from=builder /app/target/*.jar app.jar

# 3️⃣ 포트 개방
EXPOSE 8080

# 4️⃣ 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
