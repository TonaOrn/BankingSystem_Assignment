package com.ig.banking_system.services.implementations;

import com.ig.banking_system.dto.transactions.TransactionReqDto;
import com.ig.banking_system.model.Transactions;
import com.ig.banking_system.repositories.TransactionsRepository;
import com.ig.banking_system.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionsRepository transactionsRepo;


	@Override
	public Transactions createTransaction(TransactionReqDto req) {
		req.setTransactionRef(String.valueOf(System.currentTimeMillis()));
		return transactionsRepo.save(req.toTransaction());
	}
}
