package com.anniyamtech.bbps.responseDto;

import javax.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckComplaintStatusResponseDto {
	private static final long serialVersionUID = 1L;
	
	private Head head;
	
	private FailureReason reason;
	
	private Txn txn;
	
	private ComplaintDetailsResponseDto complaintDetails; 
	
}
