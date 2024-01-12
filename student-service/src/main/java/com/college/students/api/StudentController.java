package com.college.students.api;

import static com.college.students.utils.CustomLogger.logKeyValue;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.college.students.dto.Create;
import com.college.students.dto.ErrorResponsesDTO;
import com.college.students.dto.ResponseDTO;
import com.college.students.dto.StudentInputDTO;
import com.college.students.dto.Update;
import com.college.students.service.StudentService;
import com.college.students.utils.CommonUtil;
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

	@GetMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> getStudentById(@PathVariable(name = Constants.ID) final Long studentId) {
		log.info(String.format(METHOD_LOG_STR, "getStudentById") + logKeyValue(Constants.STUDENT_ID, studentId));
		return studentService.getStudentById(studentId);
	}

	@PostMapping(path = EndPointConstants.ID + EndPointConstants.UPLOAD_IMAGE, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO> uploadImage(@PathVariable(name = Constants.ID) final Long studentId,
			@RequestParam(name = Constants.IMAGE, required = true) MultipartFile file) {
		log.info(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.STUDENT_ID, studentId)
				+ logKeyValue(Constants.FILE_NAME, file.getOriginalFilename()));
		return studentService.uploadImage(studentId, file);
	}

	@GetMapping(path = EndPointConstants.ID + EndPointConstants.DOWNLOAD_IMAGE)
	public ResponseEntity<?> downloadImage(@PathVariable(name = Constants.ID) final Long studentId) {
		log.info(String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.STUDENT_ID, studentId));
		return studentService.downloadImage(studentId);
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> addStudent(
			@Validated(Create.class) @RequestBody(required = true) StudentInputDTO studentInput,
			BindingResult bindingResult) {

		log.info(String.format(METHOD_LOG_STR, "addStudent") + logKeyValue(Constants.STUDENT_INPUT, studentInput));

		if (bindingResult.hasErrors()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildBindingResultErrors(bindingResult, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "addStudent") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		return studentService.addStudent(studentInput);

	}

	@PutMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> updateStudent(@PathVariable(name = Constants.ID) Long studentId,
			@Validated(Update.class) @RequestBody(required = true) StudentInputDTO studentInput,
			BindingResult bindingResult) {

		log.info(String.format(METHOD_LOG_STR, "updateStudent") + logKeyValue(Constants.STUDENT_ID, studentId)
				+ logKeyValue(Constants.STUDENT_INPUT, studentInput));

		if (bindingResult.hasErrors()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildBindingResultErrors(bindingResult, requestId.getId());
			log.error(
					String.format(METHOD_LOG_STR, "updateStudent") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		return studentService.updateStudent(studentId, studentInput);
	}

	@DeleteMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> deleteStudent(@PathVariable(name = Constants.ID) Long studentId) {
		log.info(String.format(METHOD_LOG_STR, "deleteStudent") + logKeyValue(Constants.STUDENT_ID, studentId));
		return studentService.deleteStudent(studentId);
	}

}
