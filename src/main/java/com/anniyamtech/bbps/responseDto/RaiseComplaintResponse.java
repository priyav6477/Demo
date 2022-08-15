package com.anniyamtech.bbps.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintResponse {
	private RaiseComplaintRespHead head;
	private RaiseComplaintRespReason reason;
	private RaiseComplaintRespTxn txn;
	private RaiseComplaintRespComplaintDetails complaintDetails;
}
