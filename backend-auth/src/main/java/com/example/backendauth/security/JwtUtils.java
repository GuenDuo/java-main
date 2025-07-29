package com.example.backendauth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Tạo SecretKey

    // Trích xuất tên người dùng (subject) từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Trích xuất ngày hết hạn của token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Trích xuất claim từ token bằng cách sử dụng Function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);  // Lấy tất cả claims từ token
        return claimsResolver.apply(claims);  // Trả về giá trị của claim
    }

    // Trích xuất tất cả claims từ token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Sử dụng SECRET_KEY để phân tích token
                .parseClaimsJws(token)  // Phân tích token JWT và lấy claims
                .getBody();
    }

    // Kiểm tra xem token đã hết hạn chưa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Kiểm tra nếu ngày hết hạn đã qua
    }

    // Kiểm tra tính hợp lệ của token, bao gồm username và thời gian hết hạn
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);  // Trích xuất username từ token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));  // Kiểm tra token hợp lệ
    }

    // Tạo một JWT từ thông tin người dùng (UserDetails)
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Đặt username làm subject
                .claim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)  // Lấy quyền hạn của người dùng (roles)
                        .collect(Collectors.toList()))  // Chuyển thành list
                .setIssuedAt(new Date())  // Đặt ngày cấp token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Đặt thời gian hết hạn (10 giờ)
                .signWith(SECRET_KEY)  // Ký với SECRET_KEY
                .compact();  // Tạo token
    }

    // Trích xuất role từ token
    public String extractRole(String token) {
        List<String> roles = extractClaim(token, claims -> claims.get("role", List.class));
        return roles != null && !roles.isEmpty() ? roles.get(0) : null;
    }
}
