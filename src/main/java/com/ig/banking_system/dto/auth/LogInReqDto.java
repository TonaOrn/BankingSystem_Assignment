package com.ig.banking_system.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LogInReqDto(@NotBlank String username, @NotBlank String password) {
}
