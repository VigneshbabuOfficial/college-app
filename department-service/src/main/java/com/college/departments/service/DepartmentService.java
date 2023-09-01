package com.college.departments.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college.departments.config.ConverterConfig;
import com.college.departments.dao.DepartmentDAO;
import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.DepartmentResponseDTO;
import com.college.departments.dto.ErrorResponseDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.dto.SuccessResponseDTO;
import com.college.departments.entity.Department;
import com.college.departments.utils.CustomLogger;

@Service
public class DepartmentService {

	@Autowired
	private CustomLogger log;

	@Autowired
	private DepartmentDAO dao;

	@Autowired
	private ConverterConfig converterConfig;

	public ResponseEntity<ResponseDTO> getDepartments(String filterParam, String sortParam, Integer page,
			Integer limit) {

		log.debug("method = getDepartments, filterParam = " + filterParam + " , sortParam = " + sortParam + ", page = "
				+ page + ", limit = " + limit);

		Set<Department> departments = dao.getDepartments().stream().collect(Collectors.toSet());

		ResponseDTO responseDTO = SuccessResponseDTO.builder().data(departments).build();

		log.info("method = getDepartments, responseDTO = " + responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> getDepartmentById(Long id) {

		log.debug("method = getDepartmentById, id = " + id);

		Optional<Department> departmentOpt = dao.findById(id);

		if (departmentOpt.isEmpty()) {

			ErrorResponseDTO responseDTO = ErrorResponseDTO.builder().errorCode("NO_DATA")
					.message("Department does not exists").build();

			log.info("method = getDepartmentById, responseDTO = " + responseDTO);

			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);

		}

		ResponseDTO responseDTO = SuccessResponseDTO.builder().data(Set.of(departmentOpt.get())).build();

		log.debug("method = getDepartmentById, responseDTO = " + responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	public ResponseEntity<ResponseDTO> addDepartment(DepartmentInputDTO departmentInput) {

		log.debug("method = addDepartment, departmentInput = " + departmentInput);

		Department savedDepart = dao.addDepartment(departmentInput);

		ResponseDTO responseDTO = SuccessResponseDTO.builder()
				.data(Set.of(converterConfig.converter(savedDepart, DepartmentResponseDTO.class))).build();

		log.debug("method = addDepartment, responseDTO = " + responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

}
