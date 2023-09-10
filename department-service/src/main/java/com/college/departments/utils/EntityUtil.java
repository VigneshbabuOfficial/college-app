package com.college.departments.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import com.college.departments.enums.Departments;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntityUtil {

	private static final String DATE_TIME_FORMATTER = "[yyyy-MM-dd'T'HH:mm:s][yyyy-MM-dd'T'HH:mm:s'Z'][yyyy-MM-dd'T'HH:mm:s.SSS'Z'][yyyy-MM-dd HH:mm:ss[.SSSSSS][.SSSSS][.SSSS][.SSS][.SS][.S]][yyyy-MM-dd HH:mm:ss][yyyy-MM-dd h:mm a][MMM dd, yyyy hh:mm a][yyyy-MM-dd'T'HH:mm:ss][yyyy-MM-dd'T'HH:mm][yyyy-MM-dd HH:mm]";

	public static LocalDateTime convertLocalDateTime(String dateTimeValue) {

		return LocalDateTime.parse(dateTimeValue, DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));

	}

	public static Order getOrderbyPredicate(CriteriaBuilder criteriaBuilder, From<?, ?> from, String sortParam) {

		String column = sortParam.split(":")[0];
		String value = sortParam.split(":")[1].toUpperCase();

		switch (value) {
		case Constants.ASC:
			return criteriaBuilder.asc(from.get(column));
		case Constants.DESC:
			return criteriaBuilder.desc(from.get(column));
		default:
			throw new IllegalArgumentException("Invalid order by " + value);
		}
	}

	public static Predicate getPredicate(CriteriaBuilder criteriaBuilder, From<?, ?> from, String filter) {

		String column = filter.split(":")[0];
		String opr = filter.split(":")[1];
		String value = filter.split(":")[2];
		Object[] values = value.split("|");

		String entityName = from.getJavaType().getSimpleName();

		if (Entities.DEPARTMENT.equalsIgnoreCase(entityName)) {
			if (Constants.NAME.equalsIgnoreCase(column)) {
				return getPredicate(criteriaBuilder, from, column, opr, Departments.valueOf(value), values);
			} else
				return getPredicate(criteriaBuilder, from, column, opr, value, values);
		} else
			return getPredicate(criteriaBuilder, from, column, opr, value, values);
	}

	private static Predicate getPredicate(CriteriaBuilder criteriaBuilder, From<?, ?> from, String column, String opr,
			Object value, Object[] values) {

		switch (opr) {
		case Constants.EQUAL:
			return criteriaBuilder.equal(from.get(column), value);
		case Constants.NOT_EQUAL:
			return criteriaBuilder.notEqual(from.get(column), value);
		case Constants.GREATER_THAN:
			return criteriaBuilder.greaterThan(from.get(column), value.toString());
		case Constants.GRETER_THAN_OR_EQUAL_TO:
			return criteriaBuilder.greaterThanOrEqualTo(from.get(column), value.toString());
		case Constants.LESS_THAN:
			return criteriaBuilder.lessThan(from.get(column), value.toString());
		case Constants.LESS_THAN_OR_EQUAL_TO:
			return criteriaBuilder.lessThanOrEqualTo(from.get(column), value.toString());
		case Constants.IN:
			return from.get(column).in(values);
		case Constants.NOT_IN:
			return criteriaBuilder.not(from.get(column).in(values));
		case Constants.ILIKE:
			return criteriaBuilder.like(criteriaBuilder.lower(from.get(column)), value.toString().toLowerCase());
		case Constants.NOT_ILIKE:
			return criteriaBuilder.notLike(criteriaBuilder.lower(from.get(column)), value.toString().toLowerCase());
		default:
			throw new IllegalArgumentException("Unexpected value: " + opr);
		}
	}

	public static List<ObjectNode> jpaTuplesToListNode(List<Tuple> data) {

		return data.stream().map(EntityUtil::jpaTupleToNode).collect(Collectors.toList());
	}

	public static ObjectNode jpaTupleToNode(Tuple data) {

		ObjectNode resultNode = JsonNodeFactory.instance.objectNode();

		data.getElements().forEach(col -> {

			if (Objects.isNull(data.get(col)) && CustomStringUtil.isBlank(String.valueOf(data.get(col)))) { // Null/Empty
																											// Check

				resultNode.putPOJO(col.getAlias(), null);

			} else if (col.getJavaType() == LocalDateTime.class) {

				setLocalDateTime(col.getAlias(), data.get(col), resultNode);

			} else if (col.getJavaType() == LocalDate.class) {

				setLocalDate(col.getAlias(), data.get(col), resultNode);

			} else if (col.getJavaType() == LocalTime.class) {

				setLocalTime(col.getAlias(), data.get(col), resultNode);

			} else {

				resultNode.putPOJO(col.getAlias(), data.get(col));

			}
		});
		return resultNode;
	}

	/**
	 * @param key
	 * @param value
	 * @param resultNode - store the LocalDate in yyyy-MM-dd
	 */
	public static void setLocalDate(String key, Object value, ObjectNode resultNode) {

		resultNode.put(key,
				LocalDate.parse(String.valueOf(value), DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD)).toString());

	}

	public static void setLocalDateTime(String key, Object value, ObjectNode resultNode) {

		String dateTime = String.valueOf(value).length() > 19 ? String.valueOf(value).substring(0, 19)
				: String.valueOf(value);

		resultNode.putPOJO(key,
				DateTimeFormatter.ofPattern(Constants.LOCALDATETIME_FORMAT).format(convertLocalDateTime(dateTime)));

	}

	/**
	 * @param key
	 * @param value
	 * @param resultNode - store the LocalTime in HH:mm:ss
	 */
	public static void setLocalTime(String key, Object value, ObjectNode resultNode) {

		resultNode.put(key, LocalTime.parse(String.valueOf(value), DateTimeFormatter.ISO_LOCAL_TIME).toString());

	}
}
