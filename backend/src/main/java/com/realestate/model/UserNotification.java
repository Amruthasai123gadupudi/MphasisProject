
package com.realestate.model;

import com.realestate.model.User.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification_settings")

public class UserNotification {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id") 
	private User user;
    
    private String message;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_role")
    private Role targetRole;

}


