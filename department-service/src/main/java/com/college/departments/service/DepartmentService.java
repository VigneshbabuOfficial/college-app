package com.college.departments.service;

import static com.college.departments.utils.CustomLogger.logKeyValue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college.departments.config.ConverterConfig;
import com.college.departments.dao.DepartmentDAO;
import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.DepartmentResponseDTO;
import com.college.departments.dto.ErrorResponsesDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.dto.SuccessDataResponseDTO;
import com.college.departments.dto.SuccessMessageResponseDTO;
import com.college.departments.entity.Department;
import com.college.departments.enums.Departments;
import com.college.departments.repository.DepartmentRepository;
import com.college.departments.temp.Temp;
import com.college.departments.utils.CommonUtil;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.EntityUtil;
import com.college.departments.utils.ErrorCodeMessage;
import com.college.departments.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class DepartmentService {

	private static final String METHOD_LOG_STR = "DepartmentService.%s()";

	private final ConverterConfig converterConfig;

	private final DepartmentDAO dao;

	private final DepartmentRepository departmentRepository;

	private final CustomLogger log;

	private final RequestId requestId;

	private final ObjectMapper objectMapper;

	private final Temp temp;

	public DepartmentService(final ConverterConfig converterConfig, final DepartmentDAO dao,
			final DepartmentRepository departmentRepository, final CustomLogger log, final RequestId requestId,
			final ObjectMapper objectMapper, final Temp temp) {
		super();
		this.converterConfig = converterConfig;
		this.dao = dao;
		this.departmentRepository = departmentRepository;
		this.log = log;
		this.requestId = requestId;
		this.objectMapper = objectMapper;
		this.temp = temp;
	}

	public ResponseEntity<ResponseDTO> addDepartment(DepartmentInputDTO departmentInput) {

		log.info(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("departmentInput", departmentInput));

		Optional<Department> optionalDept = dao.isDepartmentExist(Departments.valueOf(departmentInput.getName().get()),
				null);

		if (optionalDept.isPresent()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					departmentInput.getName().get() + " Department already exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		// ----------------------------- testing method stub
		ObjectNode input = objectMapper.convertValue(departmentInput, ObjectNode.class);
		try {
			System.out.println("temp.testNode(12) = " + temp.testNode(12));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.println("temp.testNode(input) = " + temp.testNode(input));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// -----------------------------

		Department savedDepart = dao.addDepartment(departmentInput);

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.converterList(savedDepart, DepartmentResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDTO> getDepartmentById(Long id) {

		log.info(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("id", id));

		Optional<Department> departmentOpt = dao.findById(id);

		if (departmentOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					"Department does not exists", requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder().data(Arrays.asList(departmentOpt.get())).build();

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
			responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_FETCH_RESOURCE_MSG, requestId.getId());

			log.error(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue("exception", e.getMessage())
					+ logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!tupleDataList.isEmpty()) {

			dataListNode.addAll(EntityUtil.jpaTuplesToListNode(tupleDataList));

		}

		if (fields != null && fields.length > 0) {

			responseDTO = SuccessDataResponseDTO.builder().data(dataListNode).page(page).limit(limit).total(total)
					.currentPageTotal(dataListNode.size()).build();
		} else {

			responseDTO = SuccessDataResponseDTO.builder().data(departments).page(page).limit(limit).total(total)
					.currentPageTotal(departments.size()).build();
		}

		log.debug(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	public ResponseEntity<ResponseDTO> updateDepartment(Long id, DepartmentInputDTO departmentInput) {

		log.info(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("departmentInput", departmentInput));

		// id validation
		Optional<Department> optionalDept = dao.findById(id);
		if (optionalDept.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					"Sorry! Department doesn't exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		// name validation
		if (Optional.ofNullable(departmentInput.getName()).isPresent()
				&& dao.isDepartmentExist(Departments.valueOf(departmentInput.getName().get()), id).isPresent()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					departmentInput.getName().get() + " Department already exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, "updateDepartment") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		Department dept = optionalDept.get();

		if (Optional.ofNullable(departmentInput.getName()).isPresent()) {
			dept.setName(Departments.valueOf(departmentInput.getName().get()));
		}

		if (Optional.ofNullable(departmentInput.getComments()).isPresent()) {
			dept.setComments(departmentInput.getComments().get());
		}

		dept.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0));

		Department savedDepart = departmentRepository.save(dept);

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(Arrays.asList(converterConfig.converter(savedDepart, DepartmentResponseDTO.class))).build();

		log.debug(String.format(METHOD_LOG_STR, "updateDepartment") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDTO> deleteDepartments(final List<Long> ids) {

		log.info(String.format(METHOD_LOG_STR, "deleteDepartments") + logKeyValue("ids", ids));

		// validating ids
		List<Department> departments = dao.findAllByIds(ids);

		List<Long> dbIds = departments.stream().map(Department::getId).toList();

		List<Long> invalidIds = new ArrayList<Long>(ids);
		invalidIds.removeAll(dbIds);

		if (!invalidIds.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					"Department(s) doesn't exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, "deleteDepartments") + logKeyValue("responseDTO", responseDTO)
					+ logKeyValue("invalidIds", invalidIds));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		dao.deleteDepartments(departments);

		ResponseDTO responseDTO = SuccessMessageResponseDTO.builder().message("Deleted Successfully").build();

		log.debug(String.format(METHOD_LOG_STR, "deleteDepartments") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
