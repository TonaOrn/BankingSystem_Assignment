package com.ig.banking_system.dto.enumerates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountTypeEnum {
	SAVING(1),
	CHECKING(2),
	BUSINESS(3);

	private final int value;
}
