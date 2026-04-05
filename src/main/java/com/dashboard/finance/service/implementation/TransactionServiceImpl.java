package com.dashboard.finance.service.implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dashboard.finance.dto.CreateTransactionRequest;
import com.dashboard.finance.exception.ResourceNotFoundException;
import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.model.User;
import com.dashboard.finance.model.enums.TransactionType;
import com.dashboard.finance.repository.TransactionRepository;
import com.dashboard.finance.repository.UserRepository;
import com.dashboard.finance.service.interfaces.TransactionService;

@Service
public class TransactionServiceImpl
        implements TransactionService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            UserRepository userRepository) {

        this.transactionRepository =
                transactionRepository;

        this.userRepository =
                userRepository;
    }

    @Override
    public Transaction createTransaction(
            CreateTransactionRequest request) {

        // Step 1 — Find User

        User user =
                userRepository
                        .findById(request.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with id: "
                                                + request.getUserId()));

        // Step 2 — Create Transaction

        Transaction transaction =
                new Transaction();

        transaction.setAmount(
                request.getAmount());

        transaction.setType(
                request.getType());

        transaction.setCategory(
                request.getCategory());

        transaction.setDescription(
                request.getDescription());

        transaction.setDate(
                request.getDate());

        transaction.setCreatedBy(user);

        // Step 3 — Save

        return transactionRepository
                .save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();

    }

    @Override
    public List<Transaction> getTransactionsByUser(Long userId) {

        return transactionRepository
                .findByCreatedById(userId);

    }

    @Override
    public void deleteTransaction(Long id) {

        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found with id: "
                                                + id));

        transactionRepository.delete(transaction);

    }
    
    
    
    @Override
    public Transaction updateTransaction(
            Long id,
            CreateTransactionRequest request) {

        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found with id: "
                                                + id));

        User user =
                userRepository
                        .findById(request.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with id: "
                                                + request.getUserId()));

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setCreatedBy(user);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction>
    getTransactionsByCategory(String category) {

        return transactionRepository
                .findByCategory(category);

    }

    @Override
    public Page<Transaction> getTransactionsPaginated(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return transactionRepository
                .findAll(pageable);
    }
    

@Override
public List<Transaction> searchTransactions(
        String category,
        TransactionType type) {

    // Both category and type present

    if (category != null && type != null) {

        return transactionRepository
                .findByCategoryAndType(
                        category,
                        type);
    }

    // Only category

    if (category != null) {

        return transactionRepository
                .findByCategory(category);
    }

    // Only type

    if (type != null) {

        return transactionRepository
                .findByType(type);
    }

    // Nothing provided

    return transactionRepository
            .findAll();
}

}