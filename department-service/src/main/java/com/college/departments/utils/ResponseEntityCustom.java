package com.college.departments.utils;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

public class ResponseEntityCustom<T> extends ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = -4253239319095997923L;

	public ResponseEntityCustom(HttpStatus status) {
		super(status);
	}

	public ResponseEntityCustom(@Nullable T body, HttpStatus status) {
		super(body, status);
	}

	public ResponseEntityCustom(MultiValueMap<String, String> headers, HttpStatus status) {
		super(headers, status);
	}

	public ResponseEntityCustom(@Nullable T body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
	}

}
