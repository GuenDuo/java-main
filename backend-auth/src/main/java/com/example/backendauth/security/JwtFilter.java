package com.example.backendauth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.backendauth.service.UserService;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/login",   // Đăng nhập
            "/api/auth/register", // Đăng ký
            "/api/auth/me"        // Thông tin người dùng hiện tại
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Nếu đường dẫn là một trong các endpoint không yêu cầu xác thực JWT, bỏ qua filter
        if (EXCLUDED_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Nếu không có header Authorization hoặc không bắt đầu bằng "Bearer ", bỏ qua filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Trích xuất JWT từ header Authorization
        jwt = authHeader.substring(7);  // Lấy JWT sau "Bearer "
        username = jwtUtils.extractUsername(jwt);  // Trích xuất username từ JWT

        // Kiểm tra nếu username có giá trị và chưa được xác thực trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Lấy thông tin người dùng từ UserService
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Kiểm tra tính hợp lệ của JWT
            if (jwtUtils.isTokenValid(jwt, userDetails)) {
                // Nếu token hợp lệ, tạo đối tượng Authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Đặt chi tiết vào token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Tiếp tục với chuỗi các filter còn lại
        filterChain.doFilter(request, response);
    }
}

