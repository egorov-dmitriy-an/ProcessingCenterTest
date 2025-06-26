package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String terminalId;
    @ManyToOne
    @JoinColumn(name = "mcc_id_id")
    private MerchantCategoryCode mccId;
    @ManyToOne
    @JoinColumn(name = "pos_id_id")
    private SalesPoint posId;
}
