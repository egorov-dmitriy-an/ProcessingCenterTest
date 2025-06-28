package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "holder_name")
    private String holderName;
    @ManyToOne
    @JoinColumn(name = "card_status_id")
    private CardStatus cardStatusId;
    @ManyToOne
    @JoinColumn(name = "payment_system_id")
    private PaymentSystem paymentSystemId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;
    @Column(name = "received_from_issuing_bank")
    private LocalDateTime receivedFromIssuingBank;
    @Column(name = "sent_to_issuing_bank")
    private LocalDateTime sentToIssuingBank;
}
