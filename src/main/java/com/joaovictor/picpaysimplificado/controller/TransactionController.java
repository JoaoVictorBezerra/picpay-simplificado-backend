package com.joaovictor.picpaysimplificado.controller;


import com.joaovictor.picpaysimplificado.dto.transaction.CreateTransactionDTO;
import com.joaovictor.picpaysimplificado.entity.Transaction;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) throws IOException {
        var transaction = transactionService.doTransfer(createTransactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
