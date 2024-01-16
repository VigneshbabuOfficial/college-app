package com.college.faculty.service;

import static com.college.faculty.utils.CustomLogger.logKeyValue;

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

import com.college.faculty.config.ConverterConfig;
import com.college.faculty.dto.ErrorResponsesDTO;
import com.college.faculty.dto.FacultyInputDTO;
import com.college.faculty.dto.FacultyResponseDTO;
import com.college.faculty.dto.ResponseDTO;
import com.college.faculty.dto.SuccessDataResponseDTO;
import com.college.faculty.dto.SuccessMessageResponseDTO;
import com.college.faculty.model.Faculty;
import com.college.faculty.model.FacultyDAO;
import com.college.faculty.model.FacultyRepository;
import com.college.faculty.utils.CommonUtil;
import com.college.faculty.utils.Constants;
import com.college.faculty.utils.CustomLogger;
import com.college.faculty.utils.EntityUtil;
import com.college.faculty.utils.ErrorCodeMessage;
import com.college.faculty.utils.ImageUtil;
import com.college.faculty.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class FacultyService {

	private static final String METHOD_LOG_STR = "FacultyService.%s()";

	private final ConverterConfig converterConfig;

	private final CustomLogger log;

	private final FacultyRepository repository;

	private final RequestId requestId;

	private final FacultyDAO facultyDAO;

	public FacultyService(final ConverterConfig converterConfig, final CustomLogger log,
			final ObjectMapper objectMapper, final FacultyRepository repository, final RequestId requestId,
			final FacultyDAO facultyDAO) {
		super();
		this.converterConfig = converterConfig;
		this.log = log;
		this.repository = repository;
		this.requestId = requestId;
		this.facultyDAO = facultyDAO;
	}

	public ResponseEntity<ResponseDTO> addFaculty(FacultyInputDTO facultyInput) {

		final String methodName = "addFaculty";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_INPUT, facultyInput));

		Optional<Faculty> facultyOptional = facultyDAO.isFacultyExist(facultyInput);

		if (facultyOptional.isPresent()) {
			String duplicateField = facultyOptional.get().getAdhaarNum()
					.equals(Long.valueOf(facultyInput.getAdhaarNum().get())) ? "Adhaar Number" : "Email";
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					duplicateField + " already exists", requestId.getId());
			log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

		Faculty facultySaved = facultyDAO.addFaculty(facultyInput);

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(facultySaved, FacultyResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDTO> getFacultyById(Long facultyId) {

		final String methodName = "getFacultyById";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_ID, facultyId));

		Optional<Faculty> facultyOpt = facultyDAO.findById(facultyId);

		if (facultyOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.FACULTY_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(facultyOpt.get(), FacultyResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> getAllFaculties(String[] filter, String[] orFilter, String[] sort, int page,
			int limit, String[] fields) {

		final String methodName = "getAllFaculties";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FILTER, Arrays.toString(filter))
				+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
				+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.PAGE, page)
				+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		List<ObjectNode> dataListNode = new ArrayList<>();
		List<Faculty> facultyList = new ArrayList<>();
		List<Tuple> tupleDataList = new ArrayList<>();

		int offset = (page - 1) * limit;

		Long total = 0L;

		ResponseDTO responseDTO = null;

		try {
			total = facultyDAO.getAllFaculties(facultyList, tupleDataList, filter, orFilter, sort, offset, limit,
					fields);
		} catch (Exception e) {
			responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_FETCH_RESOURCE_MSG, requestId.getId());

			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.EXCEPTION, e.getMessage())
					+ logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!tupleDataList.isEmpty()) {

			dataListNode.addAll(EntityUtil.jpaTuplesToListNode(tupleDataList));

		}

		if (fields != null && fields.length > 0) {

			responseDTO = SuccessDataResponseDTO.builder()
					.data(converterConfig.convertToObjectNodeList(dataListNode, FacultyResponseDTO.class)).limit(limit)
					.total(total).currentPageTotal(dataListNode.size()).build();
		} else {

			responseDTO = SuccessDataResponseDTO.builder()
					.data(converterConfig.converterList(facultyList, FacultyResponseDTO.class)).page(page).limit(limit)
					.total(total).currentPageTotal(facultyList.size()).build();
		}

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> uploadImage(Long facultyId, MultipartFile file) {

		final String methodName = "uploadImage";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_ID, facultyId)
				+ logKeyValue(Constants.FILE_NAME, file.getOriginalFilename()));

		Optional<Faculty> facultyOpt = facultyDAO.findById(facultyId);

		if (facultyOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.FACULTY_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		try {
			facultyOpt.get().setProfilePic(ImageUtil.compressImage(file.getBytes(), requestId.getId()));
		} catch (IOException e) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_UPLOAD_IMAGE_DUE_TO_INTERNAL_SERVER_ERROR_MSG, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO), e);
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		repository.save(facultyOpt.get());

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(facultyOpt.get(), FacultyResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<?> downloadImage(Long facultyId) {

		final String methodName = "downloadImage";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_ID, facultyId));

		Optional<Faculty> facultyOpt = facultyDAO.findById(facultyId);

		if (facultyOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.FACULTY_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		if (Objects.isNull(facultyOpt.get().getProfilePic())) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.IMAGE_NOT_FOUND, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		byte[] image = null;
		try {
			image = ImageUtil.decompressImage(facultyOpt.get().getProfilePic(), requestId.getId());
		} catch (Exception e) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INTERNAL_SERVER_ERROR,
					ErrorCodeMessage.FAILED_TO_DOWNLOAD_IMAGE_DUE_TO_INTERNAL_SERVER_ERROR_MSG, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO), e);
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}

	public ResponseEntity<ResponseDTO> updateFaculty(Long facultyId, FacultyInputDTO facultyInput) {

		final String methodName = "updateFaculty";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_ID, facultyId)
				+ logKeyValue(Constants.FACULTY_INPUT, facultyInput));

		Optional<Faculty> facultyOpt = facultyDAO.findById(facultyId);

		if (facultyOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.FACULTY_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		// adhaar & email validation
		if (facultyInput.getAdhaarNum().isPresent() || facultyInput.getEmail().isPresent()) {

			Optional<Faculty> studentOptional = facultyDAO.isFacultyExist(facultyId, facultyInput);

			if (studentOptional.isPresent()) {
				String duplicateField = studentOptional.get().getAdhaarNum()
						.equals(Long.valueOf(facultyInput.getAdhaarNum().get())) ? "Adhaar Number" : "Email";
				ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
						duplicateField + " already exists", requestId.getId());
				log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		}

		Faculty facultySaved = facultyDAO.updateStudent(facultyOpt.get(), facultyInput);

		ResponseDTO responseDTO = SuccessDataResponseDTO.builder()
				.data(converterConfig.convertToList(facultySaved, FacultyResponseDTO.class)).build();

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	public ResponseEntity<ResponseDTO> deleteFaculty(Long facultyId) {

		final String methodName = "deleteFaculty";

		log.info(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.FACULTY_ID, facultyId));

		Optional<Faculty> studentOpt = facultyDAO.findById(facultyId);

		if (studentOpt.isEmpty()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
					ErrorCodeMessage.FACULTY_DOES_NOT_EXIST, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}

		repository.delete(studentOpt.get());

		ResponseDTO responseDTO = SuccessMessageResponseDTO.builder().message("Deleted Successfully").build();

		log.debug(String.format(METHOD_LOG_STR, methodName) + logKeyValue(Constants.RESPONSE_DTO, responseDTO));

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
