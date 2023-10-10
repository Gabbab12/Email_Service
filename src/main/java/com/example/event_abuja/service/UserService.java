package com.example.event_abuja.service;


import com.example.event_abuja.model.Users;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> signUp(Users user);

    Users email(String email) throws MessagingException;
}
