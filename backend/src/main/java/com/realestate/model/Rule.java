package com.realestate.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule {

    public enum Role {
        MANAGER,
        ADMIN,
        BUYER,
        SELLER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;  // Unique rule code or identifier

    @Column(nullable = false)
    private String description;  // The rule description

    @Column(nullable = false)
    private boolean active;  // Is the rule active or not

    @Enumerated(EnumType.STRING)
    private Role targetRole;  // Role this rule applies to
}