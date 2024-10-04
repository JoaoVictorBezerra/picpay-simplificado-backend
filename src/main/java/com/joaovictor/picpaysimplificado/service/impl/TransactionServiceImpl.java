package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.constants.Constants;
import com.joaovictor.picpaysimplificado.constants.api.transaction.TransactionStatusEnum;
import com.joaovictor.picpaysimplificado.dto.transaction.CreateTransactionDTO;
import com.joaovictor.picpaysimplificado.dto.transaction.ExternalStatusResponseDTO;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.entity.User;
import com.joaovictor.picpaysimplificado.exceptions.transaction.InsufficientBalance;
import com.joaovictor.picpaysimplificado.exceptions.transaction.InvalidPayerException;
import com.joaovictor.picpaysimplificado.mappers.TransactionMapper;
import com.joaovictor.picpaysimplificado.repository.TransactionRepository;
import com.joaovictor.picpaysimplificado.service.interfac.CommunicationService;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import com.joaovictor.picpaysimplificado.service.interfac.UserService;
import com.joaovictor.picpaysimplificado.utils.api.StringUtil;
import com.joaovictor.picpaysimplificado.utils.http.JsonUtil;
import com.joaovictor.picpaysimplificado.utils.http.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CommunicationService communicationService;

    @Value("${api.authorize.url}")
    private String authorizeUrl;

    @Override
    public Transaction createTransaction(CreateTransactionDTO transactionDTO) {
        log.info("Start to create transaction");
        var transaction = TransactionMapper.toEntity(transactionDTO);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction confirmTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatusEnum.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Override
    public int failTransactions() {
        var transactionList = transactionRepository.findAll();
        var failedTransactions = transactionList
              .stream()
              .filter(transaction -> transaction.getStatus().equals(TransactionStatusEnum.CREATED))
              .filter(transaction -> transaction.getCreatedAt().isBefore(Instant.now()))
              .toList();
        int transactions = 0;
        for (var transaction : failedTransactions) {
            transaction.setStatus(TransactionStatusEnum.FAILED);
            transactionRepository.save(transaction);
            transactions++;
        }
        return transactions;
    }

    @Override
    public Transaction doTransfer(CreateTransactionDTO transactionDTO) throws IOException {
        log.info("METHOD:: Start to transfer from {} to {} value {}", transactionDTO.payerId(), transactionDTO.payeeId(), transactionDTO.amount());
        var payer = userService.findUserById(transactionDTO.payerId());
        var payee = userService.findUserById(transactionDTO.payeeId());
        if (!verifyIfPayerIsValid(payer)) {
            log.error("METHOD:: Invalid payer {}", StringUtil.setMaskOnDocument(payer.getDocument()));
            throw new InvalidPayerException(Constants.INVALID_PAYER_DETAIL, Constants.INVALID_PAYER_TITLE);
        }
        if (payer.getBalance().compareTo(transactionDTO.amount()) < 0) {
            log.error("METHOD:: Insufficient balance {}", payer.getBalance());
            throw new InsufficientBalance(Constants.INSUFFICIENT_BALANCE_DETAIL, Constants.INSUFFICIENT_BALANCE_TITLE);
        }
        var transaction = createTransaction(transactionDTO);
        verifyIfTransferIsApproved();
        payer.setBalance(payer.getBalance().subtract(transactionDTO.amount()));
        payee.setBalance(payee.getBalance().add(transactionDTO.amount()));
        List.of(payer, payee).forEach(userService::saveUser);
        var updatedTransaction = confirmTransaction(transaction);
        sendTransactionNotification(payee, payer, transactionDTO.amount());
        return updatedTransaction;
    }

    @Override
    public boolean verifyIfTransferIsApproved() throws IOException {
        var response = RequestUtil.doRequest(authorizeUrl, null, null, null, HttpMethod.GET, null);
        var body = JsonUtil.fromJson(response.getBody(), ExternalStatusResponseDTO.class);
        return body.getData().isAuthorization();
    }

    @Override
    public void sendTransactionNotification(User payee, User payer, BigDecimal amount) {
        sendTransactionSmsNotification(payee.getPhoneNumber(), StringUtil.setMaskOnDocument(payer.getDocument()), amount);
        sendTransactionEmailNotification(payee, payer, amount);
    }

    private void sendTransactionSmsNotification(String toNumber, String identification, BigDecimal amount) {
        communicationService.sendSmsNotification(toNumber, "Você recebeu uma transferência de " + identification + " no valor de R$ " + amount);
    }

    private void sendTransactionEmailNotification(User payee, User payer, BigDecimal amount) {
        var template = "Você recebeu uma transferência de: " + StringUtil.setMaskOnDocument(payer.getDocument()) + " no valor de R$ " + amount;
        var subject = "Ei! Você recebeu uma nova transferência.";
        communicationService.sendEmailNotification(payee.getEmail(), subject, template);
    }

    public boolean verifyIfPayerIsValid(User payer) {
        return payer.getDocument().length() != 14;
    }

}
