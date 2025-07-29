package com.example.backendauth.service;

import com.example.backendauth.dto.ProjectRequest;
import com.example.backendauth.dto.ProjectResponse;
import com.example.backendauth.model.Project;
import org.springframework.stereotype.Service;
import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(Long id);
    ProjectResponse updateProject(Long id, ProjectRequest request);
    void deleteProject(Long id);
}
