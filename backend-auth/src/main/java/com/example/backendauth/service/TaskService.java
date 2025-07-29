package com.example.backendauth.service;

import com.example.backendauth.model.Task;
import com.example.backendauth.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.backendauth.dto.TaskRequest;
import com.example.backendauth.model.Project;
import com.example.backendauth.repository.ProjectRepository;



@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStartDate(request.getStartDate());
        task.setEndDate(request.getEndDate());
        task.setStatus(request.getStatus() != null ? request.getStatus() : "Pending");
        // Lấy Project từ DB nếu có
        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }
        return taskRepository.save(task);
    }

    public Task completeTask(Long id) {
        Task task = getTaskById(id); // hoặc taskRepository.findById(id).orElseThrow(...)
        if (task == null) throw new RuntimeException("Task not found");
        task.setStatus("Completed");
        return taskRepository.save(task);
    }


    public Task createTask(Task task) {
        return taskRepository.save(task); // Tạo task mới
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null); // Lấy task theo ID
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Lấy tất cả tasks
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id); // Xóa task
    }
}
