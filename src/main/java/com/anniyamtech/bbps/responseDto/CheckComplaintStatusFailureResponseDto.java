package com.anniyamtech.bbps.responseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckComplaintStatusFailureResponseDto {
	private static final long serialVersionUID = 1L;
	
	private Head head;
	
	private FailureReason reason;
	
	private Txn txn;
	
	private ComplaintDetailsResponseDto complaintDetails; 
	
}
