package com.example.backendauth.controller;

import com.example.backendauth.model.User;
import com.example.backendauth.dto.ProjectRequest; // Import DTO ProjectRequest
import com.example.backendauth.dto.ProjectResponse; // Import DTO ProjectResponse
import com.example.backendauth.service.ProjectService;  // Import ProjectService
import com.example.backendauth.service.UserService;    // Import UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ProjectService projectService;  // Inject ProjectService

    @Autowired
    private UserService userService;        // Inject UserService

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Trả về file login.html trong thư mục templates
    }

    @GetMapping("/projects")
    public String projectsPage(Model model) {
        // Lấy danh sách các project từ service
        List<ProjectResponse> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);  // Gửi dữ liệu vào view
        return "projects";  // Trả về file projects.html
    }

    @GetMapping("/admin/add-user")
    public String addUserPage() {
        return "adduser";  // Trả về file adduser.html
    }

    @PostMapping("/admin/add-user")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);  // Thêm người dùng mới vào hệ thống
        return "redirect:/admin/users";  // Redirect tới danh sách người dùng
    }

    @GetMapping("/admin/create-project")
    public String createProjectPage() {
        return "create_project";  // Trả về file create_project.html
    }

    @PostMapping("/admin/create-project")
    public String createProject(@ModelAttribute ProjectRequest projectRequest) {
        projectService.createProject(projectRequest);  // Tạo mới một project
        return "redirect:/projects";  // Redirect tới danh sách projects
    }

    @GetMapping("/projects/{id}")
    public String getProjectById(@PathVariable Long id, Model model) {
        ProjectResponse project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project_details";  // Trả về file project_details.html
    }

    @PostMapping("/projects/{id}/update")
    public String updateProject(@PathVariable Long id, @ModelAttribute ProjectRequest projectRequest) {
        projectService.updateProject(id, projectRequest);  // Cập nhật project
        return "redirect:/projects";  // Redirect tới danh sách projects
    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);  // Xóa project
        return "redirect:/projects";  // Redirect tới danh sách projects
    }
}
