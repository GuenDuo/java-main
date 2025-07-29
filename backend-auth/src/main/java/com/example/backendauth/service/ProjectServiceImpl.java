package com.example.backendauth.service;

import com.example.backendauth.dto.*;
import com.example.backendauth.model.Project;
import com.example.backendauth.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private ProjectResponse mapToResponse(Project project) {
        ProjectResponse resp = new ProjectResponse();
        resp.setId(project.getId());
        resp.setName(project.getName());
        resp.setDescription(project.getDescription());
        resp.setCreatedAt(project.getCreatedAt());
        resp.setStatus(project.getStatus());
        resp.setStartDate(project.getStartDate());
        resp.setEndDate(project.getEndDate());
        return resp;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
