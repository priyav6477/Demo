package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.TransactionStatusMobile;
import com.anniyamtech.bbps.requestDto.TransactionStatusReq;

public interface TransactionStatusService {
	public BaseDTO checkTransactionStatusUsingRefId(TransactionStatusReq transactionReq) throws Exception;
	public BaseDTO checkTransactionStatusUsingMobile(TransactionStatusMobile transactionReq) throws Exception;
}
