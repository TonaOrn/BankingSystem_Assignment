package com.ig.banking_system.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Context {

	private Long id;
	private String username;

	public static Context from(UserPrinciple u) {
		Context context = new Context();
		context.setUsername(u.getUsername());
		context.setId(u.getId());
		return context;
	}
}