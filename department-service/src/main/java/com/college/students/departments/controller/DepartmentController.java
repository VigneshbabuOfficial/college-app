package com.college.students.departments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.students.departments.dto.ResponseDTO;
import com.college.students.departments.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService service;

	@GetMapping
	public ResponseEntity<ResponseDTO> getDepartments(
	// @formatter:off
	@RequestParam(name = "filter",required = false) String filterParam,
	@RequestParam(name = "sort",required = false) String sortParam, 
	@RequestParam(name = "page",required = false) Integer page,
	@RequestParam(name = "limit",required = false) Integer limit
	// @formatter:on
	) {

		log.info("method = {}, filterParam = {} , sortParam = {}, page = {}, limit = {}", "getDepartments", filterParam,
				sortParam, page, limit);

		return service.getDepartments(filterParam, sortParam, page, limit);

	}

	@GetMapping("/:id")
	public ResponseEntity<ResponseDTO> getDepartmentById(@PathVariable Long id) {
		log.info("method = {}, id = {}", "getDepartmentById", id);
		return service.getDepartmentById(id);
	}

}
