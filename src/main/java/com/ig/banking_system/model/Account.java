package com.ig.banking_system.model;

import com.ig.banking_system.base.model.BaseEntity;
import com.ig.banking_system.dto.account.AccountViewDto;
import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String accountHolderName;
	private String accountHolderEmail;
	private String accountHolderPhone;
	private String nationalId;
	private String accountNumber;
	@Enumerated(EnumType.STRING)
	private AccountTypeEnum accountType;
	@Column(precision = 15, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;
	private Boolean active;

	public AccountViewDto toAccountView() {
		return new AccountViewDto(id, accountHolderName, accountHolderEmail, accountHolderPhone, nationalId, accountNumber, accountType, balance, active, this.getCreatedBy().toUserViewDto(), getCreatedDate());
	}

	public void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	public void withdraw(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}
}
