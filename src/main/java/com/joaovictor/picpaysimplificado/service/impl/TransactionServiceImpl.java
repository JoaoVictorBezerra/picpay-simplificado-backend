package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.mappers.TransactionMapper;
import com.joaovictor.picpaysimplificado.repository.TransactionRepository;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

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
}
