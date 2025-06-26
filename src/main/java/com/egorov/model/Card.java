package com.egorov.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String holderName;
    private CardStatus cardStatusId;
    private PaymentSystem paymentSystemId;
    private Account accountId;
    private LocalDateTime receivedFromIssuingBank;
    private LocalDateTime sentToIssuingBank;
}
