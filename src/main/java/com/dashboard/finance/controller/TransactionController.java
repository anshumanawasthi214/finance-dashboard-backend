package com.dashboard.finance.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.finance.dto.CreateTransactionRequest;
import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.model.enums.TransactionType;
import com.dashboard.finance.service.interfaces.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(
            TransactionService transactionService) {

        this.transactionService =
                transactionService;
    }

    @PostMapping
    public Transaction createTransaction(
            @RequestBody
            CreateTransactionRequest request) {

        return transactionService
                .createTransaction(request);
    }
    
    
    @GetMapping
    public List<Transaction> getAllTransactions() {

        return transactionService.getAllTransactions();

    }
    
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(
            @PathVariable Long userId) {

        return transactionService
                .getTransactionsByUser(userId);

    }
    
    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @PathVariable Long id) {

        transactionService.deleteTransaction(id);

    }
    
    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @RequestBody CreateTransactionRequest request) {

        return transactionService
                .updateTransaction(id, request);

    }
    
    @GetMapping("/category/{category}")
    public List<Transaction>
    getTransactionsByCategory(
            @PathVariable String category) {

        return transactionService
                .getTransactionsByCategory(category);

    }
    
    @GetMapping("/paginated")
    public Page<Transaction> getPaginatedTransactions(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return transactionService
                .getTransactionsPaginated(page, size);
    }
    
    @GetMapping("/search")
    public List<Transaction> searchTransactions(

            @RequestParam(required = false)
            String category,

            @RequestParam(required = false)
            TransactionType type) {

        return transactionService
                .searchTransactions(
                        category,
                        type);
    }
}