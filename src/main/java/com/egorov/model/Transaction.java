package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "transaction_name")
    private String transactionName;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionTypeId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card cardId;

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminalId;

    @ManyToOne
    @JoinColumn(name = "response_code_id")
    private ResponseCode responseCodeId;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Column(name = "reseived_from_issuing_bank")
    private LocalDateTime reseivedFromIssuingBank;

    @Column(name = "sent_to_issuing_bank")
    private LocalDateTime sentToIssuingBank;

}
