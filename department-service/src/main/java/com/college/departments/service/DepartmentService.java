package com.college.departments.service;

import static com.college.departments.utils.CustomLogger.logKeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.college.departments.repository.DepartmentRepository;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.EntityUtil;
import com.college.departments.utils.ErrorCodeMessage;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class DepartmentService {

	private static final String METHOD_LOG_STR = "DepartmentService.%s()";

	@Autowired
	private ConverterConfig converterConfig;

	@Autowired
	private DepartmentDAO dao;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CustomLogger log;

	public ResponseEntity<ResponseDTO> addDepartment(DepartmentInputDTO departmentInput) {

		log.info(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("departmentInput", departmentInput));

		Optional<Department> optionalDept = departmentRepository
				.findOne(Example.of(Department.builder().name(departmentInput.getName()).build()));

		if (optionalDept.isPresent()) {
			ErrorResponseDTO responseDTO = ErrorResponseDTO.builder().errorCode(ErrorCodeMessage.INVALID_DATA)
					.message(departmentInput.getName() + " Department already exists").build();
			log.debug(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		Department savedDepart = dao.addDepartment(departmentInput);

		ResponseDTO responseDTO = SuccessResponseDTO.builder()
				.data(Arrays.asList(converterConfig.converter(savedDepart, DepartmentResponseDTO.class))).build();

		log.debug(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDTO> getDepartmentById(Long id) {

		log.info(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("id", id));

		Optional<Department> departmentOpt = dao.findById(id);

		if (departmentOpt.isEmpty()) {
			ErrorResponseDTO responseDTO = ErrorResponseDTO.builder().errorCode(ErrorCodeMessage.NO_DATA)
					.message("Department does not exists").build();
			log.error(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		ResponseDTO responseDTO = SuccessResponseDTO.builder().data(Arrays.asList(departmentOpt.get())).build();

		log.debug(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	public ResponseEntity<ResponseDTO> getDepartments(String[] filterParams, String[] orFilterParams,
			String[] sortParams, int page, int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getDepartments")
				+ logKeyValue("filterParams", Arrays.toString(filterParams))
				+ logKeyValue("orFilterParams", Arrays.toString(orFilterParams))
				+ logKeyValue("sortParams", Arrays.toString(sortParams)) + logKeyValue("page", page)
				+ logKeyValue("limit", limit) + logKeyValue("fields", Arrays.toString(fields)));

		List<ObjectNode> dataListNode = new ArrayList<>();
		List<Department> departments = new ArrayList<>();
		List<Tuple> tupleDataList = new ArrayList<>();

		int offset = (page - 1) * limit;

		Long total = 0L;

		ResponseDTO responseDTO = null;

		try {
			total = dao.getDepartments(departments, tupleDataList, filterParams, orFilterParams, sortParams, offset,
					limit, fields);
		} catch (Exception e) {
			responseDTO = ErrorResponseDTO.builder().errorCode(ErrorCodeMessage.INTERNAL_SERVER_ERROR)
					.message(ErrorCodeMessage.FAILED_TO_FETCH_RESOURCE_MSG).build();
			log.error(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue("exception", e.getMessage())
					+ logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!tupleDataList.isEmpty()) {

			dataListNode.addAll(EntityUtil.jpaTuplesToListNode(tupleDataList));

		}

		if (fields != null && fields.length > 0) {

			responseDTO = SuccessResponseDTO.builder().data(dataListNode).page(page).limit(limit).total(total)
					.currentPageTotal(dataListNode.size()).build();
		} else {

			responseDTO = SuccessResponseDTO.builder().data(departments).page(page).limit(limit).total(total)
					.currentPageTotal(departments.size()).build();
		}

		log.debug(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

}
