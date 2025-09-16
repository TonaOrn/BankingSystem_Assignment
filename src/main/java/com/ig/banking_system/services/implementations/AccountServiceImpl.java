package com.ig.banking_system.services.implementations;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.dto.account.AccountDto;
import com.ig.banking_system.dto.account.AccountViewDto;
import com.ig.banking_system.dto.account.DepositReqDto;
import com.ig.banking_system.dto.account.WithdrawalReqDto;
import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import com.ig.banking_system.exceptions.ApiErrorException;
import com.ig.banking_system.model.Account;
import com.ig.banking_system.repositories.AccountRepository;
import com.ig.banking_system.services.AccountService;
import com.ig.banking_system.utilities.shared.Helper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	@Override
	public AccountViewDto registerAccount(Account req) {
		// Auto generate account number
		req.setAccountNumber(generateAccountNumber());
		req.setActive(true);
		final var account = accountRepository.save(req);
		return account.toAccountView();
	}

	@Override
	public Account getAccountDetail(long id) {
		return accountRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new ApiErrorException(404, "Account not found"));
	}

	@Override
	public Page<Account> getAccountListPage(
			String query, AccountTypeEnum accountType, String accountNumber, int page, int size) {
		return accountRepository.findAll((root, cq, cb) -> {
			ArrayList<Predicate> predicates = new ArrayList<>();

			if (!query.isBlank()) {
				var searchAccountHolder = cb.like(cb.upper(root.get("accountHolderName")), "%" + query.toUpperCase() + "%");
				var searchAccountHolderEmail = cb.like(cb.upper(root.get("accountHolderEmail")), "%" + query.toUpperCase() + "%");
				var searchAccountHolderPhone = cb.like(cb.upper(root.get("accountHolderPhone")), "%" + query.toUpperCase() + "%");
				var searchNationalId = cb.like(cb.upper(root.get("nationalId")), "%" + query.toUpperCase() + "%");
				predicates.add(cb.or(searchAccountHolder, searchAccountHolderEmail, searchAccountHolderPhone, searchNationalId));
			}

			if (accountType != null) {
				predicates.add(cb.equal(root.get("accountType"), accountType));
			}

			if (!accountNumber.isBlank()) {
				predicates.add(cb.like(cb.upper(root.get("accountNumber")), "%" + accountNumber + "%"));
			}

			Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
			return cb.and(predicates.toArray(new Predicate[0]));
		}, PageRequest.of(page, size));
	}

	@Override
	public MessageResponse updateAccount(long id, AccountDto req) {
		final var account = this.getAccountDetail(id);
		final var updatedAccount = req.updateAccount(account);
		accountRepository.save(updatedAccount);
		return new MessageResponse();
	}

	@Override
	@Transactional
	public MessageResponse deposit(DepositReqDto req) {
		final var account = accountRepository.findByAccountNumberAndStatusTrue(req.accountNumber()).orElseThrow(() -> new ApiErrorException(404, "Account not found"));
		if (Objects.equals(req.amount(), BigDecimal.ZERO)) {
			throw new ApiErrorException(404, "Amount cannot be zero");
		}
		account.deposit(req.amount());
		accountRepository.save(account);
		return new MessageResponse();
	}

	@Override
	@Transactional
	public MessageResponse withdrawal(WithdrawalReqDto req) {
		final var account = accountRepository.findByAccountNumberAndStatusTrue(req.accountNumber()).orElseThrow(() -> new ApiErrorException(404, "Account not found"));
		if (req.amount().compareTo(account.getBalance()) > 0) {
			throw new ApiErrorException(404, "Insufficient balance");
		}
		account.withdraw(req.amount());
		accountRepository.save(account);
		return new MessageResponse();
	}

	private String generateAccountNumber() {
		var accountNumber = Helper.randomNumber(8);
		if (accountRepository.existByAccountNumberAndStatusTrue(accountNumber)) {
			generateAccountNumber();
		}
		return accountNumber;
	}
}
