package com.ig.banking_system.repositories;

import com.ig.banking_system.base.repository.BaseRepository;
import com.ig.banking_system.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
	Boolean existsByAccountNumberAndStatusTrue(String accountNumber);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Account> findByAccountNumberAndStatusTrue(String accountNumber);
}
