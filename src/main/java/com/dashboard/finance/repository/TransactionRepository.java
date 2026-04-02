package com.dashboard.finance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.finance.model.Transaction;

public interface TransactionRepository
extends JpaRepository<Transaction, Long> {

List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

List<Transaction> findByCategory(String category);

}