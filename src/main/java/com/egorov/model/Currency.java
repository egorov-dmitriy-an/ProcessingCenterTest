package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private Long id;
    private String currencyDigitalCode;
    private String currencyLetterCode;
    private String currencyName;
}
