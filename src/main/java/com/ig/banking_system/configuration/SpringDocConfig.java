package com.ig.banking_system.configuration;

import com.ig.banking_system.utilities.constants.SwaggerConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

	@Bean
	public GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder()
				.group("Banking-api")
				.packagesToScan("com.ig.banking_system")
				.build();
	}

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(
						new Info()
								.title(SwaggerConstant.TITLE)
								.description(SwaggerConstant.DESCRIPTION)
								.version(SwaggerConstant.VERSION)
								.termsOfService(SwaggerConstant.TERM_OF_SERVICE_URL)
								.license(
										new License()
												.name(SwaggerConstant.CONTACT_VERSION)
												.url(SwaggerConstant.CONTACT_URL_VERSION)
								        )
								.contact(
										new Contact()
												.name(SwaggerConstant.CONTACT_NAME)
												.url(SwaggerConstant.CONTACT_URL)
												.email(SwaggerConstant.CONTACT_EMAIL)
								        )
				     )
				.components(
						new Components()
								.addSecuritySchemes(
										"Authorization",
										new SecurityScheme()
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")
												.in(SecurityScheme.In.HEADER)
								                   )
				           )
				.addSecurityItem(
						new SecurityRequirement().addList("Authorization", List.of("read", "write"))
				                );
	}
}
