package com.joaovictor.picpaysimplificado.service.interfac;

import com.joaovictor.picpaysimplificado.entity.Transaction;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction createTransaction(long payeeId, long payerId, BigDecimal amount);
    Transaction confirmTransaction(Transaction transaction);
    void failTransaction(Transaction transaction);

    void doTransfer(long payerId, long payeeId, BigDecimal amount);
    boolean verifyIfTransferIsApproved();
    void sendTransactionNotification(String email);
}
