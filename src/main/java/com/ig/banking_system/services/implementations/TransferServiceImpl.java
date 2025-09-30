package com.ig.banking_system.services.implementations;

import com.ig.banking_system.dto.enumerates.TransactionTypeEnum;
import com.ig.banking_system.dto.transactions.TransactionReqDto;
import com.ig.banking_system.dto.transfer.TransferReqDto;
import com.ig.banking_system.dto.transfer.TransferResDto;
import com.ig.banking_system.exceptions.ApiErrorException;
import com.ig.banking_system.repositories.AccountRepository;
import com.ig.banking_system.services.TransactionService;
import com.ig.banking_system.services.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

	private final TransactionService transactionService;
	private final AccountRepository accountRepo;

	@Transactional
	@Override
	public TransferResDto createTransfer(TransferReqDto req) {
		try {
			final var fromAcc = accountRepo.findByAccountNumberAndStatusTrue(req.fromAccount()).orElseThrow(() -> new ApiErrorException(404, "Invalid sender account"));
			final var toAcc = accountRepo.findByAccountNumberAndStatusTrue(req.toAccount()).orElseThrow(() -> new ApiErrorException(404, "Invalid receiver account"));

			// Validate balance
			if (req.amount().compareTo(fromAcc.getBalance()) > 0) {
				throw new ApiErrorException(400, "Insufficient balance");
			}
			fromAcc.setBalance(fromAcc.getBalance().subtract(req.amount()));
			toAcc.setBalance(toAcc.getBalance().add(req.amount()));
			accountRepo.save(fromAcc);
			accountRepo.save(toAcc);
			// Create Transaction
			var transferTran = new TransactionReqDto(
					null, TransactionTypeEnum.TRANSFER, req.amount(), null, fromAcc.getAccountNumber(), toAcc.getAccountNumber(), null
			);
			var tran = transactionService.createTransaction(transferTran);
			return new TransferResDto(fromAcc.getAccountNumber(), fromAcc.getAccountHolderName(), toAcc.getAccountNumber(), toAcc.getAccountHolderName(), req.amount(), tran.getTransactionDate());
		} catch (ApiErrorException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApiErrorException(500, ex.getMessage());
		}
	}
}
