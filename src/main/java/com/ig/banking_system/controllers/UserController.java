package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.base.response.ObjectResponse;
import com.ig.banking_system.base.response.PageResponse;
import com.ig.banking_system.dto.previlleges.UserDto;
import com.ig.banking_system.dto.previlleges.UserListDto;
import com.ig.banking_system.dto.previlleges.UserViewDto;
import com.ig.banking_system.model.previlleges.Users;
import com.ig.banking_system.services.UserService;
import com.ig.banking_system.utilities.constants.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{id}")
	public ObjectResponse<UserViewDto> getUserDetail(@PathVariable long id) {
		return new ObjectResponse<>(userService.getUserDetail(id).toUserViewDto());
	}

	@PutMapping("/{id}")
	public MessageResponse updateUser(@PathVariable Long id, @RequestBody UserDto req) {
		return userService.updateUser(id, req);
	}
}
