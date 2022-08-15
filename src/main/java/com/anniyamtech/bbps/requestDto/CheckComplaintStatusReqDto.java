package com.anniyamtech.bbps.requestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckComplaintStatusReqDto {
	private Head head;
	private TxnDto txn;
	private ComplaintDetailsDto complaintDetails;
	
}
