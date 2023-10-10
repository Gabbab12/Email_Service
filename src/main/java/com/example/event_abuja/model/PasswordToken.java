package com.example.event_abuja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Boolean isValid = true;
    private String email;
    private LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);

    public PasswordToken() {
        this.token = String.valueOf(UUID.randomUUID());
    }

}
