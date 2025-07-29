package com.example.backendauth.dto;

import java.time.LocalDateTime;

public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    // Constructor mặc định
    public ProjectResponse() {}

    // Constructor có đủ tham số
    public ProjectResponse(Long id, String name, String description, String status, LocalDateTime createdAt, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Thêm getter & setter cho startDate
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    // Thêm getter & setter cho endDate
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
