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
import com.college.students.utils.Constants;
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
			@RequestParam(name = Constants.FILTER,required = false) String[] filter,
			@RequestParam(name = Constants.OR_FILTER,required = false) String[] orFilter,
			@RequestParam(name = Constants.SORT,required = false) String[] sort, 
			@RequestParam(name = Constants.PAGE,required = false,defaultValue = Constants.DEFAULT_PAGE_SIZE) int page,
			@RequestParam(name = Constants.LIMIT,required = false, defaultValue = Constants.DEFAULT_LIMIT_SIZE) int limit,
			@RequestParam(name = Constants.FIELDS,required = false) String[] fields
	// @formatter:on
	) {
		log.info(
				String.format(METHOD_LOG_STR, "getAllStudents") + logKeyValue(Constants.FILTER, Arrays.toString(filter))
						+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
						+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.PAGE, page)
						+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		return studentService.getStudents(filter, orFilter, sort, page, limit, fields);

	}

	/*
	 * @GetMapping(EndPointConstants.ID) public ResponseEntity<ResponseDTO>
	 * getStudentById(@PathVariable final Long id) {
	 * log.info(String.format(METHOD_LOG_STR, "getStudentById") + logKeyValue("id",
	 * id)); return service.getDepartmentById(id); }
	 */

}
