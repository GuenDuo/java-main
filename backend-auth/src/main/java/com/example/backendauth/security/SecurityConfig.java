package com.example.backendauth.security;

import com.example.backendauth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Configuration

public class SecurityConfig {

    @Autowired
    @Lazy
    private JwtFilter jwtFilter;  // Giả sử bạn có JwtFilter để xử lý JWT token

    @Autowired
    private UserService userService;  // Giả sử bạn có UserService để xác thực người dùng

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCryptPasswordEncoder
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // Sử dụng UserService để tìm kiếm user
        authProvider.setPasswordEncoder(passwordEncoder()); // Sử dụng PasswordEncoder để mã hóa mật khẩu
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(authProvider()); // Sử dụng authProvider
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu dùng JWT thì không cần CSRF)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Cấu hình CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Cho phép mọi người truy cập vào các endpoint auth (login, register)
                        .requestMatchers("/login", "/register").permitAll()  // Cho phép truy cập vào login, register
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Endpoint /admin cần quyền ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // Endpoint /user cho cả USER và ADMIN
                        .requestMatchers("/api/projects").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/projects").hasRole("ADMIN")
                        .requestMatchers("/api/admin/employee/**").permitAll()
                        .requestMatchers("/api/admin/list-employee").permitAll()
                        .anyRequest().authenticated()  // Các yêu cầu còn lại phải được xác thực
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session (cho JWT)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Thêm JwtFilter để xác thực JWT token trước khi vào UsernamePasswordAuthenticationFilter

        return http.build();  // Trả về cấu hình hoàn chỉnh
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // Cho phép tất cả các nguồn (có thể thay đổi theo yêu cầu)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Cho phép các phương thức HTTP
        config.setAllowedHeaders(List.of("*")); // Cho phép tất cả các headers
        config.setAllowCredentials(true); // Cho phép cookie và các thông tin xác thực khác

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Áp dụng CORS cho tất cả các endpoint
        return source;
    }
}

