package com.college.students.departments.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college.students.departments.dao.DepartmentDAO;
import com.college.students.departments.dto.ResponseDTO;
import com.college.students.departments.entity.Department;

@Service
public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentDAO dao;

	public ResponseEntity<ResponseDTO> getDepartments(String filterParam, String sortParam, Integer page,
			Integer limit) {

		log.info("method = {}, filterParam = {} , sortParam = {}, page = {}, limit = {}", "getDepartments", filterParam,
				sortParam, page, limit);

		Set<Department> departments = dao.getDepartments().stream().collect(Collectors.toSet());

		ResponseDTO responseDTO = new ResponseDTO(departments);

		log.info("method = {}, responseDTO = {} ", "getDepartments", responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> getDepartmentById(Long id) {
		log.info("method = {}, id = {}", "getDepartmentById", id);

//		Department.builder().id();

//		Optional<Department> departmentOpt = dao.find();

		return null;
	}

}
