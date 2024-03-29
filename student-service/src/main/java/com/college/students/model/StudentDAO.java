package com.college.students.model;

import static com.college.students.utils.CustomLogger.logKeyValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.college.students.dto.StudentInputDTO;
import com.college.students.utils.Constants;
import com.college.students.utils.CustomLogger;
import com.college.students.utils.EntityUtil;

@Repository
public class StudentDAO {

	private static final String METHOD_LOG_STR = "StudentDAO.%s()";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomLogger log;

	@Autowired
	private StudentRepository repository;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Long getStudents(List<Student> dataList, List<Tuple> tupleDataList, String[] filter, String[] orFilter,
			String[] sort, int offset, int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getStudents") + logKeyValue(Constants.FILTER, Arrays.toString(filter))
				+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
				+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.OFFSET, offset)
				+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaBuilder countCriteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteriaQuery;

		if (fields != null && fields.length > 0) {
			criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		} else {
			criteriaQuery = criteriaBuilder.createQuery(Student.class);
		}

		CriteriaQuery<Long> countCriteriaQuery = countCriteriaBuilder.createQuery(Long.class);

		Root<Student> dataListRoot = criteriaQuery.from(Student.class);
		Root<Student> countdataListRoot = countCriteriaQuery.from(Student.class);

		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();

		if (fields != null && fields.length > 0) {

			List<Selection<?>> selections = new ArrayList<>();

			Arrays.stream(fields).forEach(field -> selections.add(dataListRoot.get(field).alias(field)));

			if (!selections.isEmpty() && fields.length > 0) {
				criteriaQuery.multiselect(selections);
			}

		}

		if (filter != null) {
			for (String filterObj : filter) {
				predicates.add(EntityUtil.getPredicate(criteriaBuilder, dataListRoot, filterObj));
				countPredicates.add(EntityUtil.getPredicate(countCriteriaBuilder, countdataListRoot, filterObj));
			}
		}

		if (orFilter != null) {
			List<Predicate> orPredicates = new ArrayList<>();
			List<Predicate> countORPredicates = new ArrayList<>();
			for (String filterObj : orFilter) {
				orPredicates.add(EntityUtil.getPredicate(criteriaBuilder, dataListRoot, filterObj));
				countORPredicates.add(EntityUtil.getPredicate(countCriteriaBuilder, countdataListRoot, filterObj));
			}
			predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
			countPredicates.add(countCriteriaBuilder.or(countORPredicates.toArray(new Predicate[0])));
		}

		if (sort != null) {

			List<Order> orderList = new ArrayList<>();

			for (String sortParam : sort) {
				orderList.add(EntityUtil.getOrderbyPredicate(criteriaBuilder, dataListRoot, sortParam));
			}
			criteriaQuery.orderBy(orderList);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

		if (fields != null && fields.length > 0) {

			TypedQuery<Tuple> typedQuery = (TypedQuery<Tuple>) entityManager.createQuery(criteriaQuery);
			tupleDataList.addAll(typedQuery.setMaxResults(limit).setFirstResult(offset).getResultList());

		} else {

			dataList.addAll((Collection<? extends Student>) entityManager.createQuery(criteriaQuery)
					.setMaxResults(limit).setFirstResult(offset).getResultList());

		}

		countCriteriaQuery.select(countCriteriaBuilder.count(countdataListRoot));
		countCriteriaQuery.where(countPredicates.toArray(new Predicate[countPredicates.size()]));

		return entityManager.createQuery(countCriteriaQuery).getSingleResult();
	}

	public Optional<Student> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Student> isStudentExist(StudentInputDTO studentInput) {
		return repository.findFirstByAdhaarNumOrEmail(Long.valueOf(studentInput.getAdhaarNum().get()),
				studentInput.getEmail().get());
	}

	public Student addStudent(StudentInputDTO studentInput) {

		// @formatter:off
		Student newStudent = Student.builder()
				.name(studentInput.getName().get())
				.fatherName(studentInput.getFatherName().get())
				.address(studentInput.getAddress().get())
				.adhaarNum(Long.valueOf(studentInput.getAdhaarNum().get()))
				.dob(LocalDate.parse(studentInput.getDob().get()))
				.contactNum(studentInput.getContactNum().get())
				.email(studentInput.getEmail().get())
				.comments(Optional.ofNullable(studentInput.getComments()).isPresent() ? studentInput.getComments().get() : null)
				.createdAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0))
				.build();
		// @formatter:on

		return repository.save(newStudent);
	}

	public Optional<Student> isStudentExist(Long studentId, StudentInputDTO studentInput) {
		if (studentInput.getAdhaarNum().isPresent() && studentInput.getEmail().isPresent()) {
			return repository.findFirstByAdhaarNumOrEmailAndIdNot(Long.valueOf(studentInput.getAdhaarNum().get()),
					studentInput.getEmail().get(), studentId);
		} else if (studentInput.getAdhaarNum().isPresent()) {
			return repository.findFirstByAdhaarNumAndIdNot(Long.valueOf(studentInput.getAdhaarNum().get()), studentId);
		} else {
			return repository.findFirstByEmailAndIdNot(studentInput.getEmail().get(), studentId);
		}
	}

	public Student updateStudent(Student student, StudentInputDTO studentInput) {

		if (Optional.ofNullable(studentInput.getName()).isPresent()) {
			student.setName(studentInput.getName().get());
		}

		if (Optional.ofNullable(studentInput.getFatherName()).isPresent()) {
			student.setFatherName(studentInput.getFatherName().get());
		}

		if (Optional.ofNullable(studentInput.getAddress()).isPresent()) {
			student.setAddress(studentInput.getAddress().get());
		}

		if (Optional.ofNullable(studentInput.getAdhaarNum()).isPresent()) {
			student.setAdhaarNum(Long.valueOf(studentInput.getAdhaarNum().get()));
		}

		if (Optional.ofNullable(studentInput.getDob()).isPresent()) {
			student.setDob(LocalDate.parse(studentInput.getDob().get()));
		}

		if (Optional.ofNullable(studentInput.getContactNum()).isPresent()) {
			student.setContactNum(studentInput.getContactNum().get());
		}

		if (Optional.ofNullable(studentInput.getEmail()).isPresent()) {
			student.setEmail(studentInput.getEmail().get());
		}

		if (Optional.ofNullable(studentInput.getComments()).isPresent()) {
			student.setComments(studentInput.getComments().get());
		}

		student.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0));

		return repository.save(student);
	}

}
