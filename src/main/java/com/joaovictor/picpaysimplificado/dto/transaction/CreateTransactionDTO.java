package com.joaovictor.picpaysimplificado.dto.transaction;

import java.math.BigDecimal;

public record CreateTransactionDTO(long payerId, long payeeId, BigDecimal amount) {
}
