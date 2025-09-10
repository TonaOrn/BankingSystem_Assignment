package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.ObjectResponse;
import com.ig.banking_system.dto.LogInReqDto;
import com.ig.banking_system.dto.LoginDto;
import com.ig.banking_system.dto.RegisterUserDto;
import com.ig.banking_system.dto.UserDto;
import com.ig.banking_system.services.UserService;
import com.ig.banking_system.utilities.constants.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	@PostMapping("/login")
	public ObjectResponse<LoginDto> login(@Valid @RequestBody LogInReqDto req) {
		return new ObjectResponse<>(userService.login(req));
	}

	@PreAuthorize("hasAuthority('CREATE_USER')")
	@PostMapping("/register")
	public ObjectResponse<RegisterUserDto> registerUser(@Valid @RequestBody UserDto req) {
		return new ObjectResponse<>(userService.registerUser(req.toUser()));
	}
}
