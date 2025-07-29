package com.example.backendauth.service;

import com.example.backendauth.dto.EmployeeRequest;
import com.example.backendauth.dto.EmployeeResponse;
import com.example.backendauth.model.Employee;
import com.example.backendauth.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        return mapToResponse(employee);
    }

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());

        // Chuyển String sang LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate createdDate = LocalDate.parse(employeeRequest.getCreatedDate(), formatter);
        employee.setCreatedDate(createdDate.format(formatter)); // format sang String
        // ĐÚNG

        employee.setDesignation(employeeRequest.getDesignation());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setCreatedBy(employeeRequest.getCreatedBy());
        employee.setEmail(employeeRequest.getEmail());

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponse(savedEmployee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setDesignation(employee.getDesignation());
        response.setDepartment(employee.getDepartment());
        response.setCreatedBy(employee.getCreatedBy());
        response.setEmail(employee.getEmail());

        if (employee.getCreatedDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            response.setCreatedDate(LocalDate.parse(employee.getCreatedDate(), formatter));
            // hoặc dùng response.setCreatedDate(employee.getCreatedDateAsLocalDate());
        }

        return response;
    }

    // Phương thức để cập nhật thông tin nhân viên
    // Phương thức để cập nhật thông tin nhân viên
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        // Tìm kiếm nhân viên theo ID
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        // Cập nhật thông tin nhân viên
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDesignation(employeeRequest.getDesignation());
        employee.setDepartment(employeeRequest.getDepartment());
        employee.setNationality(employeeRequest.getNationality());

        // Lưu lại nhân viên đã cập nhật
        Employee updatedEmployee = employeeRepository.save(employee);

        // Chuyển đổi entity thành DTO
        return mapToResponse(updatedEmployee);
    }
}

