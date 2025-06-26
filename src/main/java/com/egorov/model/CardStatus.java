package com.egorov.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CardStatus {
    @Id
    private Long id;
    private String cardStatusName;
}
