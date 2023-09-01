package com.college.departments.controller;

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

	@Autowired
	private CustomLogger log;

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

		log.debug("method = getDepartments, filterParam = " + filterParam + " , sortParam = " + sortParam + ", page = "
				+ page + ", limit = " + limit);

		return service.getDepartments(filterParam, sortParam, page, limit);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDTO> getDepartmentById(@PathVariable Long id) {
		log.debug("method = getDepartmentById, id = " + id);
		return service.getDepartmentById(id);
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> addDepartment(@RequestBody(required = true) DepartmentInputDTO departmentInput) {
		log.debug("method = addDepartment, departmentInput = " + departmentInput);
		return service.addDepartment(departmentInput);
	}

}
