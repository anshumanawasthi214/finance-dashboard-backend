package com.dashboard.finance.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dashboard.finance.dto.DashboardSummaryResponse;
import com.dashboard.finance.dto.MonthlySummaryResponse;
import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.model.enums.TransactionType;
import com.dashboard.finance.repository.TransactionRepository;
import com.dashboard.finance.service.interfaces.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private TransactionRepository transactionRepository;

    public DashboardServiceImpl(
            TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            if (transaction.getType()
                    == TransactionType.INCOME) {

                totalIncome =
                        totalIncome +
                        transaction.getAmount();
            }

            else if (transaction.getType()
                    == TransactionType.EXPENSE) {

                totalExpense =
                        totalExpense +
                        transaction.getAmount();
            }
        }

        double netBalance =
                totalIncome - totalExpense;

        DashboardSummaryResponse response =
                new DashboardSummaryResponse();

        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetBalance(netBalance);

        return response;
    }

    @Override
    public Map<String, Double> getCategorySummary() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        Map<String, Double> categoryMap =
                new HashMap<>();

        for (Transaction transaction : transactions) {

            if (transaction.getType()
                    == TransactionType.EXPENSE) {

                String category =
                        transaction.getCategory();

                Double amount =
                        transaction.getAmount();

                if (categoryMap.containsKey(category)) {

                    Double existingAmount =
                            categoryMap.get(category);

                    categoryMap.put(
                            category,
                            existingAmount + amount);

                }
                else {

                    categoryMap.put(category, amount);

                }
            }
        }

        return categoryMap;
    }
    
    @Override
    public MonthlySummaryResponse getMonthlySummary(
            int year,
            int month) {

        List<Transaction> transactions =
                transactionRepository.findAll();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {

            if (transaction.getDate() != null) {

                int transactionYear =
                        transaction.getDate().getYear();

                int transactionMonth =
                        transaction.getDate().getMonthValue();

                if (transactionYear == year &&
                    transactionMonth == month) {

                    if (transaction.getType()
                            == TransactionType.INCOME) {

                        totalIncome =
                                totalIncome +
                                transaction.getAmount();
                    }

                    else if (transaction.getType()
                            == TransactionType.EXPENSE) {

                        totalExpense =
                                totalExpense +
                                transaction.getAmount();
                    }
                }
            }
        }

        MonthlySummaryResponse response =
                new MonthlySummaryResponse();

        response.setYear(year);
        response.setMonth(month);
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);

        return response;
    }
    
    @Override
    public List<Transaction> getRecentTransactions(
            int limit) {

        if (limit <= 0) {

            limit = 5;

        }

        List<Transaction> transactions =
                transactionRepository
                        .findAllByOrderByDateDesc();

        List<Transaction> recentList =
                new ArrayList<>();

        int count = 0;

        for (Transaction transaction : transactions) {

            if (count >= limit) {

                break;

            }

            recentList.add(transaction);

            count++;

        }

        return recentList;
    }
}