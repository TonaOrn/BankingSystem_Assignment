package com.ig.banking_system.model.previlleges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ig.banking_system.base.model.BaseEntity;
import com.ig.banking_system.dto.previlleges.UserListDto;
import com.ig.banking_system.dto.previlleges.UserViewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(value = {"password", "roles"}, allowSetters = true)
public class Users extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String phoneNumber;
	private Boolean active;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = true)
	private Role role;

	public Users(Long id, String firstName, String lastName, String email, String username, String password, String phoneNumber, Role role) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public Users(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public UserListDto toUserListDto() {
		return new UserListDto(id, firstName, lastName, username, email, role.getName());
	}

	public UserViewDto toUserViewDto() {
		return new UserViewDto(id, firstName, lastName, username);
	}
}
