package com.college.students.utils;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

public class CustomLogger implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient Logger logger;

	@Autowired
	private RequestId requestId;

	public static CustomLogger getLogger(String siteName) {

		CustomLogger customLogger = new CustomLogger();

		String logCategory;

		switch (siteName) {
		case "department-service":
			logCategory = "com.college.department.service";
			break;
		case "student-service":
			logCategory = "com.college.student.service";
			break;
		default:
			logCategory = "com.college.default";
		}

		customLogger.logger = Logger.getLogger(logCategory);

		return customLogger;

	}

	private String getValue(String value) {

		return "~> " + value;
	}

	private String getRequestId() {

		try {

			return Objects.isNull(requestId) ? "" : requestId.getId();
		} catch (Exception e) {
			return "";
		}

	}

	public void info(String message) {

		logger.log(Level.INFO, getValue("message = " + message + ", requestId = " + getRequestId()));

	}

	public void debug(Long userId, Long orgId, String message) {

		logger.log(Level.FINE, getValue("userId = " + userId + ", orgId = " + orgId + ", message = " + message
				+ ", requestId = " + getRequestId()));

	}

	public void debug(Long orgId, String message) {

		logger.log(Level.FINE,
				getValue("orgId = " + orgId + ", message = " + message + ", requestId =" + getRequestId()));
	}

	public void debug(String message) {

		logger.log(Level.FINE, getValue("message = " + message + ", requestId = " + getRequestId()));

	}

	public void warning(Long userId, Long orgId, String message) {

		logger.log(Level.WARNING, getValue("userId = " + userId + ", orgId = " + orgId + ", message = " + message
				+ ", requestId = " + getRequestId()));

	}

	public void warning(Long orgId, String message) {

		logger.log(Level.WARNING,
				getValue("orgId = " + orgId + ", message = " + message + ", requestId = " + getRequestId()));

	}

	public void warning(String message) {

		logger.log(Level.WARNING, getValue("message = " + message + ", requestId = " + getRequestId()));

	}

	public void error(Long userId, Long orgId, String message) {

		logger.log(Level.SEVERE, getValue("userId = " + userId + ", orgId = " + orgId + ", message = " + message
				+ ", requestId = " + getRequestId()));

	}

	public void error(Long orgId, String message) {

		logger.log(Level.SEVERE,
				getValue("orgId = " + orgId + " , requestId = " + getRequestId() + ", message = " + message));

	}

	public void error(String message) {

		logger.log(Level.SEVERE, getValue("message = " + message + ", requestId = " + getRequestId()));

	}

	public void info(String message, String requestId) {

		logger.log(Level.INFO, getValue("message = " + message + ", requestId = " + requestId));

	}

	public void info(Long userId, Long orgId, String message, String requestId) {

		logger.log(Level.INFO, getValue(
				"userId = " + userId + ", orgId = " + orgId + ", message = " + message + ", requestId = " + requestId));

	}

	public void debug(String message, String requestId) {

		logger.log(Level.FINE, getValue("message = " + message + ", requestId = " + requestId));

	}

	public void debug(Long userId, Long orgId, String message, String requestId) {

		logger.log(Level.FINE, getValue(
				"userId = " + userId + ", orgId = " + orgId + ", message = " + message + ", requestId = " + requestId));

	}

	public void warning(Long userId, Long orgId, String message, String requestId) {

		logger.log(Level.WARNING, getValue(
				"userId = " + userId + ", orgId = " + orgId + ", message = " + message + ", requestId = " + requestId));

	}

	public void error(Long userId, Long orgId, String message, String requestId) {

		logger.log(Level.SEVERE, getValue(
				"userId = " + userId + ", orgId = " + orgId + ", message = " + message + ", requestId = " + requestId));

	}

	public void error(String message, String requestId) {

		logger.log(Level.SEVERE, getValue("message = " + message + ", requestId = " + requestId));

	}

	public static String logKeyValue(String key, Object value) {
		return ", " + key + " = " + value;
	}

}
