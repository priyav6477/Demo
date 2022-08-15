package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintTxnHeadReq {
	private String ver;
	private String ts;
	private String origInst;
	private String refId;
}
