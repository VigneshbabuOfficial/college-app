package com.college.students.departments.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-status")
public class AppStatusController {

	@GetMapping
	public String getAppStatus() {
		return "Success !!! College Students Department service is ready to access.";
	}
}
