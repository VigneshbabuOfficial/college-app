package com.college.departments.dao;

import static com.college.departments.utils.CustomLogger.logKeyValue;

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

import com.college.departments.dto.DepartmentInputDTO;
import com.college.departments.entity.Department;
import com.college.departments.repository.DepartmentRepository;
import com.college.departments.utils.CustomLogger;
import com.college.departments.utils.EntityUtil;

@Repository
public class DepartmentDAO {

	private static final String METHOD_LOG_STR = "DepartmentDAO.%s()";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomLogger log;

	@Autowired
	private DepartmentRepository repository;

	public Department addDepartment(DepartmentInputDTO departmentInput) {

		log.debug("method = addDepartment, departmentInput = " + departmentInput);

		Department newDepartment = Department.builder().name(departmentInput.getName())
				.comments(departmentInput.getComments()).build();

		return repository.save(newDepartment);
	}

	public Optional<Department> findById(Long id) {

		return repository.findById(id);
	}

	public List<Department> getDepartments() {

		return repository.findAll();
	}

	@SuppressWarnings("unchecked")
	public Long getDepartments(List<Department> dataList, List<Tuple> tupleDataList, String[] filterParams,
			String[] orFilterParams, String[] sortParams, int offset, int limit, String[] fields) {

		log.info(String.format(METHOD_LOG_STR, "getDepartments")
				+ logKeyValue("filterParams", Arrays.toString(filterParams))
				+ logKeyValue("orFilterParams", Arrays.toString(orFilterParams))
				+ logKeyValue("sortParams", Arrays.toString(sortParams)) + logKeyValue("offset", offset)
				+ logKeyValue("limit", limit) + logKeyValue("fields", Arrays.toString(fields)));

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaBuilder countCriteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteriaQuery;

		if (fields != null && fields.length > 0) {
			criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		} else {
			criteriaQuery = criteriaBuilder.createQuery(Department.class);
		}

		CriteriaQuery<Long> countCriteriaQuery = countCriteriaBuilder.createQuery(Long.class);

		Root<Department> dataListRoot = criteriaQuery.from(Department.class);
		Root<Department> countdataListRoot = countCriteriaQuery.from(Department.class);

		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();

		if (fields != null && fields.length > 0) {

			List<Selection<?>> selections = new ArrayList<>();

			Arrays.stream(fields).forEach(field -> selections.add(dataListRoot.get(field).alias(field)));

			if (!selections.isEmpty() && fields.length > 0) {
				criteriaQuery.multiselect(selections);
			}

		}

		if (filterParams != null) {
			for (String filter : filterParams) {
				predicates.add(EntityUtil.getPredicate(criteriaBuilder, dataListRoot, filter));
				countPredicates.add(EntityUtil.getPredicate(countCriteriaBuilder, countdataListRoot, filter));
			}
		}

		if (orFilterParams != null) {
			List<Predicate> orPredicates = new ArrayList<>();
			List<Predicate> countORPredicates = new ArrayList<>();
			for (String filter : orFilterParams) {
				orPredicates.add(EntityUtil.getPredicate(criteriaBuilder, dataListRoot, filter));
				countORPredicates.add(EntityUtil.getPredicate(countCriteriaBuilder, countdataListRoot, filter));
			}
			predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
			countPredicates.add(countCriteriaBuilder.or(countORPredicates.toArray(new Predicate[0])));
		}

		if (sortParams != null) {

			List<Order> orderList = new ArrayList<>();

			for (String sortParam : sortParams) {
				orderList.add(EntityUtil.getOrderbyPredicate(criteriaBuilder, dataListRoot, sortParam));
			}
			criteriaQuery.orderBy(orderList);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

		if (fields != null && fields.length > 0) {

			TypedQuery<Tuple> typedQuery = (TypedQuery<Tuple>) entityManager.createQuery(criteriaQuery);
			tupleDataList.addAll(typedQuery.setMaxResults(limit).setFirstResult(offset).getResultList());

		} else {

			dataList.addAll((Collection<? extends Department>) entityManager.createQuery(criteriaQuery)
					.setMaxResults(limit).setFirstResult(offset).getResultList());

		}

		countCriteriaQuery.select(countCriteriaBuilder.count(countdataListRoot));
		countCriteriaQuery.where(countPredicates.toArray(new Predicate[countPredicates.size()]));

		return entityManager.createQuery(countCriteriaQuery).getSingleResult();
	}

}
