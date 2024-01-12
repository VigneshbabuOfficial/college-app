package com.college.students.service;

import static com.college.students.utils.CustomLogger.logKeyValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.college.students.config.ConverterConfig;
import com.college.students.dto.ErrorResponsesDTO;
import com.college.students.dto.ResponseDTO;
import com.college.students.dto.StudentInputDTO;
import com.college.students.dto.StudentResponseDTO;
import com.college.students.dto.SuccessDataResponseDTO;
import com.college.students.model.Student;
import com.college.students.model.StudentDAO;
import com.college.students.model.StudentRepository;
import com.college.students.utils.CommonUtil;
import com.college.students.utils.Constants;
import com.college.students.utils.CustomLogger;
import com.college.students.utils.EntityUtil;
import com.college.students.utils.ErrorCodeMessage;
import com.college.students.utils.ImageUtil;
import com.college.students.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class StudentService {

	private static final String METHOD_LOG_STR = "StudentService.%s()";

	private final ConverterConfig converterConfig;

	private final CustomLogger log;

	private final StudentRepository repository;

	private final RequestId requestId;

	private final StudentDAO studentDAO;

	public StudentService(final ConverterConfig converterConfig, final CustomLogger log,
			final ObjectMapper objectMapper, final StudentRepository repository, final RequestId requestId,
			final StudentDAO studentDAO) {
		super();
		this.converterConfig = converterConfig;
		this.log = log;
		this.repository = repository;
		this.requestId = requestId;
		this.studentDAO = studentDAO;
	}

	public ResponseEntity<ResponseDTO> addStudent(StudentInputDTO studentInput) {

		log.info(String.format(METHOD_LOG_STR, "addStudent") + logKeyValue(Constants.STUDENT_INPUT, studentInput));

		Optional<Student> studentOptional = studentDAO.isStudentExist(studentInput);

		if (studentOptional.isPresent()) {
			String duplicateField = studentOptional.get().getAdhaarNum()
					.equals(Long.valueOf(studentInput.getAdhaarNum().get())) ? "Adhaar Number" : "Email";
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					duplicateField + " already exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, "addStudent") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		Student studentSaved = studentDAO.addStudent(studentInput);

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(studentSaved, StudentResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, "addStudent") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDTO> getStudentById(Long studentId) {

		log.info(String.format(METHOD_LOG_STR, "getStudentById") + logKeyValue(Constants.STUDENT_ID, studentId));

		Optional<Student> studentOpt = studentDAO.findById(studentId);

		if (studentOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.STUDENT_DOES_NOT_EXIST, requestId.getId());
			log.error(
					String.format(METHOD_LOG_STR, "getStudentById") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(studentOpt.get(), StudentResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, "getStudentById") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> getStudents(String[] filter, String[] orFilter, String[] sort, int page,
			int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getStudents") + logKeyValue(Constants.FILTER, Arrays.toString(filter))
				+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
				+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.PAGE, page)
				+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		List<ObjectNode> dataListNode = new ArrayList<>();
		List<Student> studentList = new ArrayList<>();
		List<Tuple> tupleDataList = new ArrayList<>();

		int offset = (page - 1) * limit;

		Long total = 0L;

		ResponseDTO responseDTO = null;

		try {
			total = studentDAO.getStudents(studentList, tupleDataList, filter, orFilter, sort, offset, limit, fields);
		} catch (Exception e) {
			responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_FETCH_RESOURCE_MSG, requestId.getId());

			log.error(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue(Constants.EXCEPTION, e.getMessage())
					+ logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!tupleDataList.isEmpty()) {

			dataListNode.addAll(EntityUtil.jpaTuplesToListNode(tupleDataList));

		}

		if (fields != null && fields.length > 0) {

			responseDTO = SuccessDataResponseDTO.builder()
					.data(converterConfig.convertToObjectNodeList(dataListNode, StudentResponseDTO.class)).limit(limit)
					.total(total).currentPageTotal(dataListNode.size()).build();
		} else {

			responseDTO = SuccessDataResponseDTO.builder()
					.data(converterConfig.converterList(studentList, StudentResponseDTO.class)).page(page).limit(limit)
					.total(total).currentPageTotal(studentList.size()).build();
		}

		log.debug(String.format(METHOD_LOG_STR, "getDepartments") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> uploadImage(Long studentId, MultipartFile file) {
		log.info(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.STUDENT_ID, studentId)
				+ logKeyValue(Constants.FILE_NAME, file.getOriginalFilename()));

		Optional<Student> studentOpt = studentDAO.findById(studentId);

		if (studentOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.STUDENT_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		try {
			studentOpt.get().setProfilePic(ImageUtil.compressImage(file.getBytes(), requestId.getId()));
		} catch (IOException e) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_UPLOAD_IMAGE_DUE_TO_INTERNAL_SERVER_ERROR_MSG, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO),
					e);
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		repository.save(studentOpt.get());

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(studentOpt.get(), StudentResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, "uploadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<?> downloadImage(Long studentId) {
		log.info(String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.STUDENT_ID, studentId));

		Optional<Student> studentOpt = studentDAO.findById(studentId);

		if (studentOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.STUDENT_DOES_NOT_EXIST, requestId.getId());
			log.error(
					String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		if (Objects.isNull(studentOpt.get().getProfilePic())) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.IMAGE_NOT_FOUND, requestId.getId());
			log.error(
					String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		byte[] image = null;
		try {
			image = ImageUtil.decompressImage(studentOpt.get().getProfilePic(), requestId.getId());
		} catch (Exception e) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_DOWNLOAD_IMAGE_DUE_TO_INTERNAL_SERVER_ERROR_MSG, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "downloadImage") + logKeyValue(Constants.RESPONSE_DTO, responseDTO),
					e);
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}

}
