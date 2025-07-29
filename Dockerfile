# Chọn một base image cho Nginx
FROM nginx:alpine

# Sao chép toàn bộ mã nguồn của bạn vào container
COPY . /usr/share/nginx/html

# Cấu hình Nginx để phục vụ ứng dụng
EXPOSE 80

# Khởi động Nginx khi container được chạy
CMD ["nginx", "-g", "daemon off;"]
