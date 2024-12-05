package com.innowise.onlineforum.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;

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
    @Builder.Default
    private boolean isBanned = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserCredentials credentials;
}
