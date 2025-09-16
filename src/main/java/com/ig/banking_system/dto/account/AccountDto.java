package com.ig.banking_system.dto.account;

import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import com.ig.banking_system.model.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	private long id;
	private String accountHolderName;
	@Email(message = "not a valid email format")
	private String accountHolderEmail;
	@Pattern(regexp = "\\d+")
	private String accountHolderPhone;
	private String nationalId;
	private AccountTypeEnum accountType;
	private BigDecimal balance = BigDecimal.ZERO;
	private Boolean active;

	public Account toAccount() {
		return new Account(id, accountHolderName, accountHolderEmail, accountHolderPhone, nationalId, "", accountType, balance, active);
	}

	public Account updateAccount(Account account) {
		account.setAccountHolderName(accountHolderName);
		account.setAccountHolderEmail(accountHolderEmail);
		account.setAccountHolderPhone(accountHolderPhone);
		account.setNationalId(nationalId);
		account.setAccountType(accountType);
		return account;
	}
}
