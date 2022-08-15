package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionStatusReq {
	private Head head;
	private TxnDto txn;
	private TransactionDetails transactionDetails;
	private String otp;
	
}
