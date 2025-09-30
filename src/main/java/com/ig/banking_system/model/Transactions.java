package com.ig.banking_system.model;

import com.ig.banking_system.dto.enumerates.TransactionTypeEnum;
import com.ig.banking_system.dto.transactions.TransactionResDto;
import com.ig.banking_system.model.previlleges.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(indexes = {
		@Index(columnList = "transactionRef"),
		@Index(columnList = "transactionDate"),
		@Index(columnList = "transactionType"),
})
public class Transactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String transactionRef;
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum transactionType;
	@Column(precision = 15, scale = 2)
	private BigDecimal amount;
	private String accountNumber;
	private String fromAccountNumber;
	private String toAccountNumber;
	private Date transactionDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_by")
	private Users processBy;

	public TransactionResDto toTransactionResDto() {
		return new TransactionResDto(id, transactionRef, transactionType, amount, accountNumber, fromAccountNumber, toAccountNumber, transactionDate, processBy.toUserViewDto());
	}
}
