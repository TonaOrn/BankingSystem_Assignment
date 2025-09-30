package com.ig.banking_system.services.implementations;

import com.ig.banking_system.configuration.AuditingConfig;
import com.ig.banking_system.dto.transactions.TransactionReqDto;
import com.ig.banking_system.model.Transactions;
import com.ig.banking_system.repositories.TransactionsRepository;
import com.ig.banking_system.services.TransactionService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionsRepository transactionsRepo;
	private final AuditingConfig auditingConfig;

	@Override
	public Transactions createTransaction(TransactionReqDto req) {
		req.setTransactionRef(String.valueOf(System.currentTimeMillis()));
		if (auditingConfig.getCurrentUser().isPresent()) {
			req.setProcessBy(auditingConfig.getCurrentUser().get());
		}
		return transactionsRepo.save(req.toTransaction());
	}

	@Override
	public Page<Transactions> getTransactionListPage(String query, String fromDate, String toDate, int page, int size) {
		return transactionsRepo.findAll((root, cq, cb ) -> {
			ArrayList<Predicate> predicates = new ArrayList<>();

			if (!StringUtils.isBlank(query)) {
				var searchAccountNum = cb.like(cb.upper(root.get("accountNumber")), "%" + query.toUpperCase() + "%");
				var searchFromAccountNum = cb.like(cb.upper(root.get("fromAccountNumber")), "%" + fromDate.toUpperCase() + "%");
				var searchToAccountNum = cb.like(cb.upper(root.get("toAccountNumber")), "%" + toDate.toUpperCase() + "%");
				predicates.add(cb.or(searchAccountNum, searchFromAccountNum, searchToAccountNum));
			}

			if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
				var searchDateFunction = cb.function("TO_CHAR", String.class, root.get("transactionDate"), cb.literal("yyyy-MM-dd"));
				predicates.add(cb.between(searchDateFunction, fromDate, toDate));
			}

			Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
			return cb.and(predicates.toArray(new Predicate[0]));
		}, PageRequest.of(page, size));
	}
}
