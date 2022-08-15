package com.anniyamtech.bbps.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentRespTxnDto
{
	private String msgId;

	private String ts;
	
	private String txnReferenceId;
	

	
	
	
}
