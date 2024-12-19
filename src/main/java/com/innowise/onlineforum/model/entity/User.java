package com.innowise.onlineforum.model.entity;

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
    private Long id;

    @Basic(fetch = FetchType.EAGER)
    private String username;

    private String email;

    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole = UserRole.GUEST;

    @Builder.Default
    private boolean isBanned = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserCredentials credentials;
}
