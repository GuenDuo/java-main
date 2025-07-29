# Chọn image của Node.js hoặc Nginx tùy thuộc vào cách bạn muốn triển khai
FROM nginx:alpine

# Sao chép các tệp từ thư mục frontend vào thư mục mặc định của Nginx
COPY ./frontend /usr/share/nginx/html

# Mở cổng 80 để Nginx có thể phục vụ các tệp tĩnh
EXPOSE 80
