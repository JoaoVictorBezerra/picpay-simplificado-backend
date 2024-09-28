package com.joaovictor.picpaysimplificado.repository;

import com.joaovictor.picpaysimplificado.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
