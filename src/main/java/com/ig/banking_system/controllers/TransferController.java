package com.ig.banking_system.controllers;

import com.ig.banking_system.base.response.ObjectResponse;
import com.ig.banking_system.dto.transfer.TransferReqDto;
import com.ig.banking_system.dto.transfer.TransferResDto;
import com.ig.banking_system.services.TransferService;
import com.ig.banking_system.utilities.constants.Constant;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.MAIN_PATH + "/transfer")
@AllArgsConstructor
public class TransferController {

	private final TransferService transferService;

	@PreAuthorize("hasAuthority('TRANSFER')")
	@PostMapping
	public ObjectResponse<TransferResDto> createTransfer(@Valid @RequestBody TransferReqDto transferReqDto) {
		return new ObjectResponse<>(transferService.createTransfer(transferReqDto));
	}
}
