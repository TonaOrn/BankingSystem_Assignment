package com.ig.banking_system.repositories;

import com.ig.banking_system.base.repository.BaseRepository;
import com.ig.banking_system.model.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<Users> {
		public Optional<Users> findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(String username, String email);
}
