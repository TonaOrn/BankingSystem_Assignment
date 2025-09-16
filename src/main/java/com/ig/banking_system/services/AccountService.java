package com.ig.banking_system.services;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.dto.account.AccountDto;
import com.ig.banking_system.dto.account.AccountViewDto;
import com.ig.banking_system.dto.account.DepositReqDto;
import com.ig.banking_system.dto.account.WithdrawalReqDto;
import com.ig.banking_system.dto.enumerates.AccountTypeEnum;
import com.ig.banking_system.model.Account;
import org.springframework.data.domain.Page;

public interface AccountService {
	AccountViewDto registerAccount(Account req);

	Account getAccountDetail(long id);

	Page<Account> getAccountListPage(String query, AccountTypeEnum accountType, String accountNumber, int page, int size);

	MessageResponse updateAccount(long id, AccountDto req);

	MessageResponse deposit(DepositReqDto req);

	MessageResponse withdrawal(WithdrawalReqDto req);
}
