package com.college.students.departments.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.college.students.departments.dto.DepartmentInputDTO;
import com.college.students.departments.entity.Department;
import com.college.students.departments.repository.DepartmentRepository;

@Repository
public class DepartmentDAO {

	private static final Logger log = LoggerFactory.getLogger(DepartmentDAO.class);

	@Autowired
	private DepartmentRepository repository;

	public List<Department> getDepartments() {

		return repository.findAll();
	}

	public Optional<Department> findById(Long id) {

		return repository.findById(id);
	}

	public Department addDepartment(DepartmentInputDTO departmentInput) {

		log.debug("method = {}, departmentInput = {}", "addDepartment", departmentInput);

		Department newDepartment = Department.builder().name(departmentInput.getName())
				.comments(departmentInput.getComments()).build();

		return repository.save(newDepartment);
	}

}
