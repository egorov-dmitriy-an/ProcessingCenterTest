package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionType {
    private Long id;
    private String transactionTypeName;
    private String operator;
}
