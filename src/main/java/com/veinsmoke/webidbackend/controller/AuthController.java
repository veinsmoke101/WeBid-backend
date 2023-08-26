package com.veinsmoke.webidbackend.controller;


import com.veinsmoke.webidbackend.dto.LoginRequest;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.ClientRepository;
import com.veinsmoke.webidbackend.dto.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;

    private ResponseEntity<HashMap<String, String>> login(LoginRequest loginRequest, String userType, HttpServletResponse httpServletResponse) {

        String emailAndType = loginRequest.email() + ":" + userType;

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(emailAndType, loginRequest.password());
        authenticationManager.authenticate(token);

        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        Client client = clientRepository.findByEmail(loginRequest.email()).get();

        /* generate ably token

        String ablyJwt = jwtUtil.createAblyToken(
                Collections.emptyMap(),
                user.getUsername(),
                TimeUnit.MINUTES.toMinutes(60),
                keySecret,
                keyId
        );
         generate ably token */

        Cookie cookie = new Cookie("email", loginRequest.email());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        httpServletResponse.addCookie(cookie);


        HashMap<String, Object> claim = new HashMap<>();
        claim.put("userType", userType);
        // add ably token to jwt claims
        // claim.put("x-ably-token",ablyJwt);

        String jwtToken = jwtUtil.generateToken(claim, user);
        HashMap<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("profileImg", client.getProfileImg());


        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Credentials", "true" );

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, String>> loginClient(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return login(loginRequest, "client", response);
    }

    @PostMapping("/admin")
    public ResponseEntity<HashMap<String, String>> loginAdmin(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return login(loginRequest, "admin", response);
    }


}
