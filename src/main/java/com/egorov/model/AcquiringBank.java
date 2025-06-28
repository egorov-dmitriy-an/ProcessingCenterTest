package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "acquiring_bank")
public class AcquiringBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bic")
    private String bic;
    @Column(name = "abbreviated_name")
    private String abbreviatedName;

}
