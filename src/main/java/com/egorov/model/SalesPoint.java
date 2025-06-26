package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String posName;
    private String posAddress;
    private String posInn;
    @ManyToOne
    @JoinColumn(name = "acquiring_bank_id_id")
    private AcquiringBank acquiringBankId;
}
