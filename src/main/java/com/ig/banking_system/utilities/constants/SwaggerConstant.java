package com.ig.banking_system.utilities.constants;

public final class SwaggerConstant {

	// Prevent instantiation of this utility class
	private SwaggerConstant() {
		// SonarQube/Checkstyle/Good Practice suggests either throwing an exception
		// or just leaving this empty for a utility class like this.
		throw new UnsupportedOperationException("This is a constants utility class and cannot be instantiated");
	}

	public static final String TITLE = "Banking System API";
	public static final String DESCRIPTION = "Spring Boot Assignment";
	public static final String VERSION = "1.0";
	public static final String TERM_OF_SERVICE_URL = "Term of service";
	public static final String CONTACT_NAME = "Tona Orn";
	public static final String CONTACT_URL = "http://www.idealinkconsulting.com/contact-us/";
	public static final String CONTACT_EMAIL = "tona.orn@igdigital.asia";
	public static final String CONTACT_VERSION = "Apache License Version 2.0";
	public static final String CONTACT_URL_VERSION = "https://www.apache.org/licenses/LICENSE-2.0";
}