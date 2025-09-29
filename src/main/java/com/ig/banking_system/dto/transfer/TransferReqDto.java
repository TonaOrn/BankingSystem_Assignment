package com.ig.banking_system.dto.transfer;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransferReqDto(
		@NotBlank
		String fromAccount,
		@NotBlank
		String toAccount,
		BigDecimal amount
) {
}
