package com.joaovictor.picpaysimplificado.entity;

import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long payeeId;
    private long payerId;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;
}
