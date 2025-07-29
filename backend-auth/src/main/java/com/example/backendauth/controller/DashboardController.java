package com.example.backendauth.controller;

import com.example.backendauth.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backendauth.dto.DashboardData;



@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardData getDashboardData() {
        // Lấy dữ liệu cho biểu đồ từ các service
        return dashboardService.getDashboardData();
    }
}
