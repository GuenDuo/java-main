package com.example.backendauth.controller;

import com.example.backendauth.dto.*;
import com.example.backendauth.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Chỉ cho phép ADMIN tạo project mới
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProjectResponse createProject(@RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    // Cho phép cả ADMIN và USER lấy tất cả projects
    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Chỉ cho phép ADMIN và USER xem chi tiết project
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // Chỉ cho phép ADMIN cập nhật project
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id, @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    // Chỉ cho phép ADMIN xóa project
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
