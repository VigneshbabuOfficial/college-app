package com.college.students.departments.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college.students.departments.config.ConverterConfig;
import com.college.students.departments.dao.DepartmentDAO;
import com.college.students.departments.dto.DepartmentInputDTO;
import com.college.students.departments.dto.DepartmentResponseDTO;
import com.college.students.departments.dto.ErrorResponseDTO;
import com.college.students.departments.dto.ResponseDTO;
import com.college.students.departments.dto.SuccessResponseDTO;
import com.college.students.departments.entity.Department;

@Service
public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentDAO dao;

	@Autowired
	private ConverterConfig converterConfig;

	public ResponseEntity<ResponseDTO> getDepartments(String filterParam, String sortParam, Integer page,
			Integer limit) {

		log.debug("method = {}, filterParam = {} , sortParam = {}, page = {}, limit = {}", "getDepartments",
				filterParam, sortParam, page, limit);

		Set<Department> departments = dao.getDepartments().stream().collect(Collectors.toSet());

		ResponseDTO responseDTO = SuccessResponseDTO.builder().data(departments).build();

		log.info("method = {}, responseDTO = {} ", "getDepartments", responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> getDepartmentById(Long id) {

		log.debug("method = {}, id = {}", "getDepartmentById", id);

		Optional<Department> departmentOpt = dao.findById(id);

		if (departmentOpt.isEmpty()) {

			ErrorResponseDTO responseDTO = ErrorResponseDTO.builder().errorCode("NO_DATA")
					.message("Department does not exists").build();

			log.info("method = {}, responseDTO = {} ", "getDepartmentById", responseDTO);

			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);

		}

		ResponseDTO responseDTO = SuccessResponseDTO.builder().data(Set.of(departmentOpt.get())).build();

		log.info("method = {}, responseDTO = {} ", "getDepartmentById", responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	public ResponseEntity<ResponseDTO> addDepartment(DepartmentInputDTO departmentInput) {

		log.debug("method = {}, departmentInput = {}", "addDepartment", departmentInput);

		Department savedDepart = dao.addDepartment(departmentInput);

		ResponseDTO responseDTO = SuccessResponseDTO.builder()
				.data(Set.of(converterConfig.converter(savedDepart, DepartmentResponseDTO.class))).build();

		log.info("method = {}, responseDTO = {} ", "addDepartment", responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

}
