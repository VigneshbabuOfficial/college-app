package com.college.departments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.college.departments.config.ConverterConfig;
import com.college.departments.dao.DepartmentDAO;
import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.dto.ErrorResponsesDTO;
import com.college.departments.dto.ResponseDTO;
import com.college.departments.dto.SuccessDataResponseDTO;
import com.college.departments.entity.Department;
import com.college.departments.enums.Departments;
import com.college.departments.repository.DepartmentRepository;
import com.college.departments.temp.Temp;
import com.college.departments.utils.CommonUtil;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.ErrorCodeMessage;
import com.college.departments.utils.RequestId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.SneakyThrows;

/*************** Unit test for @DepartmentService *****************/
class DepartmentServiceTest {

	private ConverterConfig converterConfig;

	private DepartmentDAO dao;

	private DepartmentRepository departmentRepository;

	private CustomLogger log;

	private RequestId requestId;

	private ObjectMapper objectMapper;

	private Temp temp;

	private DepartmentService service;

	MockedStatic<RequestContextHolder> requestContextHolderMockedStatic;
	private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
	private final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

	@BeforeEach
	void setup() {
		converterConfig = mock(ConverterConfig.class);
		dao = mock(DepartmentDAO.class);
		departmentRepository = mock(DepartmentRepository.class);
		log = mock(CustomLogger.class);
		requestId = mock(RequestId.class);
		objectMapper = mock(ObjectMapper.class);
		temp = mock(Temp.class);
		service = new DepartmentService(converterConfig, dao, departmentRepository, log, requestId, objectMapper, temp);

		// for session data validation
		requestContextHolderMockedStatic = mockStatic(RequestContextHolder.class);
		final ServletRequestAttributes mockServletRequestAttributes = mock(ServletRequestAttributes.class);
		requestContextHolderMockedStatic.when(RequestContextHolder::currentRequestAttributes)
				.thenReturn(mockServletRequestAttributes);
		when(mockServletRequestAttributes.getRequest()).thenReturn(mockHttpServletRequest);
		when(mockServletRequestAttributes.getResponse()).thenReturn(mockHttpServletResponse);
	}

	@AfterEach
	public void tearDown() {
		requestContextHolderMockedStatic.close();
	}

	// session data test example
	/*
	 * @SneakyThrows
	 * 
	 * @Test void bitbucketPostInstallation_should_redirect_to_teamserver() {
	 * 
	 * final String code = RandomStringUtils.randomAlphanumeric(18); final
	 * Optional<UUID> orgId = Optional.of(UUID.randomUUID()); final ScmType scmType
	 * = ScmType.BITBUCKET;
	 * 
	 * final MockHttpSession mockHttpSession = new MockHttpSession();
	 * mockHttpSession.setAttribute( Constants.TEAMSERVER_URL_KEY,
	 * "https://teamserver-page.contsec.com");
	 * mockHttpSession.setAttribute(Constants.ORGANIZATION_ID_KEY, orgId.get());
	 * 
	 * final ServletRequestAttributes mockServletRequestAttributes = mock();
	 * requestContextHolderMockedStatic
	 * .when(RequestContextHolder::currentRequestAttributes)
	 * .thenReturn(mockServletRequestAttributes); final MockHttpServletRequest
	 * mockHttpServletRequest = new MockHttpServletRequest();
	 * when(mockServletRequestAttributes.getRequest()).thenReturn(
	 * mockHttpServletRequest);
	 * 
	 * final String expectedRedirectUrl =
	 * scmPostInstallationApiDelegateImpl.buildTeamServerRedirect(orgId, scmType,
	 * code);
	 * 
	 * mockHttpServletRequest.setSession(mockHttpSession);
	 * 
	 * final ResponseEntity<Void> response =
	 * scmPostInstallationApiDelegateImpl.scmPostInstallationRedirect(scmType,
	 * code);
	 * 
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND); final var
	 * location = response.getHeaders().getLocation();
	 * assertThat(location).isNotNull().hasToString(expectedRedirectUrl); }
	 */

	@Test
	@SneakyThrows
	void addDepartment_should_create_new_department() {

		// inputs
		DepartmentInputDTO inputs = new DepartmentInputDTO();
		inputs.setName(Optional.of(Departments.EEE.name()));
		inputs.setComments(Optional.of("TEST COMMENT"));

		// mock methods
		when(dao.isDepartmentExist(any(Departments.class), eq(null))).thenReturn(Optional.empty());

		Department newDepartment = Department.builder().name(Departments.valueOf(inputs.getName().get()))
				.comments(inputs.getComments().get()).createdAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0))
				.id(100L).build();
		when(dao.addDepartment(eq(inputs))).thenReturn(newDepartment);
		ResponseDTO newResponseDTO = SuccessDataResponseDTO.builder().data(Arrays.asList(newDepartment)).build();
		when(converterConfig.converter(eq(newDepartment), any())).thenReturn(newResponseDTO);

		final OngoingStubbing<Integer> thenReturn = when(temp.testNode(anyInt())).thenReturn(2023);
		System.out.println("thenReturn = " + thenReturn);
		final OngoingStubbing<ObjectNode> thenReturn2 = when(temp.testNode(any())).thenReturn(null);
		System.out.println("thenReturn2 = " + thenReturn2);

		// expected response
		ResponseDTO responseDTO = SuccessDataResponseDTO.builder().data(Arrays.asList(newResponseDTO)).build();
		ResponseEntity<ResponseDTO> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

		// assert the results
		ResponseEntity<ResponseDTO> response = service.addDepartment(inputs);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		// assertThat(response.getBody()).isEqualTo(expectedResponse.getBody());
	}

	@Test
	@SneakyThrows
	void addDepartment_should_return_error_response_for_invalid_department_name() {

		// inputs
		DepartmentInputDTO inputs = new DepartmentInputDTO();
		inputs.setName(Optional.of(Departments.EEE.name()));
		inputs.setComments(Optional.of("TEST COMMENT"));

		// mock methods
		Department newDepartment = Department.builder().name(Departments.valueOf(inputs.getName().get()))
				.comments(inputs.getComments().get()).createdAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0))
				.id(100L).build();
		when(dao.isDepartmentExist(any(Departments.class), eq(null))).thenReturn(Optional.of(newDepartment));

		// expected response
		ErrorResponsesDTO responseDTO = CommonUtil.buildErrorResponse(ErrorCodeMessage.INVALID_DATA,
				newDepartment.getName() + " Department already exists", requestId.getId());
		ResponseEntity<ResponseDTO> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);

		// assert the results
		ResponseEntity<ResponseDTO> response = service.addDepartment(inputs);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(((ErrorResponsesDTO) response.getBody()).getErrors().get(0).getErrorCode())
				.isEqualTo(ErrorCodeMessage.INVALID_DATA);
		assertThat(((ErrorResponsesDTO) response.getBody()).getErrors().get(0).getMessage())
				.isEqualTo(newDepartment.getName() + " Department already exists");
	}

}
