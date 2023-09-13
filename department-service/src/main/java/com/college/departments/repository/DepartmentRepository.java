package com.college.departments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.departments.entity.Department;
import com.college.departments.enums.Departments;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> findByNameAndIdNot(Departments name, Long id);

}
