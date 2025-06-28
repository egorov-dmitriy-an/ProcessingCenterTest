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
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String holderName;
    @ManyToOne
    @JoinColumn(name = "card_status_id_id")
    private CardStatus cardStatusId;
    @ManyToOne
    @JoinColumn(name = "payment_system_id_id")
    private PaymentSystem paymentSystemId;
    @ManyToOne
    @JoinColumn(name = "account_id_id")
    private Account accountId;
    private LocalDateTime receivedFromIssuingBank;
    private LocalDateTime sentToIssuingBank;


}
