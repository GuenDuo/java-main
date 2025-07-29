package com.example.backendauth.dto;

import java.util.List;

public class DashboardData {
    private List<Integer> projectsStatus;
    private List<Integer> tasksStatus;

    // Getter and Setter
    public List<Integer> getProjectsStatus() {
        return projectsStatus;
    }

    public void setProjectsStatus(List<Integer> projectsStatus) {
        this.projectsStatus = projectsStatus;
    }

    public List<Integer> getTasksStatus() {
        return tasksStatus;
    }

    public void setTasksStatus(List<Integer> tasksStatus) {
        this.tasksStatus = tasksStatus;
    }
}
