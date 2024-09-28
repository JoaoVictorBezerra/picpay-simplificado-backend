package com.joaovictor.picpaysimplificado.mappers;

import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.dto.transaction.CreateTransactionDTO;
import com.joaovictor.picpaysimplificado.entity.Transaction;

import java.time.Instant;

public final class TransactionMapper {
    private TransactionMapper() {
        throw new IllegalArgumentException("This is an utility class");
    }

    public static Transaction toEntity(CreateTransactionDTO transactionDTO) {
        return new Transaction()
              .builder()
              .payerId(transactionDTO.payerId())
              .value(transactionDTO.amount())
              .payeeId(transactionDTO.payeeId())
              .status(TransactionStatusEnum.CREATED)
              .createdAt(Instant.now())
              .updatedAt(null)
              .build();
    }
}
