package com.college.departments.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-status")
class AppStatusController {

	@GetMapping
	String getAppStatus() {
		return "Success !!! Department service is ready to access.";
	}
}
