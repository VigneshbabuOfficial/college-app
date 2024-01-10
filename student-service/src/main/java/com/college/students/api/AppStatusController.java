package com.college.students.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.students.utils.EndPointConstants;

@RestController
@RequestMapping(EndPointConstants.APP_STATUS)
class AppStatusController {

	@GetMapping
	String getAppStatus() {
		return "Success !!! Student service is ready to access.";
	}
}
