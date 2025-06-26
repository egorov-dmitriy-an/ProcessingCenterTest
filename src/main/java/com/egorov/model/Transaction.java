package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private LocalDate transactonDate;
    private BigDecimal sum;
    private String transactionName;
    private Account accountId;
    private TransactionType transactionTypeId;
    private Card cardId;
    private Terminal terminalId;
    private ResponseCode responseCodeId;
    private String authorizationCode;
    private LocalDateTime resievedFromIssuingBank;
    private LocalDateTime sentToIssuingBank;
}
