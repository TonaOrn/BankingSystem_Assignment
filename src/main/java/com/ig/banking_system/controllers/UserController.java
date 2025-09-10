package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.PageResponse;
import com.ig.banking_system.dto.UserListDto;
import com.ig.banking_system.model.Users;
import com.ig.banking_system.services.UserService;
import com.ig.banking_system.utilities.constants.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/list")
	public PageResponse<UserListDto> userListPage(
			@RequestParam(required = false) String query,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {
		var listPage = userService.getUserListPage(query, page, size);
		var userList = listPage.getContent().stream().map(Users::toUserListDto).toList();
		return new PageResponse<>(userList, listPage.getTotalElements());
	}
}
