package com.ig.banking_system.dto.transfer;

import java.math.BigDecimal;
import java.util.Date;

public record TransferResDto(
		String fromAccount,
		String fromAccountHolderName,
		String toAccount,
		String toAccountHolderName,
		BigDecimal balance,
		Date transactionDate
) {
}
