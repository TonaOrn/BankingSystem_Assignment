package com.ig.banking_system.dto.previlleges;

import com.ig.banking_system.model.previlleges.Role;
import com.ig.banking_system.model.previlleges.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	@Email(message = "Invalid email format")
	@NotBlank
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private String phoneNumber;
	private Role role;

	public Users toUser() {
		return new Users(id, firstName, lastName, email, username, password, phoneNumber, role);
	}

	public Users updateUser(Users u) {
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setUsername(username);
		u.setEmail(email);
		u.setRole(role);
		return u;
	}

	public UserViewDto toUserViewDto() {
		return new UserViewDto(id, firstName, lastName, username);
	}
}
