package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuingBank {
    private Long id;
    private String bic;
    private String bin;
    private String abbreviatedName;
}
