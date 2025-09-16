package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.base.response.ObjectResponse;
import com.ig.banking_system.base.response.PageResponse;
import com.ig.banking_system.dto.account.AccountDto;
import com.ig.banking_system.dto.account.AccountViewDto;
import com.ig.banking_system.dto.account.DepositReqDto;
import com.ig.banking_system.dto.account.WithdrawalReqDto;
import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import com.ig.banking_system.model.Account;
import com.ig.banking_system.services.AccountService;
import com.ig.banking_system.utilities.constants.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@PreAuthorize("hasAuthority('CREATE_ACCOUNT')")
	@PostMapping("/register")
	public ObjectResponse<AccountViewDto> registerAccount(@Valid @RequestBody AccountDto req) {
		return new ObjectResponse<>(accountService.registerAccount(req.toAccount()));
	}

	@PreAuthorize("hasAuthority('VIEW_ALL_ACCOUNTS')")
	@GetMapping("/list")
	public PageResponse<AccountViewDto> getAccountListPage(
			@RequestParam(required = false) String query,
			@RequestParam(required = false) AccountTypeEnum accountType,
			@RequestParam(required = false) String accountNumber,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	                                                      ) {
		final var listPage = accountService.getAccountListPage(query, accountType, accountNumber, page, size);
		final var contentList = listPage.getContent().stream().map(Account::toAccountView).toList();
		return new PageResponse<>(contentList, listPage.getTotalElements());
	}

	@GetMapping("/{id}")
	public ObjectResponse<AccountViewDto> getAccount(@PathVariable long id) {
		return new ObjectResponse<>(accountService.getAccountDetail(id).toAccountView());
	}

	@PutMapping("/{id}")
	public MessageResponse updateAccount(@PathVariable long id, @Valid @RequestBody AccountDto req) {
		return accountService.updateAccount(id, req);
	}

	/**
	 * Deposit
	 **/
	@PostMapping("/deposit")
	public MessageResponse deposit(@Valid @RequestBody DepositReqDto req) {
		return accountService.deposit(req);
	}

	/**
	 * Withdrawal
	 **/
	@PostMapping("/deposit")
	public MessageResponse withdrawal(@Valid @RequestBody WithdrawalReqDto req) {
		return accountService.withdrawal(req);
	}
}
