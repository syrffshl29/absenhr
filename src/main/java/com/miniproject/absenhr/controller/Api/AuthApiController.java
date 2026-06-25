package com.miniproject.absenhr.controller.Api;

import com.miniproject.absenhr.core.dto.request.LoginRequest;
import com.miniproject.absenhr.model.Users;
import com.miniproject.absenhr.repository.UserRepository;
import com.miniproject.absenhr.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthApiController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthApiController(AuthenticationManager authenticationManager,
                             UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        // 🔐 validasi username & password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // 🔥 generate JWT
        return JwtUtil.generateToken(user.getUsername(),user.getRole());
    }
}
