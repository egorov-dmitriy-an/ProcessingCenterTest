package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "currency_digital_code")
    private String currencyDigitalCode;
    @Column(name = "currency_letter_code")
    private String currencyLetterCode;
    @Column(name = "currency_name")
    private String currencyName;
}
