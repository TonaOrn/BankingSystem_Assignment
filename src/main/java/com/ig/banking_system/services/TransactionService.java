package com.ig.banking_system.services;

import com.ig.banking_system.dto.transactions.TransactionReqDto;
import com.ig.banking_system.model.Transactions;
import org.springframework.data.domain.Page;

public interface TransactionService {
	Transactions createTransaction(TransactionReqDto req);

	Page<Transactions> getTransactionListPage(String query, String fromDate, String toDate, int page, int size);
}
