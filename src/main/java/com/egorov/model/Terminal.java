package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Terminal {
    private Long id;
    private String terminalId;
    private MerchantCategoryCode mccId;
    private SalesPoint posId;
}
