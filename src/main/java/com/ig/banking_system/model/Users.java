package com.ig.banking_system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ig.banking_system.base.model.BaseEntity;
import com.ig.banking_system.dto.UserListDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

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

	public UserListDto toUserListDto() {
		return new UserListDto(id, firstName, lastName, username, email);
	}
}
