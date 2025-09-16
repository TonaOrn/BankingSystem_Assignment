package com.ig.banking_system.repositories.previleges;

import com.ig.banking_system.base.repository.BaseRepository;
import com.ig.banking_system.model.previlleges.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<Users> {
		public Optional<Users> findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(String username, String email);
		public Boolean existsByUsernameIgnoreCaseAndStatusTrue(String username);
		public Boolean existsByUsernameIgnoreCaseAndStatusTrueAndIdNot(String email, Long id);
		public Boolean existsByEmailIgnoreCaseAndStatusTrue(String email);
		public Boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
