package com.dashboard.finance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.finance.dto.DashboardSummaryResponse;
import com.dashboard.finance.dto.MonthlySummaryResponse;
import com.dashboard.finance.model.Transaction;
import com.dashboard.finance.service.interfaces.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {

        return dashboardService
                .getDashboardSummary();
    }
    
    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary() {

        return dashboardService
                .getCategorySummary();

    }
    
    @GetMapping("/monthly-summary")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        return dashboardService
                .getMonthlySummary(year, month);
    }
    
    @GetMapping("/recent-transactions")
    public List<Transaction> getRecentTransactions(
            @RequestParam(defaultValue = "5")
            int limit) {

        return dashboardService
                .getRecentTransactions(limit);
    }
}