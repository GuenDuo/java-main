package com.example.backendauth.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String createdBy;
    private String designation;
    private String department;
    private String photoUrl;
    private String email;
    private String nationality;
    // Chuyển đổi LocalDate thành String cho cơ sở dữ liệu
    @Column(name = "created_date")
    private String createdDate; // hoặc LocalDate
    // Lưu ngày dưới dạng String trong DB

    // Getter và Setter cho các trường dữ liệu
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    // Phương thức chuyển đổi từ LocalDate sang String
    public LocalDate getCreatedDateAsLocalDate() {
        if (this.createdDate == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.createdDate, formatter);  // Đúng: chuyển String thành LocalDate
    }

    // Phương thức để lưu LocalDate dưới dạng String vào DB
    public void setCreatedDateFromLocalDate(LocalDate createdDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createdDate = createdDate.format(formatter);  // Chuyển LocalDate thành String
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
