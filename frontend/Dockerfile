# Bước 1: Tạo image Nginx
FROM nginx:alpine

# Bước 2: Sao chép các file frontend vào thư mục của Nginx
COPY . /usr/share/nginx/html

# Bước 3: Mở port 80 để phục vụ các request
EXPOSE 80

# Bước 4: Chạy Nginx để phục vụ các file tĩnh
CMD ["nginx", "-g", "daemon off;"]
