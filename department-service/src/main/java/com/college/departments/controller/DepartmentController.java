package com.college.departments.controller;

import static com.college.departments.utils.CustomLogger.logKeyValue;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.college.departments.dto.Create;
import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.ErrorResponsesDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.dto.Update;
import com.college.departments.service.DepartmentService;
import com.college.departments.utils.CommonUtil;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.RequestId;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {

	private static final String METHOD_LOG_STR = "DepartmentController.%s()";

	@Autowired
	private CustomLogger log;

	@Autowired
	private DepartmentService service;

	@Autowired
	private RequestId requestId;

	@PostMapping
	public ResponseEntity<ResponseDTO> addDepartment(
			@Validated(value = Create.class) @RequestBody(required = true) DepartmentInputDTO departmentInput,
			BindingResult bindingResult) {
		log.info(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("departmentInput", departmentInput));

		if (bindingResult.hasErrors()) {
			ErrorResponsesDTO responseDTO = CommonUtil.buildBindingResultErrors(bindingResult, requestId.getId());
			log.error(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("responseDTO", responseDTO));
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}

//		return null;
		return service.addDepartment(departmentInput);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDTO> getDepartmentById(@PathVariable Long id) {
		log.info(String.format(METHOD_LOG_STR, "getDepartmentById") + logKeyValue("id", id));
		return service.getDepartmentById(id);
	}

	@GetMapping
	public ResponseEntity<ResponseDTO> getDepartments(
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

		return service.getDepartments(filterParams, orFilterParams, sortParams, page, limit, fields);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<ResponseDTO> updateDepartment(@PathVariable Long id,
			@Validated(value = Update.class) @RequestBody(required = true) DepartmentInputDTO departmentInput) {
		log.info(String.format(METHOD_LOG_STR, "updateDepartment") + logKeyValue("id", id)
				+ logKeyValue("departmentInput", departmentInput));
		return service.updateDepartment(id, departmentInput);
	}

	@DeleteMapping(path = "/{ids}")
	public ResponseEntity<ResponseDTO> deleteDepartments(@PathVariable List<Long> ids) {
		log.info(String.format(METHOD_LOG_STR, "deleteDepartments") + logKeyValue("ids", ids));
		return service.deleteDepartments(ids);
	}

}
