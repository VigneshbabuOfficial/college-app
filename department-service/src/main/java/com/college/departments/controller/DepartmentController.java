package com.college.departments.controller;

import static com.college.departments.utils.CustomLogger.logKeyValue;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.service.DepartmentService;
import com.college.departments.utils.CustomLogger;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private static final String METHOD_LOG_STR = "DepartmentController.%s()";

	@Autowired
	private CustomLogger log;

	@Autowired
	private DepartmentService service;

	@PostMapping
	public ResponseEntity<ResponseDTO> addDepartment(@RequestBody(required = true) DepartmentInputDTO departmentInput) {
		log.info(String.format(METHOD_LOG_STR, "addDepartment") + logKeyValue("departmentInput", departmentInput));
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

}
