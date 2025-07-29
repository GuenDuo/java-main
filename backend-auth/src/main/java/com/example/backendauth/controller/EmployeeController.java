package com.example.backendauth.controller;

import com.example.backendauth.dto.EmployeeRequest;
import com.example.backendauth.dto.EmployeeResponse;
import com.example.backendauth.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/admin")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // API để lấy danh sách nhân viên (Admin có thể xem và tạo)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list-employee")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // API để lấy chi tiết nhân viên
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // API để cập nhật thông tin nhân viên (Chỉ admin mới có quyền)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(id, employeeRequest);
        return ResponseEntity.ok(updatedEmployee);
    }
}

