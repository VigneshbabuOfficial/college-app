package com.college.faculty.api;

import static com.college.faculty.utils.CustomLogger.logKeyValue;

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

import com.college.faculty.dto.Create;
import com.college.faculty.dto.ErrorResponsesDTO;
import com.college.faculty.dto.FacultyInputDTO;
import com.college.faculty.dto.ResponseDTO;
import com.college.faculty.dto.Update;
import com.college.faculty.service.FacultyService;
import com.college.faculty.utils.CommonUtil;
import com.college.faculty.utils.Constants;
import com.college.faculty.utils.CustomLogger;
import com.college.faculty.utils.EndPointConstants;
import com.college.faculty.utils.RequestId;

@RestController
@RequestMapping(EndPointConstants.FACULTY)
@Validated
public class FacultyController {

	private static final String METHOD_LOG_STR = "FacultyController.%s()";

	private final CustomLogger log;

	private final RequestId requestId;

	private final FacultyService facultyService;

	public FacultyController(final CustomLogger log, final RequestId requestId, final FacultyService facultyService) {
		super();
		this.log = log;
		this.requestId = requestId;
		this.facultyService = facultyService;
	}

	@GetMapping
	public ResponseEntity<ResponseDTO> getAllFaculties(
	// @formatter:off
			@RequestParam(name = Constants.FILTER,required = false) String[] filter,
			@RequestParam(name = Constants.OR_FILTER,required = false) String[] orFilter,
			@RequestParam(name = Constants.SORT,required = false) String[] sort, 
			@RequestParam(name = Constants.PAGE,required = false,defaultValue = Constants.DEFAULT_PAGE_SIZE) int page,
			@RequestParam(name = Constants.LIMIT,required = false, defaultValue = Constants.DEFAULT_LIMIT_SIZE) int limit,
			@RequestParam(name = Constants.FIELDS,required = false) String[] fields
	// @formatter:on
	) {
		log.info(String.format(METHOD_LOG_STR, "getAllFaculties")
				+ logKeyValue(Constants.FILTER, Arrays.toString(filter))
				+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
				+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.PAGE, page)
				+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		return facultyService.getAllFaculties(filter, orFilter, sort, page, limit, fields);

	}

	@GetMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> getFacultyById(@PathVariable(name = Constants.ID) final Long facultyId) {
		log.info(String.format(METHOD_LOG_STR, "getFacultyById") + logKeyValue(Constants.FACULTY_ID, facultyId));
		return facultyService.getFacultyById(facultyId);
	}

	@PostMapping(path = EndPointConstants.ID + EndPointConstants.UPLOAD_IMAGE, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO> uploadImage(@PathVariable(name = Constants.ID) final Long facultyId,
			@RequestParam(name = Constants.IMAGE, required = true) MultipartFile file) {
		log.info(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.FACULTY_ID, facultyId)
				+ logKeyValue(Constants.FILE_NAME, file.getOriginalFilename()));
		return facultyService.uploadImage(facultyId, file);
	}

	@GetMapping(path = EndPointConstants.ID + EndPointConstants.DOWNLOAD_IMAGE)
	public ResponseEntity<?> downloadImage(@PathVariable(name = Constants.ID) final Long facultyId) {
		log.info(String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.FACULTY_ID, facultyId));
		return facultyService.downloadImage(facultyId);
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> addFaculty(
			@Validated(Create.class) @RequestBody(required = true) FacultyInputDTO facultyInput,
			BindingResult bindingResult) {

		log.info(String.format(METHOD_LOG_STR, "addFaculty") + logKeyValue(Constants.FACULTY_INPUT, facultyInput));

		if (bindingResult.hasErrors()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildBindingResultErrors(bindingResult, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "addFaculty") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		return facultyService.addFaculty(facultyInput);

	}

	@PutMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> updateFaculty(@PathVariable(name = Constants.ID) Long facultyId,
			@Validated(Update.class) @RequestBody(required = true) FacultyInputDTO facultyInput,
			BindingResult bindingResult) {

		log.info(String.format(METHOD_LOG_STR, "updateFaculty") + logKeyValue(Constants.FACULTY_ID, facultyId)
				+ logKeyValue(Constants.FACULTY_INPUT, facultyInput));

		if (bindingResult.hasErrors()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildBindingResultErrors(bindingResult, requestId.getId());
			log.error(
					String.format(METHOD_LOG_STR, "updateFaculty") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		return facultyService.updateFaculty(facultyId, facultyInput);
	}

	@DeleteMapping(path = EndPointConstants.ID)
	public ResponseEntity<ResponseDTO> deleteFaculty(@PathVariable(name = Constants.ID) Long facultyId) {
		log.info(String.format(METHOD_LOG_STR, "deleteFaculty") + logKeyValue(Constants.FACULTY_ID, facultyId));
		return facultyService.deleteFaculty(facultyId);
	}

}
