package com.joaovictor.picpaysimplificado.repository;

import com.joaovictor.picpaysimplificado.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
