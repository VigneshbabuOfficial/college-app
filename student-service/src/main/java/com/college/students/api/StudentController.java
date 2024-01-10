package com.college.students.api;

import static com.college.students.utils.CustomLogger.logKeyValue;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.students.dto.ResponseDTO;
import com.college.students.service.StudentService;
import com.college.students.utils.CustomLogger;
import com.college.students.utils.EndPointConstants;
import com.college.students.utils.RequestId;

@RestController
@RequestMapping(EndPointConstants.STUDENT)
@Validated
public class StudentController {

	private static final String METHOD_LOG_STR = "StudentController.%s()";

	private final CustomLogger log;

	private final RequestId requestId;

	private final StudentService studentService;

	public StudentController(final CustomLogger log, final RequestId requestId, final StudentService studentService) {
		super();
		this.log = log;
		this.requestId = requestId;
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<ResponseDTO> getAllStudents(
	// @formatter:off
			@RequestParam(name = "filter",required = false) String[] filterParams,
			@RequestParam(name = "ORfilter",required = false) String[] orFilterParams,
			@RequestParam(name = "sort",required = false) String[] sortParams, 
			@RequestParam(name = "page",required = false,defaultValue = "1") int page,
			@RequestParam(name = "limit",required = false, defaultValue = "10") int limit,
			@RequestParam(name = "fields",required = false) String[] fields
	// @formatter:on
	) {
		log.info(String.format(METHOD_LOG_STR, "getDepartments")
				+ logKeyValue("filterParams", Arrays.toString(filterParams))
				+ logKeyValue("orFilterParams", Arrays.toString(orFilterParams))
				+ logKeyValue("sortParams", Arrays.toString(sortParams)) + logKeyValue("page", page)
				+ logKeyValue("limit", limit) + logKeyValue("fields", Arrays.toString(fields)));

		return studentService.getStudents(filterParams, orFilterParams, sortParams, page, limit, fields);

	}

}
