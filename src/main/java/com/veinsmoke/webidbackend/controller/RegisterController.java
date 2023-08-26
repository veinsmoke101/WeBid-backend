package com.veinsmoke.webidbackend.controller;


import com.veinsmoke.webidbackend.dto.RegisterRequest;
import com.veinsmoke.webidbackend.exception.EmailAlreadyExistException;
import com.veinsmoke.webidbackend.mapper.ClientMapper;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.service.ClientService;
import com.veinsmoke.webidbackend.dto.util.EmailSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RegisterController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    private static final String MESSAGE = "message";


    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        clientService.findByEmail(registerRequest.email()).ifPresent(client -> {
            throw new EmailAlreadyExistException("Email already exists");
        });

        Client client = clientMapper.registerRequestToClient(registerRequest);

        // prepare verification code
        String verificationCode = UUID.randomUUID().toString();
        client.setVerificationCode(verificationCode);
        client.setVerificationTokenExpireAt(LocalDateTime.now().plusMinutes(15));
        client.setPassword(passwordEncoder.encode(client.getPassword()));


        // send verification code via email
        String mailBody = "Thank you for registering to WeBid. Please click on the link below to verify your email address and complete the registration process:" +
                "http://localhost:8080/verify?code=" + verificationCode;
        emailSender.sendSimpleMessage(client.getEmail(), "WeBid account verification", mailBody);

        // save client
        clientService.saveClient(client);

        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, "A verification code has been sent to your email address");
        responseBody.put("verificationCode", verificationCode);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/verify")
    public ResponseEntity<HashMap<String, String>> verify(@RequestParam String code, @RequestParam String email) {
        HashMap<String, String> responseBody = new HashMap<>();
        Optional<Client> clientOptional = clientService.findByVerificationCodeAndEmail(code, email);



        Client client;
        if(clientOptional.isEmpty()){
            responseBody.put(MESSAGE, "Invalid email or verification code");
            return ResponseEntity.badRequest().body(responseBody);
        }

        client = clientOptional.get();

        if (client.getVerified().equals(true)) {
            responseBody.put(MESSAGE, "Your email has already been verified");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        if (client.getVerificationTokenExpireAt().isBefore(LocalDateTime.now())) {
            responseBody.put(MESSAGE, "Verification code has expired");
            return ResponseEntity.badRequest().body(responseBody);
        }

        client.setVerified(true);
        clientService.saveClient(client);

        responseBody.put(MESSAGE, "Your email has been verified");
        return ResponseEntity.ok(responseBody);
    }

}
