package com.ig.banking_system.services;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.dto.auth.LogInReqDto;
import com.ig.banking_system.dto.auth.LoginDto;
import com.ig.banking_system.dto.auth.RegisterUserDto;
import com.ig.banking_system.dto.previlleges.UserDto;
import com.ig.banking_system.model.previlleges.Users;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	LoginDto login(LogInReqDto loginDto);

	RegisterUserDto registerUser(Users req);

	MessageResponse updateUser(Long id, UserDto req);

	Users getUserDetail(long id);

	Page<Users> getUserListPage(String query, int page, int size);
}
