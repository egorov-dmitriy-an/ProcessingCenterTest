package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "currency_id_id")
    private Currency currencyId;
    @ManyToOne
    @JoinColumn(name = "issuing_bank_id_id")
    private IssuingBank issuingBankId;
}
