package com.egorov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCode {
    private Long id;
    private String errorCode;
    private String errorDescription;
    private String errorLevel;
}
