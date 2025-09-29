package com.ig.banking_system.services;

import com.ig.banking_system.dto.transfer.TransferReqDto;
import com.ig.banking_system.dto.transfer.TransferResDto;

public interface TransferService {
	TransferResDto createTransfer(TransferReqDto req);
}
