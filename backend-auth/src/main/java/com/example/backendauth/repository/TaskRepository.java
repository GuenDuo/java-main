package com.example.backendauth.repository;

import com.example.backendauth.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Đảm bảo đã có annotation @Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    int countByStatus(String status);  // Phương thức để đếm task theo trạng thái

}
