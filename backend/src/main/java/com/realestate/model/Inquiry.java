package com.realestate.model;

import java.time.LocalDateTime;

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
@Table(name = "inquiry")
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "TEXT")
	private String message;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@ManyToOne
	@JoinColumn(name = "property_id")
	private Property property;

	@Column(name = "response_message")
	private String responseMessage;

	@ManyToOne
	@JoinColumn(name = "responder_id")
	private User responder;

	@Column(name = "responded_at")
	private LocalDateTime respondedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "target_role", nullable = false)
	private TargetRole targetRole;
	
	@ManyToOne
	@JoinColumn(name = "target_user_id")
	private User targetUser; // Either seller or manager based on targetRole

	@Column(name = "schedule_date")
	private LocalDateTime scheduleDate;

	public enum TargetRole {
		SELLER, MANAGER
	}

}