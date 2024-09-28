package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.constants.Constants;
import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.entity.User;
import com.joaovictor.picpaysimplificado.exceptions.transaction.InsufficientBalance;
import com.joaovictor.picpaysimplificado.exceptions.transaction.InvalidPayerException;
import com.joaovictor.picpaysimplificado.mappers.TransactionMapper;
import com.joaovictor.picpaysimplificado.repository.TransactionRepository;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import com.joaovictor.picpaysimplificado.service.interfac.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private UserService userService;

    @Override
    public Transaction createTransaction(long payeeId, long payerId, BigDecimal amount) {
        log.info("Start to create transaction");
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
        log.info("Start to transfer from {} to {} value {}", payerId, payeeId, amount);
        var payer = userService.findUserById(payerId);
        var payee = userService.findUserById(payeeId);
        var canDoTransaction = verifyIfPayerIsValid(payer);
        if(!canDoTransaction) throw new InvalidPayerException(Constants.INVALID_PAYER_DETAIL, Constants.INVALID_PAYER_DETAIL);
        if(payer.getBalance().compareTo(amount) < 0) throw new InsufficientBalance(Constants.INSUFFICIENT_BALANCE_DETAIL, Constants.INSUFFICIENT_BALANCE_TITLE);
        payer.setBalance(payer.getBalance().subtract(amount));
        payer.setBalance(payee.getBalance().add(amount));
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

    public boolean verifyIfPayerIsValid(User payer) {
        return payer.getDocument().length() != 14;
    }

}
