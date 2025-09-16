package com.ig.banking_system.dto.auth;

public record RegisterUserDto(
		long id, String firstName, String lastName, String email, String username, String token
) {

}
