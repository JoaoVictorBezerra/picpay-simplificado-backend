package com.joaovictor.picpaysimplificado.service.interfac;

import com.joaovictor.picpaysimplificado.dto.transaction.CreateTransactionDTO;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.entity.User;

import java.io.IOException;
import java.math.BigDecimal;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionDTO transactionDTO);
    Transaction confirmTransaction(Transaction transaction);
    int failTransactions();
    Transaction doTransfer(CreateTransactionDTO transactionDTO) throws IOException;
    boolean verifyIfTransferIsApproved() throws IOException;

    void sendTransactionNotification(User payee, User payer, BigDecimal amount);
}
