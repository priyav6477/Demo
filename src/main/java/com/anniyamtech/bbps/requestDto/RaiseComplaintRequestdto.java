package com.anniyamtech.bbps.requestDto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintRequestdto implements Serializable {
	private RaiseComplaintHeadReq head;
	private RaiseComplaintTransactionReq txn;
	private RaiseComplaintComplaintDetailsReq complaintDetails;
    private String otp;
}
