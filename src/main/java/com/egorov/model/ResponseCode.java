package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "response_code")
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "error_code")
    private String errorCode;
    @Column(name = "error_description")
    private String errorDescription;
    @Column(name = "error_level")
    private String errorLevel;
}
