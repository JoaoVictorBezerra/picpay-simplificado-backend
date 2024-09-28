package com.joaovictor.picpaysimplificado.mappers;

import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.entity.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public final class TransactionMapper {
    private TransactionMapper() {
        throw new IllegalArgumentException("This is an utility class");
    }

    public static Transaction toEntity(long payeeId, long payerId, BigDecimal value) {
        return new Transaction()
              .builder()
              .payerId(payerId)
              .value(value)
              .payeeId(payeeId)
              .status(TransactionStatusEnum.CREATED)
              .createdAt(Instant.now())
              .updatedAt(null)
              .build();
    }
}
