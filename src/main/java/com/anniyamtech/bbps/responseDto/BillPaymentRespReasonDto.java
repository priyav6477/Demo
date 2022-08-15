package com.anniyamtech.bbps.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentRespReasonDto {
	private String responseCode;

	private String responseReason;
	private String complianceReasonCd;
	private String complianceReason;
	private String approvalRefNum;
}
