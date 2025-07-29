package com.example.backendauth.service;

import com.example.backendauth.dto.DashboardData;
import com.example.backendauth.repository.ProjectRepository;
import com.example.backendauth.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Phương thức trả về dữ liệu cho Dashboard
    public DashboardData getDashboardData() {
        // Truy xuất dữ liệu cho các biểu đồ
        List<Integer> projectStatus = getProjectStatus();
        List<Integer> taskStatus = getTaskStatus();

        // Tạo dữ liệu trả về
        DashboardData data = new DashboardData();
        data.setProjectsStatus(projectStatus);
        data.setTasksStatus(taskStatus);

        return data;
    }

    // Phương thức tính toán trạng thái của các dự án
    private List<Integer> getProjectStatus() {
        int completed = projectRepository.countByStatus("Completed");  // Đếm số dự án có trạng thái Completed
        int inProgress = projectRepository.countByStatus("In Progress");  // Đếm số dự án có trạng thái In Progress
        int pending = projectRepository.countByStatus("Pending");  // Đếm số dự án có trạng thái Pending

        System.out.println("Completed: " + completed); // Thêm dòng log để kiểm tra giá trị
        System.out.println("In Progress: " + inProgress);
        System.out.println("Pending: " + pending);

        return List.of(completed, inProgress, pending);  // Trả về một danh sách gồm ba giá trị tương ứng
    }

    // Phương thức tính toán trạng thái của các task
    private List<Integer> getTaskStatus() {
        int completed = taskRepository.countByStatus("Completed");
        int onHold = taskRepository.countByStatus("On Hold");
        int inProgress = taskRepository.countByStatus("In Progress");
        int pending = taskRepository.countByStatus("Pending");

        return List.of(completed, onHold, inProgress, pending);
    }
}
