package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "issuing_bank")
public class IssuingBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bic")
    private String bic;
    @Column(name = "bin")
    private String bin;
    @Column(name = "abbreviated_name")
    private String abbreviatedName;
}
