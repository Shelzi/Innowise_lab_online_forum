package com.innowise.onlineforum.onlineforum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "onlineforum")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    //private UserRole userRole;
    private boolean isBanned;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserCredentials credentials;
}
