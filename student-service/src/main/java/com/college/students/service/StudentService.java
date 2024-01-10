package com.college.students.service;

import static com.college.students.utils.CustomLogger.logKeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.college.students.config.ConverterConfig;
import com.college.students.dto.ResponseDTO;
import com.college.students.dto.SuccessDataResponseDTO;
import com.college.students.model.Student;
import com.college.students.model.StudentDAO;
import com.college.students.model.StudentRepository;
import com.college.students.utils.CommonUtil;
import com.college.students.utils.CustomLogger;
import com.college.students.utils.EntityUtil;
import com.college.students.utils.ErrorCodeMessage;
import com.college.students.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class StudentService {

	private static final String METHOD_LOG_STR = "DepartmentService.%s()";

	private final ConverterConfig converterConfig;

	private final CustomLogger log;

	private final ObjectMapper objectMapper;

	private final StudentRepository repository;

	private final RequestId requestId;

	private final StudentDAO studentDAO;

	public StudentService(final ConverterConfig converterConfig, final CustomLogger log,
			final ObjectMapper objectMapper, final StudentRepository repository, final RequestId requestId,
			final StudentDAO studentDAO) {
		super();
		this.converterConfig = converterConfig;
		this.log = log;
		this.objectMapper = objectMapper;
		this.repository = repository;
		this.requestId = requestId;
		this.studentDAO = studentDAO;
	}

	public ResponseEntity<ResponseDTO> getStudents(String[] filterParams, String[] orFilterParams, String[] sortParams,
			int page, int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getDepartments")
				+ logKeyValue("filterParams", Arrays.toString(filterParams))
				+ logKeyValue("orFilterParams", Arrays.toString(orFilterParams))
				+ logKeyValue("sortParams", Arrays.toString(sortParams)) + logKeyValue("page", page)
				+ logKeyValue("limit", limit) + logKeyValue("fields", Arrays.toString(fields)));

		List<ObjectNode> dataListNode = new ArrayList<>();
		List<Student> studentList = new ArrayList<>();
		List<Tuple> tupleDataList = new ArrayList<>();

		int offset = (page - 1) * limit;

		Long total = 0L;

		ResponseDTO responseDTO = null;

		try {
			total = studentDAO.getStudents(studentList, tupleDataList, filterParams, orFilterParams, sortParams, offset,
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

			responseDTO = SuccessDataResponseDTO.builder().data(studentList).page(page).limit(limit).total(total)
					.currentPageTotal(studentList.size()).build();
		}

		log.debug(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue("responseDTO", responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
