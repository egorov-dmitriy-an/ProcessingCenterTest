package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantCategoryCode {
    private Long id;
    private String mcc;
    private String mccName;
}
