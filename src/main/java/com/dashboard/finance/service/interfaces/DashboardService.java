package com.dashboard.finance.service.interfaces;

import java.util.List;
import java.util.Map;

import com.dashboard.finance.dto.DashboardSummaryResponse;
import com.dashboard.finance.dto.MonthlySummaryResponse;
import com.dashboard.finance.model.Transaction;

public interface DashboardService {

    DashboardSummaryResponse getDashboardSummary();
    
    Map<String, Double> getCategorySummary();
    
    MonthlySummaryResponse getMonthlySummary(
            int year,
            int month);
    
    List<Transaction> getRecentTransactions(int limit);

}