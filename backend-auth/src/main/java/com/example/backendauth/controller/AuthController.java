package com.example.backendauth.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.backendauth.dto.RegisterRequest;
import com.example.backendauth.dto.LoginRequest;
import com.example.backendauth.dto.AuthResponse;
import com.example.backendauth.service.UserService;
import com.example.backendauth.security.JwtUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  // Cho phép mọi nguồn gốc, có thể thay thế cho frontend của bạn

public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);  // Đảm bảo đăng ký người dùng
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Xác thực người dùng
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);

            // Lấy role từ User entity (được trả về từ UserDetails)
            String role = userDetails.getAuthorities().iterator().next().getAuthority();  // Lấy role từ authorities
            if (role.startsWith("ROLE_")) role = role.substring(5);  // Loại bỏ "ROLE_" nếu có

            // Trả về token và role trong response
            return ResponseEntity.ok(new AuthResponse(token, role));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Sai tài khoản hoặc mật khẩu");  // Trả về thông báo lỗi khi đăng nhập thất bại
        }
    }
}
