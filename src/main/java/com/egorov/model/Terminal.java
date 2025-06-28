package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "terminal_id")
    private String terminalId;
    @ManyToOne
    @JoinColumn(name = "mcc_id")
    private MerchantCategoryCode mccId;
    @ManyToOne
    @JoinColumn(name = "pos_id")
    private SalesPoint posId;
}
