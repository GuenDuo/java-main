package com.example.backendauth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;

@Entity  // Đảm bảo rằng bạn đánh dấu class này là một Entity của JPA
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Tên task

    @Column(nullable = false)
    private String description; // Mô tả task

    @Column(nullable = false)
    private Date startDate; // Ngày bắt đầu

    @Column(nullable = false)
    private Date endDate; // Ngày kết thúc

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project; // Liên kết với project

    @Column(nullable = false)
    private String status = "Pending"; // Thêm trạng thái task (ví dụ: "Completed", "In Progress", "Pending")

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
