package com.veinsmoke.webidbackend.controller;


import com.veinsmoke.webidbackend.dto.LoginRequest;
import com.veinsmoke.webidbackend.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private ResponseEntity<HashMap<String, String>> login(LoginRequest loginRequest, String userType) {

        String emailAndType = loginRequest.email() + ":" + userType;

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(emailAndType, loginRequest.password());
        authenticationManager.authenticate(token);

        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        HashMap<String, Object> claim = new HashMap<>();
        claim.put("userType", userType);

        String jwtToken = jwtUtil.generateToken(claim, user);
        HashMap<String, String> response = new HashMap<>();
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, String>> loginClient(@Valid @RequestBody LoginRequest loginRequest) {
        return login(loginRequest, "client");
    }

    @PostMapping("/admin")
    public ResponseEntity<HashMap<String, String>> loginAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        return login(loginRequest, "admin");
    }


}
