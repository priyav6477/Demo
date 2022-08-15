package com.anniyamtech.bbps.responseDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxnList {
	private String txnReferenceId;
	private String agentId;
	private String billerId;
	private String amount;
	private String txnStatus;
	private Date txnDate;
			
	
}
