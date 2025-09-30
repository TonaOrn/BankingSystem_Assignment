package com.ig.banking_system.configuration;

import com.ig.banking_system.security.JwtAuthorizationFilter;
import com.ig.banking_system.security.CustomAccessDeniedHandler;
import com.ig.banking_system.security.CustomAuthenticationEntryPoint;
import com.ig.banking_system.utilities.constants.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

		private final CustomAuthenticationEntryPoint authenticationEntryPoint;
		private final CustomAccessDeniedHandler accessDeniedHandler;
		private final JwtAuthorizationFilter jwtAuthorizationFilter;

		List<String> whiteListEndpoint = List.of(Constant.MAIN_PATH + "/auth/login", "/api-docs/**", "/swagger-ui/**");

		@Bean
		public PasswordEncoder bCryptPasswordEncoder() {
				return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
				http.csrf(AbstractHttpConfigurer::disable)
								.authorizeHttpRequests(
												auth ->
																auth.requestMatchers(whiteListEndpoint.toArray(new String[0])).permitAll()
																				.anyRequest().authenticated()
								)
								.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
								.exceptionHandling(
												exception -> exception.authenticationEntryPoint(authenticationEntryPoint)
																.accessDeniedHandler(accessDeniedHandler)
								)
								.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
								.httpBasic(AbstractHttpConfigurer::disable)
								.formLogin(AbstractHttpConfigurer::disable);
				return http.build();
		}
}
