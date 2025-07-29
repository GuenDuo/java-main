# Sử dụng image Nginx chính thức làm base image
FROM nginx:latest

# Sao chép file login.html vào thư mục mặc định của Nginx
COPY ./login.html /usr/share/nginx/html/index.html

# Mở port 80 cho Nginx
EXPOSE 80

# Chạy Nginx
CMD ["nginx", "-g", "daemon off;"]
