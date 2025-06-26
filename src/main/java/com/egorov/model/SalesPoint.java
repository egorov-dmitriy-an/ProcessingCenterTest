package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesPoint {
    private Long id;
    private String posName;
    private String posAddress;
    private String posInn;
    private AcquiringBank acquiringBankId;
}
