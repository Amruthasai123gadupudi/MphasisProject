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
@Table(name = "transaction")
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INITIATED;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    
    public void setProperty(Property property) {
        this.property = property;
        if (property != null) {
            this.amount = property.getPrice();
        } else {
            this.amount = null;
        }
    }



    public enum Status {
        INITIATED, IN_PROGRESS, COMPLETED
    }
}
