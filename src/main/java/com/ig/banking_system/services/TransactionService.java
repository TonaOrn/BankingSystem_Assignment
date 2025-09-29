package com.ig.banking_system.services;

import com.ig.banking_system.dto.transactions.TransactionReqDto;
import com.ig.banking_system.model.Transactions;

public interface TransactionService {
	Transactions createTransaction(TransactionReqDto req);
}
