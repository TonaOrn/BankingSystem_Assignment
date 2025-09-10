package com.ig.banking_system.services.implementations;

import com.ig.banking_system.base.response.MessageResponse;
import com.ig.banking_system.dto.LogInReqDto;
import com.ig.banking_system.dto.LoginDto;
import com.ig.banking_system.dto.RegisterUserDto;
import com.ig.banking_system.dto.UserDto;
import com.ig.banking_system.exceptions.ApiErrorException;
import com.ig.banking_system.model.Users;
import com.ig.banking_system.repositories.RoleRepository;
import com.ig.banking_system.repositories.UserRepository;
import com.ig.banking_system.security.payload.UserPrinciple;
import com.ig.banking_system.services.UserService;
import com.ig.banking_system.utilities.jwt.JwtUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwt;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new UserPrinciple();
	}

	@Override
	public LoginDto login(LogInReqDto loginDto) {
		final var user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseAndStatusTrue(loginDto.username(), loginDto.username()).orElseThrow(() ->
				new ApiErrorException(401, "Invalid username or password"));
		if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
			throw new ApiErrorException(401, "Invalid username or password");
		}
		var userPermission = roleRepository.getRolePermissionByUser(user.getId());
		final var userAuthorities = userPermission.stream().map(p -> new SimpleGrantedAuthority(p.getName())).toList();
		final var userDetail = new UserPrinciple(
				user.getId(), user.getUsername(), user.getPassword(), userAuthorities
		);
		final var token = jwt.generateToken(userDetail);
		return new LoginDto(token);
	}

	@Override
	public RegisterUserDto registerUser(Users req) {
		req.setPassword(passwordEncoder.encode(req.getPassword()));
		var user = userRepository.save(req);
		var userPermission = roleRepository.getRolePermissionByUser(user.getId());
		final var userAuthorities = userPermission.stream().map(p -> new SimpleGrantedAuthority(p.getName())).toList();
		final var userDetail = new UserPrinciple(
				user.getId(), user.getUsername(), user.getPassword(), userAuthorities
		);
		final var token = jwt.generateToken(userDetail);
		return new RegisterUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), token);
	}

	@Transactional
	@Override
	public MessageResponse updateUser(Long id, UserDto req) {
		final var user = userRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new ApiErrorException(404, "User not found"));
		var userUpdated = req.updateUser(user);
		userRepository.save(userUpdated);
		return new MessageResponse();
	}

	@Override
	public Page<Users> getUserListPage(String query, int page, int size) {
		return userRepository.findAll((root, cq, cb) -> {
			ArrayList<Predicate> predicates = new ArrayList<>();

			if (!query.isBlank()) {
				var searchUsername = cb.like(cb.upper(root.get("username")), "%" + query.toUpperCase() + "%");
				var searchEmail = cb.like(cb.upper(root.get("email")), "%" + query.toUpperCase() + "%");
				predicates.add(cb.or(searchUsername, searchEmail));
			}
			Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
			return cb.and(predicates.toArray(new Predicate[0]));
		}, PageRequest.of(page, size));
	}
}
