package com.ig.banking_system.dto.transactions;

import com.ig.banking_system.dto.enumerates.TransactionTypeEnum;
import com.ig.banking_system.dto.previlleges.UserDto;
import com.ig.banking_system.dto.previlleges.UserViewDto;
import com.ig.banking_system.model.previlleges.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionResDto {
	private Long id;
	private String transactionRef;
	private TransactionTypeEnum transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String fromAccountNumber;
	private String toAccountNumber;
	private Date transactionDate;
	private UserViewDto processBy;
}
