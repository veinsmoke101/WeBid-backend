package com.veinsmoke.webidbackend.controller;

import io.ably.lib.rest.AblyRest;
import io.ably.lib.rest.Auth;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.Capability;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AblyController {

    private final AblyRest ablyRest;

//  private final JwtUtil jwtUtil;

    @GetMapping
    public String auth(HttpServletResponse response, @RequestParam String email) throws AblyException {

        if(email == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"");
        }

        Auth.TokenParams tokenParams = getTokenParams(email);
        return createTokenRequest(tokenParams, response);
    }

    public Auth.TokenParams getTokenParams(String username) throws AblyException {
        Auth.TokenParams tokenParams = new Auth.TokenParams();
        tokenParams.capability = Capability.c14n("{ '*': ['subscribe'] }");
        if (username != null) {
            tokenParams.clientId = username;
        }
        return tokenParams;
    }

    public String createTokenRequest(Auth.TokenParams tokenParams, HttpServletResponse response) {
        Auth.TokenRequest tokenRequest;
        try {
            tokenRequest = ablyRest.auth.createTokenRequest(tokenParams, null);
            response.setHeader("Content-Type", "application/json");
            return tokenRequest.asJson();
        } catch (AblyException e) {
            response.setStatus(500);
            return "Error requesting token: " + e.getMessage();
        }
    }

//    @GetMapping
//    public String ablyJwt() {
//        String keyId = "97xIFg.jLXgJA";
//        String keySecret = "isxhklGx2FUNrLajXy_xkNF7ZECOhUL7mO6vVcxQKds";
//
//        String  username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return jwtUtil.createAblyToken(
//                Collections.emptyMap(),
//                username,
//                TimeUnit.MINUTES.toMinutes(60),
//                keySecret,
//                keyId
//        );
//    }
}
