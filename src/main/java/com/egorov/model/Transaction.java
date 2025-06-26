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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate transactionDate;
    private BigDecimal sum;
    private String transactionName;
    @ManyToOne
    @JoinColumn(name = "account_id_id")
    private Account accountId;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id_id")
    private TransactionType transactionTypeId;
    @ManyToOne
    @JoinColumn(name = "card_id_id")
    private Card cardId;
    @ManyToOne
    @JoinColumn(name = "terminal_id_id")
    private Terminal terminalId;
    @ManyToOne
    @JoinColumn(name = "response_code_id_id")
    private ResponseCode responseCodeId;
    private String authorizationCode;
    private LocalDateTime reseivedFromIssuingBank;
    private LocalDateTime sentToIssuingBank;
}
