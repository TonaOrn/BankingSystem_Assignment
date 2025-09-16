package com.ig.banking_system.dto.account;

import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import com.ig.banking_system.dto.previlleges.UserViewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountViewDto {
	private long id;
	private String accountHolderName;
	private String accountHolderEmail;
	private String accountHolderPhone;
	private String nationalId;
	private String accountNumber;
	private AccountTypeEnum accountType;
	private BigDecimal balance = BigDecimal.ZERO;
	private Boolean active;
	private UserViewDto createdBy;
	private Date createdDate;
}
