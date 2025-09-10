package com.ig.banking_system.dto;

public record UserListDto(
		long id, String firstName, String lastName, String username, String email
) {
}
