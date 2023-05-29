package com.example.carsharingservice.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Column(nullable = false)
    private PaymentType paymentType;
    @Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Rental rental;
    @Column(nullable = false)
    private String paymentUrl;
    @Column(nullable = false)
    private String paymentSessionId;
    @Column(nullable = false)
    private BigDecimal paymentAmount;
}
