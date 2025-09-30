package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.PageResponse;
import com.ig.banking_system.dto.transactions.TransactionResDto;
import com.ig.banking_system.model.Transactions;
import com.ig.banking_system.services.TransactionService;
import com.ig.banking_system.utilities.constants.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/transactions")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	@PreAuthorize("hasAuthority('VIEW_TRANSACTIONS')")
	@GetMapping("/list")
	public PageResponse<TransactionResDto> getTransactionListPage(
			@RequestParam(required = false) String query,
			@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	                                                             ) {
		final var listPage = transactionService.getTransactionListPage(query, fromDate, toDate, page, size);
		final var contentList = listPage.getContent().stream().map(Transactions::toTransactionResDto).toList();
		return new PageResponse<>(contentList, listPage.getTotalElements());
	}
}
