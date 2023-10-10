package com.example.event_abuja.serviceImpl;


import com.example.event_abuja.model.Users;
import com.example.event_abuja.repositories.UserRepository;
import com.example.event_abuja.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;private final EmailServiceImpl mailService;
    @Override
    public ResponseEntity<String> signUp(Users user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exist, please Login");
        } else{
            Users users = new Users();
            users.setName(user.getName());
            users.setEmail(user.getEmail());
            users.setPassword(user.getPassword());
            userRepository.save(users);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User registration successful.");

    }
    @Override
    public Users email(String email) throws MessagingException {
        Users users = new Users();
        users.setEmail(email);
        mailService.sendEmail(email, EmailServiceImpl.CONTENT, EmailServiceImpl.SUBJECT);
        return users;
    }
}
