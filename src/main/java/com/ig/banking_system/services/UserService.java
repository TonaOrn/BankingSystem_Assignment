package com.ig.banking_system.services;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.dto.LogInReqDto;
import com.ig.banking_system.dto.LoginDto;
import com.ig.banking_system.dto.RegisterUserDto;
import com.ig.banking_system.dto.UserDto;
import com.ig.banking_system.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
		LoginDto login(LogInReqDto loginDto);
    RegisterUserDto registerUser(Users req);
    MessageResponse updateUser(Long id, UserDto req);
		Page<Users> getUserListPage(String query, int page, int size);
}
