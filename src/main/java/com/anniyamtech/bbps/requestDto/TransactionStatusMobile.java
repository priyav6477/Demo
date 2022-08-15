package com.anniyamtech.bbps.requestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionStatusMobile {
	private Head head;
	private TxnDto txn;
	private TransactionDetailsMobile transactionDetails;
	private String otp;
	
}
