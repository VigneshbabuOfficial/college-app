package com.college.departments;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/***************
 * Unit test for @Lnk DepartmentServiceApplication
 *****************/

class DepartmentServiceApplicationTests {

	@Test
	void main_should_start_app() {
		try (MockedStatic<SpringApplication> mockedSpringApp = mockStatic(SpringApplication.class)) {

			mockedSpringApp.when(() -> SpringApplication.run(DepartmentServiceApplication.class))
					.thenReturn(mock(ConfigurableApplicationContext.class));

			DepartmentServiceApplication.main(new String[] {});

			mockedSpringApp.verify(() -> SpringApplication.run(DepartmentServiceApplication.class));
		}
	}

}
