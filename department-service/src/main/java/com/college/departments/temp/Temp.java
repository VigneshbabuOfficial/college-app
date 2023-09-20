package com.college.departments.temp;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class Temp {

	public ObjectNode testNode(ObjectNode input) throws Exception {
		input.put("testNode", "testNode");
		throw new Exception("testNode exception");
	}

	public int testNode(int input) throws Exception {
		throw new Exception("testNode exception");
	}

}
