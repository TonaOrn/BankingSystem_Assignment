package com.ig.banking_system.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawalReqDto(
		@NotBlank
		String accountNumber,
		@NotNull
		BigDecimal amount
) {
}
