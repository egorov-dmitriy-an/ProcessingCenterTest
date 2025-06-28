package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sales_point")
public class SalesPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pos_name")
    private String posName;
    @Column(name = "pos_address")
    private String posAddress;
    @Column(name = "pos_inn")
    private String posInn;
    @ManyToOne
    @JoinColumn(name = "acquiring_bank_id")
    private AcquiringBank acquiringBankId;
}
