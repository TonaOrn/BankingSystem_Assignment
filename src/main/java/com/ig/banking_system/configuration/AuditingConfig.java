package com.ig.banking_system.configuration;

import com.ig.banking_system.model.previlleges.Users;
import com.ig.banking_system.security.payload.Context;
import com.ig.banking_system.security.payload.UserPrinciple;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
@Configuration
@EnableJpaAuditing
public class AuditingConfig implements AuditorAware<Users> {

	/**
	 * Return Current User for:
	 *
	 * @LastModifiedBy annotation
	 * @CreatedBy annotation
	 */
	@Override
	@NonNull
	public Optional<Users> getCurrentAuditor() {
		final var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		log.info("Current user: {}", auth.getPrincipal());
		final var userPrincipal = (Context) auth.getPrincipal();
		return Optional.of(new Users(userPrincipal.getId(), userPrincipal.getUsername()));
	}

	public Optional<Users> getCurrentUser() {
		final var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		final var userPrincipal = (UserPrinciple) auth.getPrincipal();
		return Optional.of(new Users(userPrincipal.getId(), userPrincipal.getUsername()));
	}
}
