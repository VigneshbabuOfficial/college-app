package com.college.students.departments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.students.departments.dto.DepartmentInputDTO;
import com.college.students.departments.dto.ResponseDTO;
import com.college.students.departments.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private DepartmentService service;

	@GetMapping
	public ResponseEntity<ResponseDTO> getDepartments(
	// @formatter:off
	@RequestParam(name = "filter",required = false) String filterParam,
	@RequestParam(name = "sort",required = false) String sortParam, 
	@RequestParam(name = "page",required = false,defaultValue = "1") Integer page,
	@RequestParam(name = "limit",required = false, defaultValue = "10") Integer limit
	// @formatter:on
	) {

		// TODO: need to check the debug logs in console issue
		log.debug("method = {}, filterParam = {} , sortParam = {}, page = {}, limit = {}", "getDepartments",
				filterParam, sortParam, page, limit);

		return service.getDepartments(filterParam, sortParam, page, limit);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDTO> getDepartmentById(@PathVariable Long id) {
		log.debug("method = {}, id = {}", "getDepartmentById", id);
		return service.getDepartmentById(id);
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> addDepartment(@RequestBody(required = true) DepartmentInputDTO departmentInput) {
		log.debug("method = {}, departmentInput = {}", "addDepartment", departmentInput);
		return service.addDepartment(departmentInput);
	}

}
