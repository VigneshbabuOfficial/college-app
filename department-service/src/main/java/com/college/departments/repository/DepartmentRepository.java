package com.college.departments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.departments.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
