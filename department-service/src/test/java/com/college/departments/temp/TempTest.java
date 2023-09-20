package com.college.departments.temp;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/*************** Unit test for @Temp *****************/
class TempTest {

	@MockBean
	private Temp temp;

	@Test
	void testNode_handle_exception() {
		assertThatThrownBy(() -> temp.testNode(anyInt())).isInstanceOf(Exception.class);
	}

	@Test
	void testNode_objectNode_handle_exception() {
		assertThatThrownBy(() -> temp.testNode(any())).isInstanceOf(Exception.class);
	}

}
