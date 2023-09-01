package com.college.departments.utils;

import java.time.Instant;
import java.util.UUID;

public class RequestId {

	private String id;

	public RequestId() {

		super();
		this.id = UUID.nameUUIDFromBytes(Long.toString(Instant.now().toEpochMilli()).getBytes()).toString();
	}

	public RequestId(String id) {

		super();
		this.id = id;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	@Override
	public String toString() {

		return id;
	}

}
