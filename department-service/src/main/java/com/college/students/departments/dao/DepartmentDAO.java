package com.college.students.departments.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.college.students.departments.entity.Department;
import com.college.students.departments.repository.DepartmentRepository;

@Repository
public class DepartmentDAO {

	@Autowired
	private DepartmentRepository repository;

	public List<Department> getDepartments() {

		return repository.findAll();
	}

}
