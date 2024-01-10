package com.college.students.utils;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class RequestId implements Serializable {

	private static final long serialVersionUID = 1L;

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
