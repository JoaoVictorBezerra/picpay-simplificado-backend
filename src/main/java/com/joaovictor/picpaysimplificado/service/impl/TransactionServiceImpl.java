package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.exceptions.transaction.InsufficientBalance;
import com.joaovictor.picpaysimplificado.mappers.TransactionMapper;
import com.joaovictor.picpaysimplificado.repository.TransactionRepository;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import com.joaovictor.picpaysimplificado.service.interfac.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private UserService userService;

    @Override
    public Transaction createTransaction(long payeeId, long payerId, BigDecimal amount) {
        var transaction = TransactionMapper.toEntity(payeeId, payerId, amount);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction confirmTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatusEnum.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Override
    public void failTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatusEnum.FAILED);
        transactionRepository.save(transaction);
    }

    @Override
    public void doTransfer(long payerId, long payeeId, BigDecimal amount) {
        var payer = userService.findUserById(payerId);
        var payee = userService.findUserById(payeeId);
        if(payer.getBalance().compareTo(amount) == -1) throw new InsufficientBalance();
        payer.getBalance().subtract(amount);
        payee.getBalance().add(amount);
        var transaction = createTransaction(payeeId, payerId, amount);
        List.of(payer, payee).forEach(user -> userService.saveUser(user));
        confirmTransaction(transaction);
        sendTransactionNotification(payee.getEmail());
    }

    @Override
    public boolean verifyIfTransferIsApproved() {
        return true;
    }

    @Override
    public void sendTransactionNotification(String email) {
        // Enviar para o listener
    }
}
