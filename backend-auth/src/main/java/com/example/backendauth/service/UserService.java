package com.example.backendauth.service;

import com.example.backendauth.repository.UserRepository;
import com.example.backendauth.dto.RegisterRequest;
import com.example.backendauth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backendauth.security.JwtUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public void addUser(User user) {
        // Logic để thêm user vào cơ sở dữ liệu hoặc thực hiện các hành động cần thiết
    }

    public String register(RegisterRequest request) {
        // Kiểm tra nếu người dùng đã tồn tại
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Tiến hành tạo mới người dùng
        String role = request.getRole() != null ? request.getRole().toUpperCase() : "USER";

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);

        // Trả về JWT token cho người dùng
        return jwtUtils.generateToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm người dùng từ cơ sở dữ liệu
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Lấy authorities từ role của user và trả về UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())) // Lấy role và chuyển thành authority
        );
    }
}
