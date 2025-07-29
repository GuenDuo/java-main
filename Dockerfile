# Sử dụng image Java chính thức làm nền tảng
FROM openjdk:11-jre-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép tệp JAR vào container
COPY target/my-app.jar /app/my-app.jar

# Cấu hình cổng mà ứng dụng sẽ lắng nghe
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "my-app.jar"]
