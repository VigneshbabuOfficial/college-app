package com.college.faculty.model;

import static com.college.faculty.utils.CustomLogger.logKeyValue;

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

import com.college.faculty.dto.FacultyInputDTO;
import com.college.faculty.utils.Constants;
import com.college.faculty.utils.CustomLogger;
import com.college.faculty.utils.EntityUtil;

@Repository
public class FacultyDAO {

	private static final String METHOD_LOG_STR = "FacultyDAO.%s()";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomLogger log;

	@Autowired
	private FacultyRepository repository;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Long getAllFaculties(List<Faculty> dataList, List<Tuple> tupleDataList, String[] filter, String[] orFilter,
			String[] sort, int offset, int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getAllFaculties")
				+ logKeyValue(Constants.FILTER, Arrays.toString(filter))
				+ logKeyValue(Constants.OR_FILTER, Arrays.toString(orFilter))
				+ logKeyValue(Constants.SORT, Arrays.toString(sort)) + logKeyValue(Constants.OFFSET, offset)
				+ logKeyValue(Constants.LIMIT, limit) + logKeyValue(Constants.FIELDS, Arrays.toString(fields)));

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaBuilder countCriteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteriaQuery;

		if (fields != null && fields.length > 0) {
			criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		} else {
			criteriaQuery = criteriaBuilder.createQuery(Faculty.class);
		}

		CriteriaQuery<Long> countCriteriaQuery = countCriteriaBuilder.createQuery(Long.class);

		Root<Faculty> dataListRoot = criteriaQuery.from(Faculty.class);
		Root<Faculty> countdataListRoot = countCriteriaQuery.from(Faculty.class);

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

			dataList.addAll((Collection<? extends Faculty>) entityManager.createQuery(criteriaQuery)
					.setMaxResults(limit).setFirstResult(offset).getResultList());

		}

		countCriteriaQuery.select(countCriteriaBuilder.count(countdataListRoot));
		countCriteriaQuery.where(countPredicates.toArray(new Predicate[countPredicates.size()]));

		return entityManager.createQuery(countCriteriaQuery).getSingleResult();
	}

	public Optional<Faculty> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Faculty> isFacultyExist(FacultyInputDTO facultyInput) {
		return repository.findFirstByAdhaarNumOrEmail(Long.valueOf(facultyInput.getAdhaarNum().get()),
				facultyInput.getEmail().get());
	}

	public Faculty addFaculty(FacultyInputDTO facultyInput) {

		// @formatter:off
		Faculty newFaculty = Faculty.builder()
				.name(facultyInput.getName().get())
				.fatherName(facultyInput.getFatherName().get())
				.address(facultyInput.getAddress().get())
				.adhaarNum(Long.valueOf(facultyInput.getAdhaarNum().get()))
				.dob(LocalDate.parse(facultyInput.getDob().get()))
				.contactNum(facultyInput.getContactNum().get())
				.email(facultyInput.getEmail().get())
				.comments(Optional.ofNullable(facultyInput.getComments()).isPresent() ? facultyInput.getComments().get() : null)
				.createdAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0))
				.build();
		// @formatter:on

		return repository.save(newFaculty);
	}

	public Optional<Faculty> isFacultyExist(Long facultyId, FacultyInputDTO facultyInput) {
		if (facultyInput.getAdhaarNum().isPresent() && facultyInput.getEmail().isPresent()) {
			return repository.findFirstByAdhaarNumOrEmailAndIdNot(Long.valueOf(facultyInput.getAdhaarNum().get()),
					facultyInput.getEmail().get(), facultyId);
		} else if (facultyInput.getAdhaarNum().isPresent()) {
			return repository.findFirstByAdhaarNumAndIdNot(Long.valueOf(facultyInput.getAdhaarNum().get()), facultyId);
		} else {
			return repository.findFirstByEmailAndIdNot(facultyInput.getEmail().get(), facultyId);
		}
	}

	public Faculty updateStudent(Faculty faculty, FacultyInputDTO facultyInput) {

		if (Optional.ofNullable(facultyInput.getName()).isPresent()) {
			faculty.setName(facultyInput.getName().get());
		}

		if (Optional.ofNullable(facultyInput.getFatherName()).isPresent()) {
			faculty.setFatherName(facultyInput.getFatherName().get());
		}

		if (Optional.ofNullable(facultyInput.getAddress()).isPresent()) {
			faculty.setAddress(facultyInput.getAddress().get());
		}

		if (Optional.ofNullable(facultyInput.getAdhaarNum()).isPresent()) {
			faculty.setAdhaarNum(Long.valueOf(facultyInput.getAdhaarNum().get()));
		}

		if (Optional.ofNullable(facultyInput.getDob()).isPresent()) {
			faculty.setDob(LocalDate.parse(facultyInput.getDob().get()));
		}

		if (Optional.ofNullable(facultyInput.getContactNum()).isPresent()) {
			faculty.setContactNum(facultyInput.getContactNum().get());
		}

		if (Optional.ofNullable(facultyInput.getEmail()).isPresent()) {
			faculty.setEmail(facultyInput.getEmail().get());
		}

		if (Optional.ofNullable(facultyInput.getComments()).isPresent()) {
			faculty.setComments(facultyInput.getComments().get());
		}

		faculty.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")).withNano(0));

		return repository.save(faculty);
	}

}
