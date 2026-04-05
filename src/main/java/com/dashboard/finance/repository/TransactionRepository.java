package com.dashboard.finance.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.model.enums.TransactionType;


public interface TransactionRepository
extends JpaRepository<Transaction, Long> {
Page<Transaction> findAll(Pageable pageable);

List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

List<Transaction> findByCategory(String category);

List<Transaction> findByCreatedById(Long userId);

List<Transaction> findAllByOrderByDateDesc();

List<Transaction> findByType(TransactionType type);

List<Transaction> findByCategoryAndType(
        String category,
        TransactionType type);



}