package com.college.faculty.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CustomStringUtil {

	/**
	 * Will format the phone number from {@code 2128425500} to
	 * {@code (212) 842-5500}
	 * 
	 */
	public static String formatPhoneNumber(String strPhoneNumber) {

		String phoneNumber = strPhoneNumber;
		if (phoneNumber.toLowerCase().indexOf("ext") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("ext"));
		}
		if (phoneNumber.toLowerCase().indexOf("x") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("x"));
		}
		if (phoneNumber.indexOf("+") == -1 || strPhoneNumber.startsWith("+1")) {
			// phoneNumber = phoneNumber.replaceAll( "[-()+. ]", "" ); /*This will remove
			// only -()+. characters*/
			phoneNumber = phoneNumber.replaceAll("[^0-9]", "");/* To Remove all the special characters and alphabets */
			if (StringUtils.isNotBlank(phoneNumber)) {
				if (phoneNumber.charAt(0) == '1') {
					phoneNumber = phoneNumber.substring(1, phoneNumber.length());
				}
				if (phoneNumber.length() == 10)
					phoneNumber = "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-"
							+ phoneNumber.substring(6, phoneNumber.length());
				else {
					if (strPhoneNumber.toLowerCase().indexOf("ext") != -1
							|| strPhoneNumber.toLowerCase().indexOf("x") != -1) {
						return phoneNumber;
					} else {
						phoneNumber = strPhoneNumber.replaceAll("[^0-9]",
								"");/* To Remove all the special characters and alphabets */
					}
				}
			} else {
				if (!strPhoneNumber.toLowerCase().startsWith("x"))
					phoneNumber = strPhoneNumber;
			}
		}

		return phoneNumber;
	}

	public static String formatSPPhoneNumber(String strPhoneNumber) {

		if (isBlank(strPhoneNumber)) {

			return "";
		}

		String phoneNumber = strPhoneNumber;
		if (phoneNumber.toLowerCase().indexOf("ext") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("ext"));
		}
		if (phoneNumber.toLowerCase().indexOf("x") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("x"));
		}
		if (phoneNumber.toLowerCase().indexOf("/") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("/"));
		}
		if (phoneNumber.indexOf("+") == -1) {
			// phoneNumber = phoneNumber.replaceAll( "[-()+. ]", "" ); /*This will remove
			// only -()+. characters*/
			phoneNumber = phoneNumber.replaceAll("[^0-9]", "");/* To Remove all the special characters and alphabets */
			if (StringUtils.isNotBlank(phoneNumber)) {
				if (phoneNumber.startsWith("65")) {
					phoneNumber = phoneNumber.substring(2, phoneNumber.length());
				}

			}
		} else {
			phoneNumber = "+"
					+ phoneNumber.replaceAll("[^0-9]", "");/* To Remove all the special characters and alphabets */
		}

		if (phoneNumber.length() > 20)
			phoneNumber = "";

		return phoneNumber;
	}

	/**
	 * Method will escape the HTML, JavaScript, SQL character in a string.<br>
	 * Method is ideal to use when writing a string into database or being used in
	 * SQL queries
	 * 
	 */
	/*
	 * public static String escapeStringToNull(String stringToEscape) {
	 * 
	 * String escapedString = null;
	 * 
	 * if (StringUtils.isBlank(stringToEscape)) {
	 * 
	 * return escapedString;
	 * 
	 * }
	 * 
	 * escapedString = stringToEscape.trim();
	 * 
	 * if ("null".equals(escapedString)) {
	 * 
	 * return null; }
	 * 
	 * escapedString = StringEscapeUtils.escapeHtml(escapedString); escapedString =
	 * StringEscapeUtils.escapeJavaScript(escapedString); escapedString =
	 * StringEscapeUtils.escapeSql(escapedString);
	 * 
	 * return escapedString; }
	 */

	public static String formatUKPhoneNumber(String strPhoneNumber) {

		if (isBlank(strPhoneNumber)) {

			return "";
		}

		String phoneNumber = strPhoneNumber;
		if (phoneNumber.toLowerCase().indexOf("ext") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("ext"));
		}
		if (phoneNumber.toLowerCase().indexOf("x") != -1) {
			phoneNumber = phoneNumber.substring(0, phoneNumber.toLowerCase().indexOf("x"));
		}
		if (phoneNumber.indexOf("+") == -1) {
			// phoneNumber = phoneNumber.replaceAll( "[-()+. ]", "" ); /*This will remove
			// only -()+. characters*/
			phoneNumber = phoneNumber.replaceAll("[^0-9]", "");/* To Remove all the special characters and alphabets */
			if (StringUtils.isNotBlank(phoneNumber)) {
				if (phoneNumber.startsWith("44")) {
					phoneNumber = phoneNumber.substring(2, phoneNumber.length());
				}
				/*
				 * if ( phoneNumber.length() == 10 ) phoneNumber = "(" + phoneNumber.substring(
				 * 0, 3 ) + ") " + phoneNumber.substring( 3, 6 ) + "-" + phoneNumber.substring(
				 * 6, phoneNumber.length() ); else phoneNumber = strPhoneNumber.replaceAll(
				 * "[^0-9]", "" );
				 *
				 *//* To Remove all the special characters and alphabets */
			} else {
				phoneNumber = strPhoneNumber;
			}
		}

		if (phoneNumber.length() > 20)
			phoneNumber = "";

		return phoneNumber;
	}

	public static String formatUSPhoneNumber(String strPhoneNumber) {

		if (isBlank(strPhoneNumber)) {

			return "";
		}

		String phoneNumber = formatPhoneNumber(strPhoneNumber);

		if (phoneNumber.replaceAll("[^0-9]", "").length() < 7 || phoneNumber.length() > 20)
			phoneNumber = "";

		return phoneNumber;
	}

	public static String generateCrmGenkey(String accountName, String contactName, String phone, String email) {

		StringBuilder genkeyText = new StringBuilder(StringUtils.isNotBlank(accountName) ? accountName : "");
		genkeyText.append(StringUtils.isNotBlank(contactName) ? contactName : "");
		genkeyText.append(StringUtils.isNotBlank(phone) ? phone : "");
		genkeyText.append(StringUtils.isNotBlank(email) ? email : "");

		UUID uid = UUID
				.nameUUIDFromBytes(genkeyText.toString().replaceAll("[^a-zA-Z0-9]", "").toLowerCase().getBytes());

		return "crmgenkey_" + uid.toString();
	}

	/**
	 * Checks if a String is whitespace, empty (""), "null", "NULL" or null.
	 * 
	 * <pre>
	 *  ClStringUtil.isBlank(null)      = true
	 *  ClStringUtil.isBlank("null")    = true
	 *  ClStringUtil.isBlank("NULL")    = true
	 *  ClStringUtil.isBlank("")        = true
	 *  ClStringUtil.isBlank(" ")       = true
	 *  ClStringUtil.isBlank("bob")     = false
	 *  ClStringUtil.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 */
	public static boolean isBlank(String str) {

		if ("null".equalsIgnoreCase(StringUtils.trimToNull(str))) {

			return true;
		}

		if ("\"\"".equalsIgnoreCase(StringUtils.trimToNull(str))) {

			return true;
		}

		if ("\"null\"".equalsIgnoreCase(StringUtils.trimToNull(str))) {

			return true;
		}

		return StringUtils.isBlank(str);
	}

	/**
	 * @param value
	 * @return boolean by checking the input value is nonObject
	 */
	public static boolean isNonObject(Object value) {

		return value instanceof String || value instanceof Number || value instanceof Boolean
				|| value instanceof Character || value instanceof LocalDateTime || value instanceof LocalDate
				|| value instanceof LocalTime;

	}

	/**
	 * Checks if a String is not whitespace, empty (""), "null", "NULL" or null.
	 * 
	 * <pre>
	 *  ClStringUtil.isNotBlank(null)      = false
	 *  ClStringUtil.isNotBlank("null")    = false
	 *  ClStringUtil.isNotBlank("NULL")    = false
	 *  ClStringUtil.isNotBlank("")        = false
	 *  ClStringUtil.isNotBlank(" ")       = false
	 *  ClStringUtil.isNotBlank("bob")     = true
	 *  ClStringUtil.isNotBlank("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {

		return !isBlank(str);
	}

	/**
	 * To check string JSON is validArray
	 * 
	 * @param json
	 * @return true/false
	 */
	public static boolean isValidArray(String json) {

		try {
			JsonNode jsonNode = new ObjectMapper().readTree(json);
			return jsonNode.isArray();
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * To check string JSON is valid
	 * 
	 * @param json
	 * @return true/false
	 */
	public static boolean isValidJson(String json) {

		try {
			JsonNode jsonNode = new ObjectMapper().readTree(json);
			return jsonNode.isArray() || jsonNode.isObject();
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * To check string JSON is validObject
	 * 
	 * @param json
	 * @return true/false
	 */
	public static boolean isValidObject(String json) {

		try {
			JsonNode jsonNode = new ObjectMapper().readTree(json);
			return jsonNode.isObject();
		} catch (Exception e) {
			return false;
		}

	}

	public static String makeValidObjectToStringOrNull(Object obj) {

		if (obj == null) {
			return null;
		}

		String validString = null;

		String str = obj.toString();

		if (isBlank(str)) {

			return validString;

		}

		validString = str.trim();

		if ("null".equals(validString)) {

			return null;
		}

		return validString;
	}

	/**
	 * Check if the map having key & valid value or not.if it's having valid string
	 * it will return otherwise it will return defaultValue.
	 */
	public static String makeValidString(Map<?, ?> map, String key, String defaultValue) {

		return map.containsKey(key) && isNotBlank(String.valueOf(map.get(key))) ? String.valueOf(map.get(key))
				: defaultValue;
	}

	/**
	 * Check if the objectNode having key & valid value or not.if it's having valid
	 * string it will return otherwise it will return empty.
	 */
	public static String makeValidString(ObjectNode objectNode, String key) {

		return objectNode.hasNonNull(key) && isNotBlank(String.valueOf(objectNode.get(key)))
				? String.valueOf(objectNode.get(key).asText())
				: Constants.EMPTY_STR;
	}

	/**
	 * Check if the objectNode having key & valid value or not.if it's having valid
	 * string it will return otherwise it will return defaultValue.
	 */
	public static String makeValidString(ObjectNode objectNode, String key, String defaultValue) {

		return objectNode.hasNonNull(key) && isNotBlank(String.valueOf(objectNode.get(key)))
				? String.valueOf(objectNode.get(key).asText())
				: defaultValue;
	}

	/**
	 * Check if the String is valid value or not.if it's having valid string it will
	 * return otherwise it will return defaultValue.
	 */
	public static String makeValidString(String value, String defaultValue) {

		return (Objects.isNull(value) || isBlank(value) || String.valueOf(value).trim().equals("null")) ? defaultValue
				: value.trim();
	}

	public static String makeValidStringToNull(String str) {

		String validString = null;

		if (StringUtils.isBlank(str)) {

			return validString;

		}

		validString = str.trim();

		if ("null".equals(validString)) {

			return null;
		}

		return validString;
	}

	/*
	 * Will mask input string with "X" leaving last four characters
	 * 
	 * @param str {@code String} to mask
	 * 
	 * @return {@code String} masked
	 * 
	 * @since Version: 11.0<br>Created On: Oct 6, 2022
	 */
	public static String maskIt(String str) {

		return str.replaceAll(".(?=.{4})", "X");
	}

	/**
	 * Check if the ObjectNode having valid string or not.if it's having valid
	 * string it will return otherwise it will return default value.
	 */
	public static String nodeToEmptyString(ObjectNode objectNode, String key, String defaultString) {

		return objectNode.hasNonNull(key) && isNotBlank(objectNode.get(key).asText()) ? objectNode.get(key).asText()
				: defaultString;
	}

	/**
	 * Check if the Object having valid string or not.if it's having valid string it
	 * will return otherwise it will return null.
	 */
	public static String nullSafeToString(Object obj) {

		return Objects.nonNull(obj) ? obj.toString() : null;
	}

	/**
	 * Check if the Object having valid string or not.if it's having valid string it
	 * will return otherwise it will return default value.
	 */
	public static String nullSafeToString(Object obj, String defaultString) {

		return Objects.nonNull(obj) ? obj.toString() : defaultString;
	}

	/**
	 * To convert the string to Integer
	 * 
	 */
	public static Integer parseInt(String numberString) {

		try {
			return (StringUtils.isBlank(numberString) ? 0 : Integer.parseInt(numberString.trim()));
		} catch (NumberFormatException ne) {
			return -1;
		}
	}

	public static String titleCaseConversion(String inputString) {

		if (StringUtils.isBlank(inputString)) {
			return "";
		}

		if (StringUtils.length(inputString) == 1 || inputString.split("_").length == 1) {
			return inputString;
		}

		StringBuffer resultPlaceHolder = new StringBuffer(inputString.length());

		String[] data = inputString.split("_");

		int i = 0;
		for (String stringPart : data) {

			char[] charArray = stringPart.toLowerCase().toCharArray();

			if (i != 0)
				charArray[0] = Character.toUpperCase(charArray[0]);

			resultPlaceHolder.append(new String(charArray)).append("");

			i++;
		}

		return StringUtils.trim(resultPlaceHolder.toString());
	}

	public static boolean validString(String input) {

		return !isBlank(input) && !"-".equalsIgnoreCase(input) && !"#".equalsIgnoreCase(input);
	}

	// Private constructor as all the methods can statically access
	private CustomStringUtil() {

	}
}
