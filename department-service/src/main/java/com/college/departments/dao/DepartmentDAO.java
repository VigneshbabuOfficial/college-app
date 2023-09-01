package com.college.departments.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.entity.Department;
import com.college.departments.repository.DepartmentRepository;
import com.college.departments.utils.CustomLogger;

@Repository
public class DepartmentDAO {

	@Autowired
	private CustomLogger log;

	@Autowired
	private DepartmentRepository repository;

	public List<Department> getDepartments() {

		return repository.findAll();
	}

	public Optional<Department> findById(Long id) {

		return repository.findById(id);
	}

	public Department addDepartment(DepartmentInputDTO departmentInput) {

		log.debug("method = addDepartment, departmentInput = " + departmentInput);

		Department newDepartment = Department.builder().name(departmentInput.getName())
				.comments(departmentInput.getComments()).build();

		return repository.save(newDepartment);
	}

}
