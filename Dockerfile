# Bắt đầu từ image nginx
FROM nginx:latest

# Sao chép các file HTML và tài nguyên vào container
COPY ./ /usr/share/nginx/html/

# Mở cổng 80 để truy cập
EXPOSE 80

# Chạy nginx trong chế độ foreground
CMD ["nginx", "-g", "daemon off;"]
