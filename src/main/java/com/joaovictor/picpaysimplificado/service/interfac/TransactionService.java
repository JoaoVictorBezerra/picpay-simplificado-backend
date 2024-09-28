package com.joaovictor.picpaysimplificado.service.interfac;

import com.joaovictor.picpaysimplificado.dto.transaction.CreateTransactionDTO;
import com.joaovictor.picpaysimplificado.entity.Transaction;

import java.io.IOException;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionDTO transactionDTO);
    Transaction confirmTransaction(Transaction transaction);
    void failTransaction(Transaction transaction);

    Transaction doTransfer(CreateTransactionDTO transactionDTO) throws IOException;
    boolean verifyIfTransferIsApproved() throws IOException;
    void sendTransactionNotification(String email);
}
