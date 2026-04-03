package com.dashboard.finance.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dashboard.finance.dto.CreateTransactionRequest;
import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.model.enums.TransactionType;

public interface TransactionService {

    Transaction createTransaction(
            CreateTransactionRequest request);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByUser(
            Long userId);

    void deleteTransaction(Long id);

    Transaction updateTransaction(
            Long id,
            CreateTransactionRequest request);

    List<Transaction> getTransactionsByCategory(
            String category);

    // Pagination Method (Declaration only)

    Page<Transaction> getTransactionsPaginated(
            int page,
            int size);
    
    List<Transaction> searchTransactions(
            String category,
            TransactionType type);

}
