package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Currency currencyId;
    private IssuingBank issuingBankId;
}
