package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintReqDto {
	private String complaintType;
	private String participationType;
	private String txnReferenceId;
	private String servReason;
	private String description;
}
