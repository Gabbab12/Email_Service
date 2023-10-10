package com.example.event_abuja.controller;

import com.example.event_abuja.model.Users;
import com.example.event_abuja.serviceImpl.UserServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    @Autowired
    private final UserServiceImpl userService;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 5000;

    @PostMapping("register")
    public ResponseEntity<String> signUp(@RequestBody Users users) {
        ResponseEntity<String> register = userService.signUp(users);

        if (register.getStatusCode() != HttpStatus.CREATED) {
            return register;
        }
        boolean emailSent = false;
        int retryAttempts = 0;

        while (!emailSent && retryAttempts < MAX_RETRY_ATTEMPTS) {
            try {
                userService.email(users.getEmail());
                emailSent = true;
            } catch (MessagingException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
                retryAttempts++;
            }
        }
        if (emailSent) {
            return ResponseEntity.ok("Registration successful. Please check your email for further instructions.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send registration email after multiple retries.");
        }
    }
}
